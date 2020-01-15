package com.saibaba.hackathon.PoliceStation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saibaba.hackathon.R;


public class Inspector extends Fragment {
TextView sho,sdo,sp,sho_mob,sdo_mob,sp_mob;
    public Inspector() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_inspector, container, false);
        sho=root.findViewById(R.id.sho);
        sdo=root.findViewById(R.id.sdo);
        sp=root.findViewById(R.id.sp);
        sho_mob=root.findViewById(R.id.sho_mob);
        sdo_mob=root.findViewById(R.id.sdo_mob);
        sp_mob=root.findViewById(R.id.sp_mob);
        return root;
    }

}