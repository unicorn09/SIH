package com.saibaba.hackathon.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.StringVariable;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private DatabaseReference databaseReference,db;
    private TextView contname1,contrel1,contphon1,contname2,contrel2,contphon2,contname3,contrel3,contphon3;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        contname1=root.findViewById(R.id.tv_username_blog_item);
        contname2=root.findViewById(R.id.tv_username_blog_item1);
        contname3=root.findViewById(R.id.tv_username_blog_item3);

        contrel1=root.findViewById(R.id.tv_post_blog_item2);
        contrel2=root.findViewById(R.id.tv_post_blog_item21);
        contrel3=root.findViewById(R.id.tv_post_blog_item23);

        contphon1=root.findViewById(R.id.tv_postTime_blog_item);
        contphon2=root.findViewById(R.id.tv_postTime_blog_item1);
        contphon3=root.findViewById(R.id.tv_postTime_blog_item3);

        databaseReference=FirebaseDatabase.getInstance().getReference().child(StringVariable.EMERGENCY);

             databaseReference.child("Contact1").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contname1.setText(dataSnapshot.child(StringVariable.EMERGENCYNAME).getValue().toString());
                    contphon1.setText(dataSnapshot.child(StringVariable.EMERGENCYPHONE).getValue().toString());
                    contrel1.setText(dataSnapshot.child(StringVariable.EMERGENCYREL).getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        databaseReference.child("Contact2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contname2.setText(dataSnapshot.child(StringVariable.EMERGENCYNAME).getValue().toString());
                contphon2.setText(dataSnapshot.child(StringVariable.EMERGENCYPHONE).getValue().toString());
                contrel2.setText(dataSnapshot.child(StringVariable.EMERGENCYREL).getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("Contact3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contname3.setText(dataSnapshot.child(StringVariable.EMERGENCYNAME).getValue().toString());
                contphon3.setText(dataSnapshot.child(StringVariable.EMERGENCYPHONE).getValue().toString());
                contrel3.setText(dataSnapshot.child(StringVariable.EMERGENCYREL).getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
          return root;
    }
}