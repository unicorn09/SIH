package com.saibaba.hackathon.ui.send;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saibaba.hackathon.Adapters.ModelNotice;
import com.saibaba.hackathon.Adapters.NoticeAdapter;
import com.saibaba.hackathon.PoliceStation.Notices;
import com.saibaba.hackathon.PoliceStation.Officers;
import com.saibaba.hackathon.PoliceStation.ViewPagerAdapter;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.StringVariable;

import java.util.ArrayList;

public class SendFragment extends Fragment {

    private static final String TAG = "SendFragment";

    private SendViewModel sendViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ModelNotice> arrayList;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View v = inflater.inflate(R.layout.fragment_send, container, false);
        recyclerView=v.findViewById(R.id.recyclerviewnotices);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        String userUID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        final NoticeAdapter noticeAdapter=new NoticeAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(noticeAdapter);
        FirebaseDatabase.getInstance().getReference().child("USERS").child(userUID)
                .child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    arrayList.clear();
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        arrayList.add(new ModelNotice(dataSnapshot.child("name").getValue().toString(),
                                dataSnapshot.child("time").getValue().toString(),
                                dataSnapshot.child("content").getValue().toString()));
                    }
                    noticeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: "+databaseError.getMessage());
            }
        });
        return v;
    }
}