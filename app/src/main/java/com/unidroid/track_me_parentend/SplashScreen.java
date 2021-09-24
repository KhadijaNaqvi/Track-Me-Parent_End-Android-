package com.unidroid.track_me_parentend;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class SplashScreen extends AppCompatActivity {
    private Handler mHandler;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen2);
        mSharedPreferences=getSharedPreferences("MySharedPref2",MODE_PRIVATE);
        Log.e("run: ", ""+mSharedPreferences.getBoolean("login_state",false));
        mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(mSharedPreferences.getBoolean("check_login",false)){
                    Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{

                    Intent intent=new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        },3000);
    }

    @Override
    protected void onStart() {

        super.onStart();
    }
}
