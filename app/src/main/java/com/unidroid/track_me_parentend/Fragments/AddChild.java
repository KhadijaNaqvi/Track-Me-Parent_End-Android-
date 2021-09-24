package com.unidroid.track_me_parentend.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unidroid.track_me_parentend.MainActivity;
import com.unidroid.track_me_parentend.Model.ChildData;
import com.unidroid.track_me_parentend.R;

import java.util.HashMap;
import java.util.Map;

public class AddChild extends Fragment {
    private FirebaseAuth mAuth;
    private Button btnRegister;
    private TextInputEditText mfirstName,mlsatName,mEmail,mPassFirst,mpassSecond;
    private static String email,pass,pass2,sfirstName,slastName;
    private CheckBox mchekBox;
    private static FirebaseFirestore firebaseFirestore;
    private  static ChildData childData;
    private static String reference;
    public static boolean  checkChild=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_child, container, false);
        mAuth= FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        btnRegister=view.findViewById(R.id.btn_add_childadd);
        mfirstName=view.findViewById(R.id.child_add_firstname);
        mlsatName=view.findViewById(R.id.child_add_lastname);
        mEmail=view.findViewById(R.id.cus_email_childadd);
        mPassFirst=view.findViewById(R.id.first_password_childadd);
        mpassSecond=view.findViewById(R.id.conf_password_childadd);
        mchekBox=view.findViewById(R.id.chekbox_terms_child);
        mchekBox.setChecked(false);
        btnRegister.setEnabled(false);
        Bundle b=getArguments();
        reference=b.getString("reference");
        Log.d("onCreateView: ",""+reference);

        mchekBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) btnRegister.setEnabled(true); else btnRegister.setEnabled(false);

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkChild=false;
                sfirstName=mfirstName.getText().toString().trim();
                slastName=mlsatName.getText().toString().trim();
                email=mEmail.getText().toString().trim();
                pass=mPassFirst.getText().toString().trim();

                    pass2=mpassSecond.getText().toString().trim();
                if (TextUtils.isEmpty(sfirstName) || TextUtils.isEmpty(slastName) ||TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass2) || pass.length()<6){
                    //Toast.makeText(RegistrationActivity.this, ""+pass.length(), Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(sfirstName))mfirstName.setError("Requires");
                    if (TextUtils.isEmpty(slastName))mfirstName.setError("Requires");
                    if (TextUtils.isEmpty(email))mEmail.setError("Requires");
                    if (TextUtils.isEmpty(pass) || pass.length()<=6)mPassFirst.setError("Password Must be at least 6 Characters.");
                    if (TextUtils.isEmpty(pass2))mpassSecond.setError("Requires");
                }else if( !pass.equals(pass2)){
                    mpassSecond.setError("Password Not Matched");

                }else {
                 isChildAlreadyPresent();
                }

            }
        });

        return view;
    }
    private static void addChild(){
        childData=new ChildData(sfirstName,slastName,email,pass);
        Log.e("addChild: ", "addd");
        Map<String, Object> stringChildDataMap=new HashMap<>();
        stringChildDataMap.put("firstName",childData.getFirstName().toString());
        stringChildDataMap.put("lastName",childData.getLastName().toString());
        stringChildDataMap.put("email",childData.getEmail().toString());
        stringChildDataMap.put("password",childData.getPassword().toString());
        stringChildDataMap.put("id",reference.toString());
        firebaseFirestore.collection("childdata").add(stringChildDataMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e( "onSuccess: ","Child Add" );
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e( "onFailure: ","Child Not Add" );
            }
        });

    }
    private static void isChildAlreadyPresent(){
        firebaseFirestore.collection("childdata").whereEqualTo("email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot : task.getResult()){

                        checkChild=true;
                        //Log.e( "onComplete: ", "ChildP"+checkChild);


                    }
                    if(!checkChild){
                        addChild();

                        Log.e("onComplete: ", "Child Added");
                    }
                }
            }
        });



    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Add Your Child ");
    }
}