package com.saibaba.hackathon.Home1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saibaba.hackathon.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ModalHome> arrayList;
    HomeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView=v.findViewById(R.id.recyclerviewhome);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        arrayList=new ArrayList<>();
        arrayList.add(new ModalHome("FIR",""));
        arrayList.add(new ModalHome("COMPLAIN",""));
        arrayList.add(new ModalHome("NOC",""));
        arrayList.add(new ModalHome("LOST/FOUND",""));
        arrayList.add(new ModalHome("MISSING PERSON",""));
        arrayList.add(new ModalHome("VERIFICATION",""));
        adapter=new HomeAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
