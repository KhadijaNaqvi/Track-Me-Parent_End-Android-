package com.unidroid.track_me_parentend;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unidroid.track_me_parentend.Model.ChildData;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button mbtnLogin,mbtnRegister;
    private TextInputEditText mtextEmail,mtextPass;
    private String pass,email;
    private FirebaseAuth firebaseAuth;
    private static  SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor1;

    private static FirebaseFirestore firebaseFirestore ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mbtnLogin=findViewById(R.id.btn_login);
        mbtnRegister=findViewById(R.id.btn_Register);
        sharedPreferences=getSharedPreferences("MySharedPref2",MODE_PRIVATE);


        editor1=sharedPreferences.edit();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mtextEmail=findViewById(R.id.customer_email_login);
        mtextPass=findViewById(R.id.customer_pass_login);
        firebaseAuth= FirebaseAuth.getInstance();
        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=mtextEmail.getText().toString().trim();
                pass=mtextPass.getText().toString().trim();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    if (TextUtils.isEmpty(email)){ mtextEmail.setError("Please Enter Email");}
                    if(TextUtils.isEmpty(pass)){mtextPass.setError("Please Enter Password ");}
                }
                else{
                    signInUser(email,pass);
                   }

            }
        });
        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void signInUser(String email, String pass){
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Sign in Successfully", Toast.LENGTH_SHORT).show();
                    editor1.putString("email",email);
                    editor1.putBoolean("check_login",true);
                    editor1.commit();
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();

                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }


}


