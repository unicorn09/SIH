package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.saibaba.hackathon.R;

public class VehicleLost extends AppCompatActivity {
EditText regno,engno,chassisno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_vehicle_lost);
        initView();
        getSupportActionBar().setTitle("Vehicle Lost");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initView()
    {
        regno=findViewById(R.id.info_reg);
        engno=findViewById(R.id.info_reg2);
        chassisno=findViewById(R.id.info_chassis);
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
