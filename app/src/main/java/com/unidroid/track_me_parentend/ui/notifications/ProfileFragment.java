package com.unidroid.track_me_parentend.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.unidroid.track_me_parentend.Fragments.AddChild;
import com.unidroid.track_me_parentend.InfoFragments.AboutFragment;
import com.unidroid.track_me_parentend.InfoFragments.FeedBackFragment;
import com.unidroid.track_me_parentend.LoginActivity;
import com.unidroid.track_me_parentend.MainActivity;
import com.unidroid.track_me_parentend.R;

public class ProfileFragment extends Fragment {
     private Button btnAddChild;
     private TextView mTextViewFAQ,mTextViewlogout,mTextViewAbout,mTextViewFeedback,parentProfileName;
     private Bundle bundle;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
         btnAddChild=root.findViewById(R.id.btn_add_child_profile_page);
         mTextViewAbout=root.findViewById(R.id.about_profile_page);
         parentProfileName=root.findViewById(R.id.profile_page_parent_name);
         Context context;
         SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("MySharedPref2",Context.MODE_PRIVATE);
         parentProfileName.setText(sharedPreferences.getString("email",""));

         mTextViewFeedback=root.findViewById(R.id.feedback_profile_page);
         mTextViewlogout=root.findViewById(R.id.logout_profile_page);
         bundle=getArguments();
         btnAddChild.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Bundle bundle2=new Bundle();
                 Log.e("onClick: ",""+bundle.getString("reference") );
                 bundle2.putCharSequence("reference",bundle.getString("reference"));
                 FragmentTransaction transaction2=getFragmentManager().beginTransaction();
                 transaction2.setCustomAnimations(R.anim.slide_in_right,R.anim.fade_out);
                 AddChild fragment2=new AddChild();
                 fragment2.setArguments(bundle2);
                 transaction2.replace(R.id.nav_host_fragment,fragment2);
                 transaction2.addToBackStack(null);

                 transaction2.commit();
             }
         });
      mTextViewlogout.setOnClickListener(new View.OnClickListener() {
      @Override
       public void onClick(View v) {

          SharedPreferences sharedPreferences=getContext().getSharedPreferences("MySharedPref2", Context.MODE_PRIVATE);
          sharedPreferences.edit().clear().commit();
          Intent intent=new Intent(getContext(), LoginActivity.class);
          startActivity(intent);
          Toast.makeText(getContext(), "User Logout", Toast.LENGTH_SHORT).show();

    }});
      mTextViewAbout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentTransaction transaction2=getFragmentManager().beginTransaction();
              AboutFragment fragment2=new AboutFragment();
              transaction2.setCustomAnimations(R.anim.slide_in_right,R.anim.fade_out);
              transaction2.replace(R.id.nav_host_fragment,fragment2);

              transaction2.addToBackStack(null);
              transaction2.commit();

          }
      });
      mTextViewFeedback.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentTransaction transaction2=getFragmentManager().beginTransaction();
              FeedBackFragment fragment2=new FeedBackFragment();
              transaction2.setCustomAnimations(R.anim.slide_in_right,R.anim.fade_out);
              transaction2.replace(R.id.nav_host_fragment,fragment2);
              transaction2.addToBackStack(null);
              transaction2.commit();

          }
      });



        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Profile Page");
    }


}