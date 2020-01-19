package com.saibaba.hackathon.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saibaba.hackathon.Adapters.Model;
import com.saibaba.hackathon.Adapters.Model_Adapter;
import com.saibaba.hackathon.Forms.MissingPerson;
import com.saibaba.hackathon.Forms.RegisterFIR;
import com.saibaba.hackathon.Forms.ViewFIR;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.RecyclerViewItemListner;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
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
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView=v.findViewById(R.id.recyclerviewhome);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        arrayList.add(new Model("FIR","File eFIR / View FIR",getResources().getDrawable(R.drawable.ic_assignment_white_24dp)));
        arrayList.add(new Model("COMPLAIN","Threat / Defamation / Anonymous",getResources().getDrawable(R.drawable.ic_format_list_bulleted_white_24dp)));
        arrayList.add(new Model("NOC","Event Request / Protest Request etc.",getResources().getDrawable(R.drawable.ic_chrome_reader_mode_white_24dp)));
        arrayList.add(new Model("LOST & FOUND","Vehicle /  Article Lost / Recovered",getResources().getDrawable(R.drawable.ic_room_white_24dp)));
        arrayList.add(new Model("MISSING PERSON","Report Missing / Found",getResources().getDrawable(R.drawable.ic_person_add_white_24dp)));
        arrayList.add(new Model("VERIFICATION","Employee / Tenant / Character ",getResources().getDrawable(R.drawable.ic_assignment_turned_in_white_24dp)));
        adapter=new Model_Adapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
