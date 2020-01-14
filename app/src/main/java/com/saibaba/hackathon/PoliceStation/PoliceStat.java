package com.saibaba.hackathon.PoliceStation;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.saibaba.hackathon.R;

public class PoliceStat extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_stat);
        tabLayout = findViewById(R.id.tabLayout_blogs);
        viewPager = findViewById(R.id.viewPager_blogs);
        getSupportActionBar().setTitle("Police Station Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Officers(),"Police Station");
        viewPagerAdapter.addFragment(new Notices(),"Notices");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
