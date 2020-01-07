package com.saibaba.hackathon.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saibaba.hackathon.Adapters.Model;
import com.saibaba.hackathon.Adapters.Model_Adapter;
import com.saibaba.hackathon.R;

import java.util.ArrayList;

public class NOC extends Fragment {
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
        View v=inflater.inflate(R.layout.fragment_noc,container,false);
        recyclerView=v.findViewById(R.id.recylerviewnoc);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        arrayList.add(new Model("PROCESSION REQUEST","File eFIR / View FIR"));
        arrayList.add(new Model("PROTEST/STRIKE REQUEST","Threat / Defamation / Anonymous"));
        arrayList.add(new Model("EVENT PERFORMANCE","Event Request / Protest Request etc."));
        arrayList.add(new Model("FILM SHOOTING","Vehicle /  Article Lost / Recovered"));
        adapter=new Model_Adapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
