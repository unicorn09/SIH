package com.saibaba.hackathon;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.saibaba.hackathon.Fragments.Complaints;
import com.saibaba.hackathon.Fragments.FIR;
import com.saibaba.hackathon.Fragments.LostAndFound;
import com.saibaba.hackathon.Fragments.MisssingPerson;
import com.saibaba.hackathon.Fragments.NOC;
import com.saibaba.hackathon.Fragments.Verification;

public class HomeDesc extends AppCompatActivity {
private String name;
Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_desc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=getIntent().getStringExtra("Name");
        if(name.equalsIgnoreCase("FIR"))
                fragment=new FIR();
        else if(name.equalsIgnoreCase("Complain"))
                fragment=new Complaints();
        else if(name.equalsIgnoreCase("NOC"))
            fragment=new NOC();
        else if(name.equalsIgnoreCase("Verification"))
            fragment=new Verification();
        else if(name.equalsIgnoreCase("Missing Person"))
            fragment=new MisssingPerson();
        else if(name.equalsIgnoreCase("Lost & Found"))
            fragment=new LostAndFound();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_homedesc,fragment).commit();
    }
}
