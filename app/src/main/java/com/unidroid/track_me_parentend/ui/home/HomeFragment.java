package com.unidroid.track_me_parentend.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.NavigationUI;

import com.unidroid.track_me_parentend.Fragments.AddChild;
import com.unidroid.track_me_parentend.Fragments.LocationDataRecycler;
import com.unidroid.track_me_parentend.MainActivity;
import com.unidroid.track_me_parentend.R;
import com.unidroid.track_me_parentend.ui.notifications.ProfileFragment;

public class HomeFragment extends Fragment {

   private Button button;
   private ImageView mImageViewLocationHistory,mImageViewLiveLocation;
   private Bundle bundle;
   private String mBundleData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);







        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Home Page ");
    }
}