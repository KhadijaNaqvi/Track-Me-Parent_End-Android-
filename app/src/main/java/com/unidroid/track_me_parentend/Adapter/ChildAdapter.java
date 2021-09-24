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
import com.unidroid.track_me_parentend.Model.ChildData;

import com.unidroid.track_me_parentend.R;

public class ChildAdapter extends FirestoreRecyclerAdapter<ChildData,ChildAdapter.viewHolder> {
    private onChildClickListner onChildClickListner;



    public ChildAdapter(@NonNull FirestoreRecyclerOptions<ChildData> options,onChildClickListner onChildClickListner) {
        super(options);
        this.onChildClickListner=onChildClickListner;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull ChildData model) {
         holder.mlastname.setText(model.getEmail());
         holder.mfirstname.setText(model.getFirstName()+" "+model.getLastName());
        Log.e( "onBindViewHolder: ",""+model.getFirstName() );

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.child_data_row, parent, false);

        return new viewHolder(view,onChildClickListner);
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mfirstname,mlastname;
        onChildClickListner onChildClickListner;
        public viewHolder(@NonNull View itemView,onChildClickListner onChildClickListner) {
            super(itemView);
            mfirstname=itemView.findViewById(R.id.child_firstname);
            mlastname=itemView.findViewById(R.id.child_lastname);
            this.onChildClickListner=onChildClickListner;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            DocumentReference reference=getSnapshots().getSnapshot(getAdapterPosition()).getReference();

            String firstName=getSnapshots().getSnapshot(getAdapterPosition()).getString("firstName");
            String lastName=getSnapshots().getSnapshot(getAdapterPosition()).getString("lastName");
            String email=getSnapshots().getSnapshot(getAdapterPosition()).getString("email");
            Log.e( "onBindViewHolder: ",""+email );
            onChildClickListner.onChildItemClick(reference.getId(),(firstName+"  "+lastName),email);
        }
    }
    public interface onChildClickListner{
         void onChildItemClick(String reference);
        void onChildItemClick(String reference,String name, String email);

    }
}
