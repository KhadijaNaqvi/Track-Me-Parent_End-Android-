package com.unidroid.track_me_parentend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unidroid.track_me_parentend.Model.ChildData;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btnRegister,mbtnLogin;
    private TextInputEditText mName,mEmail,mPassFirst,mpassSecond;
    private static String email,pass,pass2,name;
    private CheckBox mchekBox;
    private static FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth= FirebaseAuth.getInstance();
        btnRegister=findViewById(R.id.btn_Register_User);
        mName=findViewById(R.id.cus_name);
        mEmail=findViewById(R.id.cus_email_register);
        mPassFirst=findViewById(R.id.first_password);
        mpassSecond=findViewById(R.id.conf_password);
        mchekBox=findViewById(R.id.chekbox_terms);
        mbtnLogin=findViewById(R.id.btn_Register_login);
        mchekBox.setChecked(false);
        btnRegister.setEnabled(false);

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mchekBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) btnRegister.setEnabled(true); else btnRegister.setEnabled(false);

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=mName.getText().toString().trim();
                email=mEmail.getText().toString().trim();
                pass=mPassFirst.getText().toString().trim();
                pass2=mpassSecond.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass2) || pass.length()<6){
                    //Toast.makeText(RegistrationActivity.this, ""+pass.length(), Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(name))mName.setError("Requires");
                    if (TextUtils.isEmpty(email))mEmail.setError("Requires");
                    if (TextUtils.isEmpty(pass) || pass.length()<=6)mPassFirst.setError("Password Must be at least 6 Characters.");
                    if (TextUtils.isEmpty(pass2))mpassSecond.setError("Requires");
                }else if( !pass.equals(pass2)){
                    mpassSecond.setError("Password Not Matched");

                }else {
                    newUser(email,pass);
                }
            }
        });
    }
    public void newUser(String memail,String mpassword){
        mAuth.createUserWithEmailAndPassword(memail,mpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    addParent();
                    Toast.makeText(RegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
    private static void addParent(){

        firebaseFirestore= FirebaseFirestore.getInstance();
        Map<String, Object> stringChildDataMap=new HashMap<>();
        stringChildDataMap.put("name",name);
        stringChildDataMap.put("pass",pass);
        stringChildDataMap.put("email",email);

        firebaseFirestore.collection("ParentData").add(stringChildDataMap)
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

}
