package com.unidroid.track_me_parentend.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.unidroid.track_me_parentend.Adapter.GPS_Adapter;
import com.unidroid.track_me_parentend.MainActivity;
import com.unidroid.track_me_parentend.Model.GPS_Data;
import com.unidroid.track_me_parentend.R;


public class LocationDataRecycler extends Fragment implements GPS_Adapter.onGpsClickListner{
    private RecyclerView recyclerView;
    private GPS_Adapter gps_adapter;
    private FirebaseFirestore mFirebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmant_gps_recyler, container, false);

        recyclerView=view.findViewById(R.id.gps_recyler);
        mFirebaseFirestore= FirebaseFirestore.getInstance();
        Bundle b=getArguments();
         String reference=b.getString("reference");
        Log.e("onCreateView: ", "R : "+reference);


        Query query=mFirebaseFirestore.collection(reference).orderBy("time", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<GPS_Data> gps_dataFirestoreRecyclerOptions=new FirestoreRecyclerOptions.Builder<GPS_Data>().setQuery(query,GPS_Data.class).build();
        gps_adapter=new GPS_Adapter(gps_dataFirestoreRecyclerOptions,this);
        gps_adapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
      RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
       itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        recyclerView.setAdapter(gps_adapter);


         return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        gps_adapter.startListening();
    }


    @Override
    public void onGpsItemClick(int position, String longitude, String latitude) {
        Log.e( "onGpsItemClick: ",
                ""+longitude+"  "+latitude);
        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?q="+latitude+","+longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

    }

    @Override
    public void onStop() {
        super.onStop();
        gps_adapter.stopListening();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Saved Locations");
    }
}