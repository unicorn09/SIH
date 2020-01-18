package com.saibaba.hackathon.Fragments;

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
import com.saibaba.hackathon.Forms.ArticleFound;
import com.saibaba.hackathon.Forms.ArticleLost;

import com.saibaba.hackathon.Forms.VehicleLost;

import com.saibaba.hackathon.R;
import com.saibaba.hackathon.RecyclerViewItemListner;

import java.util.ArrayList;


public class LostAndFound extends Fragment {
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
        View v=inflater.inflate(R.layout.fragment_lost_and_found,container,false);
        recyclerView=v.findViewById(R.id.recylerviewlostandfound);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        arrayList.add(new Model("VEHICLE LOST","Report lost vehicle",getResources().getDrawable(R.drawable.ic_directions_car_white_24dp)));
        arrayList.add(new Model("ARTICLE LOST","Event Request / Protest Request etc.",getResources().getDrawable(R.drawable.ic_card_travel_white_24dp)));
        arrayList.add(new Model("ARTICLE FOUND","Vehicle /  Article Lost / Recovered",getResources().getDrawable(R.drawable.ic_assignment_turned_in_white_24dp)));
        adapter=new Model_Adapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerViewItemListner(getActivity(), recyclerView ,new RecyclerViewItemListner.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(position==0)
                            startActivity(new Intent(getActivity(), VehicleLost.class));
                        else if(position==1)
                            startActivity(new Intent(getActivity(), ArticleLost.class));
                        else if (position==2)
                            startActivity(new Intent(getActivity(), ArticleFound.class));
                    }
                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
        return v;
    }
}
