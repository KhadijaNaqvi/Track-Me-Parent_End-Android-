package com.unidroid.track_me_parentend.InfoFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unidroid.track_me_parentend.MainActivity;
import com.unidroid.track_me_parentend.R;


public class AboutFragment extends Fragment {
    private  TextView textView;
    private String string="This application Developed by the students of COMSATS UNIVERSITY ISLAMABAD (Lahore Campus)" +
            " as a semester project.\n" +
            "This Project Developed BY :\n" +
            "Muhammad Murtaza\n" +
            "Eman Anwar\n" +
            "Khadija Naqvi\n" +
            "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_about, container, false);
        textView=view.findViewById(R.id.textview_aboutus);
        textView.setText(string);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("About us ");
    }
}