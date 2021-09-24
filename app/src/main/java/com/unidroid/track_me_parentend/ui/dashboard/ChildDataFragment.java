package com.unidroid.track_me_parentend.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.unidroid.track_me_parentend.Adapter.ChildAdapter;
import com.unidroid.track_me_parentend.Fragments.DialogFragment;
import com.unidroid.track_me_parentend.Fragments.LocationDataRecycler;
import com.unidroid.track_me_parentend.MainActivity;
import com.unidroid.track_me_parentend.Model.ChildData;
import com.unidroid.track_me_parentend.R;

public class ChildDataFragment extends Fragment implements ChildAdapter.onChildClickListner{
      private  RecyclerView recyclerView;
      private FirebaseFirestore firebaseFirestore;
      private ChildAdapter childAdapter;
      private static String activityName;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_child, container, false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=root.findViewById(R.id.recyler_child);
        Bundle b=getArguments();


        String reference=b.getString("reference");
        activityName=b.getString("home");

        Query query=firebaseFirestore.collection("childdata").whereEqualTo("id",reference);
        FirestoreRecyclerOptions<ChildData> gps_dataFirestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<ChildData>().setQuery(query,ChildData.class).build();
        childAdapter=new ChildAdapter(gps_dataFirestoreRecyclerOptions,this);
        childAdapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(childAdapter);




        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        childAdapter.startListening();
    }


    @Override
    public void onChildItemClick(String reference) {

    }

    @Override
    public void onChildItemClick(String reference, String name, String email) {
        Bundle bundle2 = new Bundle();
        Log.e("onChildItemClick: ", "" + reference);
        bundle2.putCharSequence("reference", reference);
        bundle2.putString("name",name);
        bundle2.putString("email",email);
        FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
        if (activityName == "home") {
            DialogFragment dialog = new DialogFragment();
            dialog.setArguments(bundle2);
            FragmentManager fm = getFragmentManager();

            dialog.show(getFragmentManager(), "");

        } else {
            LocationDataRecycler fragment2 = new LocationDataRecycler();
            fragment2.setArguments(bundle2);
            transaction2.setCustomAnimations(R.anim.slide_in_right,R.anim.fade_out);
            transaction2.replace(R.id.nav_host_fragment, fragment2);
            transaction2.addToBackStack(null);
            transaction2.commit();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        childAdapter.stopListening();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (activityName=="home")
        ((MainActivity) getActivity())
                .setActionBarTitle("Child Data");
        else
            ((MainActivity) getActivity())
                    .setActionBarTitle("View Child Location");
    }
}