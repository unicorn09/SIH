package com.saibaba.hackathon.PoliceStation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saibaba.hackathon.R;

import static com.saibaba.hackathon.PoliceStation.Officers.*;

public class Officers extends Fragment {
    private static ViewPagerAdapter viewPagerAdapter;

    VerticalViewPager verticalViewPager;
    public Officers() {

    }

      @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_officers, container, false);
        verticalViewPager=v.findViewById(R.id.viewPager_officers);
        viewPagerAdapter =new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new SP(),"Police Station");
        viewPagerAdapter.addFragment(new Inspector(),"Notices");
        viewPagerAdapter.addFragment(new SubIns(),"Notices");
        verticalViewPager.setAdapter(viewPagerAdapter);

        return v;
    }
}
