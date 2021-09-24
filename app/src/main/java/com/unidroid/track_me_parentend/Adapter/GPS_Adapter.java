package com.unidroid.track_me_parentend.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unidroid.track_me_parentend.Model.GPS_Data;
import com.unidroid.track_me_parentend.R;

import java.util.List;

public class GPS_Adapter extends FirestoreRecyclerAdapter<GPS_Data, GPS_Adapter.viewHolder> {
       private String id;
       private onGpsClickListner onGpsClickListner;


    public GPS_Adapter(@NonNull FirestoreRecyclerOptions<GPS_Data> options,onGpsClickListner onGpsClickListner) {
        super(options);
        this.onGpsClickListner=onGpsClickListner;

    }
    @Override
    protected void onBindViewHolder(@NonNull viewHolder viewHolder, int i, @NonNull GPS_Data gps_data) {
       viewHolder.mTextViewLongitude.setText(gps_data.getDate());
       viewHolder.mTextViewLatitude.setText(gps_data.getTime());

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.gps_data_row,parent,false);
        return new viewHolder(view,onGpsClickListner);
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextViewLongitude,mTextViewLatitude;
        onGpsClickListner onGpsClickListner;
       public viewHolder(@NonNull View itemView,onGpsClickListner onGpsClickListner) {
           super(itemView);
           mTextViewLatitude=itemView.findViewById(R.id.latitude);
           mTextViewLongitude=itemView.findViewById(R.id.logitude);
           this.onGpsClickListner=onGpsClickListner;
           itemView.setOnClickListener(this);

       }

        @Override
        public void onClick(View v) {
            DocumentReference reference=getSnapshots().getSnapshot(getAdapterPosition()).getReference();
            Log.e( "onClick: ", ""+reference.getId());
            String longitude=getSnapshots().getSnapshot(getAdapterPosition()).get("longitude").toString();
            String latitude=getSnapshots().getSnapshot(getAdapterPosition()).get("latitude").toString();

            onGpsClickListner.onGpsItemClick(getAdapterPosition(),longitude,latitude);


            //id=reference.getId();

        }
    }
   public interface onGpsClickListner {
        public void onGpsItemClick(int position,String longitude,String latitude);


   }
}
