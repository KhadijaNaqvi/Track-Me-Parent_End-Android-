package com.unidroid.track_me_parentend.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unidroid.track_me_parentend.R;

import java.util.Collection;
import java.util.List;


public class DialogFragment extends androidx.fragment.app.DialogFragment {
    private TextView mTextViewName, mTextViewEmail;
    private Button mbtnDelete;
    private Bundle bundle;
    FirebaseFirestore mFirebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        mTextViewEmail = view.findViewById(R.id._child_email_dialog);
        mTextViewName = view.findViewById(R.id.textview_child_name_dialog);
        mbtnDelete = view.findViewById(R.id.btn_delete_child_dialog);
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        bundle = getArguments();
        mTextViewName.setText(bundle.getString("name"));
        mTextViewEmail.setText(bundle.getString("email"));
        Log.e("onCreateView: ", bundle.getString("name"));

        mbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseFirestore.collection("childdata").document(bundle.getString("reference")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Child Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });


            }
        });


        return view;
    }

    void deleteCollection(CollectionReference collection, int batchSize) {
        try {


            // retrieve a small batch of documents to avoid out-of-memory errors
            // ApiFuture<QuerySnapshot> future = collection.limit(batchSize).get();
            int deleted = 0;
            // future.get() blocks on document retrieval
            //List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            // for (QueryDocumentSnapshot document : ) {
            //   document.getReference().delete();
            //    ++deleted;
            // }
            if (deleted >= batchSize) {
                // retrieve and delete another batch
                deleteCollection(collection, batchSize);
            }
        } catch (Exception e) {
            System.err.println("Error deleting collection : " + e.getMessage());
        }
    }
}