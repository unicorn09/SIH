package com.saibaba.hackathon.Fragments;


import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.saibaba.hackathon.Adapters.Model;
import com.saibaba.hackathon.Adapters.Model_Adapter;
import com.saibaba.hackathon.Forms.PersonalDetails;
import com.saibaba.hackathon.Forms.RegisterFIR;
import com.saibaba.hackathon.Forms.ViewFIR;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.RecyclerViewItemListner;

import java.util.ArrayList;

public class FIR extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Model> arrayList;
    Model_Adapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_fir,container,false);
        recyclerView=v.findViewById(R.id.recylerviewfir);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        arrayList.add(new Model("File FIR","File eFIR / View FIR"));
        arrayList.add(new Model("View FIR","File eFIR / View FIR"));
        adapter=new Model_Adapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerViewItemListner(getActivity(), recyclerView ,new RecyclerViewItemListner.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       if(position==0)
                       {
                           Intent i=new Intent(getActivity(), RegisterFIR.class);
                           i.putExtra("NOC","FIR REGISTRY");
                           startActivity(i);
                       }
                       else if(position==1)
                           startActivity(new Intent(getActivity(), ViewFIR.class));
                    }
                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
        return v;
    }
}
