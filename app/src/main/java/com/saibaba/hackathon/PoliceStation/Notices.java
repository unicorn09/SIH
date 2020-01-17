package com.saibaba.hackathon.PoliceStation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saibaba.hackathon.Adapters.Model;
import com.saibaba.hackathon.Adapters.ModelNotice;
import com.saibaba.hackathon.Adapters.Model_Adapter;
import com.saibaba.hackathon.Adapters.NoticeAdapter;
import com.saibaba.hackathon.R;

import java.util.ArrayList;


public class Notices extends Fragment {
private RecyclerView recyclerView;
    private ArrayList<ModelNotice> arrayList;
    private NoticeAdapter adapter;
    private DatabaseReference databaseReference;
    String content,timestamp,name,image,post;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_notices, container, false);




        recyclerView=v.findViewById(R.id.recyclerviewnotices);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();

        //@CREATED BY HARSH CHANGE POLICE STATION FROM HARD CODE TO GETINTENT
        databaseReference=FirebaseDatabase.getInstance().getReference().child("police-station").child("BR26003");
        databaseReference.child("notice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    String key=dataSnapshot1.getKey();
                    DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("notice").child(key);
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                             content=dataSnapshot2.child("content").getValue().toString();
                             name=dataSnapshot2.child("police-name").getValue().toString();
                             timestamp=dataSnapshot2.child("timestamp").getValue().toString();
                             image=dataSnapshot2.child("image-post").getValue().toString();
                             post=dataSnapshot2.child("police-post").getValue().toString();
                             arrayList.add(new ModelNotice(name,post,timestamp,content,image));
                             adapter=new NoticeAdapter(getActivity(),arrayList);
                             recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return v;
    }
}