package com.unidroid.track_me_parentend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unidroid.track_me_parentend.ui.dashboard.ChildDataFragment;
import com.unidroid.track_me_parentend.ui.home.HomeFragment;
import com.unidroid.track_me_parentend.ui.notifications.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private static String emailParentIntent,referenceParent;
    private static FirebaseFirestore firebaseFirestore;
    private SharedPreferences sharedPreferences;
    private long pressedTime;

    //SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref2",MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //etSupportActionBar().setBackgroundDrawable(R.drawable.disable_button);
        getSupportActionBar().setIcon(R.drawable.locationhistory_icon);
        firebaseFirestore=FirebaseFirestore.getInstance();
        sharedPreferences=getSharedPreferences("MySharedPref2",MODE_PRIVATE);
        emailParentIntent=sharedPreferences.getString("email","");
        Log.e("onCreate: ", emailParentIntent);
        getParentIdFromDataBase();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("reference",referenceParent);
        editor.commit();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case R.id.navigation_home:
                     {
                         Bundle bundle1=new Bundle();
                         bundle1.putCharSequence("reference",referenceParent);
                         bundle1.putString("home","home");
                         FragmentManager manager1=getSupportFragmentManager();
                         FragmentTransaction transaction1=manager1.beginTransaction();
                         ChildDataFragment fragment=new ChildDataFragment();
                         fragment.setArguments(bundle1);
                         transaction1.setCustomAnimations(R.anim.slide_in_right,R.anim.fade_out);
                        // transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                         transaction1.replace(R.id.nav_host_fragment,fragment);

                         transaction1.addToBackStack(null);
                         transaction1.commit();
                         break;
                     }

                     case R.id.navigation_childs:{
                         Bundle bundle1=new Bundle();
                         bundle1.putCharSequence("reference",referenceParent);

                         FragmentManager manager1=getSupportFragmentManager();
                         FragmentTransaction transaction1=manager1.beginTransaction();
                         ChildDataFragment fragment1=new ChildDataFragment();
                         fragment1.setArguments(bundle1);
                         transaction1.setCustomAnimations(R.anim.slide_in_right,R.anim.fade_out);
                         //transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                         transaction1.replace(R.id.nav_host_fragment,fragment1);
                         transaction1.addToBackStack(null);

                         transaction1.commit();
                         break;
                     }

                     case R.id.navigation_profile:
                     {
                         Bundle bundle2=new Bundle();
                         bundle2.putString("reference",referenceParent);
                         FragmentManager manager2=getSupportFragmentManager();
                         FragmentTransaction transaction2=manager2.beginTransaction();
                         ProfileFragment fragment2=new ProfileFragment();
                         fragment2.setArguments(bundle2);
                         transaction2.setCustomAnimations(R.anim.slide_in_right,R.anim.fade_out);
                         //transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                         transaction2.replace(R.id.nav_host_fragment,fragment2);
                         transaction2.addToBackStack(null);

                         transaction2.commit();
                         break;
                     }
                     case R.id.navigation_start_home:{

                        // Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                       //  startActivity(intent);
                        // finish();

                       openFragment();


                     }


                 }

                return true;
            }

        });




       openFragment();


    }
    private static void getParentIdFromDataBase(){
        firebaseFirestore.collection("ParentData").whereEqualTo("email",emailParentIntent).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot : task.getResult()){
                          DocumentReference reference=snapshot.getReference();
                          referenceParent=reference.getId();
                       // Log.e( "Reference : ", "ID" +referenceParent);
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              e.printStackTrace();

            }
        });



    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    private void openFragment()
    {
        Bundle bundle1=new Bundle();
        bundle1.putCharSequence("reference",referenceParent);
        bundle1.putString("home","home");
        FragmentManager manager1=getSupportFragmentManager();
        FragmentTransaction transaction1=manager1.beginTransaction();
        HomeFragment fragment=new HomeFragment();
        fragment.setArguments(bundle1);
        transaction1.setCustomAnimations(R.anim.slide_in_right,R.anim.fade_out);
        //transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction1.replace(R.id.nav_host_fragment,fragment);
        transaction1.addToBackStack(null);

        transaction1.commit();

    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}