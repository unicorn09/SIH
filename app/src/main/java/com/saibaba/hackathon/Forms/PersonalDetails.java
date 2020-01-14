package com.saibaba.hackathon.Forms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class PersonalDetails extends AppCompatActivity {
EditText dname,dage,dphone,demail,ddob,dflat1,dlandmark1,dcity1,dflat2,dlandmark2,dcity2;
ImageView dphoto;
SearchableSpinner ddistrict_spinner,dstate_spinner,dstation_spinner,ddistrict_spinner2,dstate_spinner2,dstation_spinner2;
TextView dmale,dfemale,dother;
String nextactivity;
CheckBox dcheck;
Button next;
    String[] station,district,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        state = new String[]{"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh",
                "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand",
                "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Pondicherry",
                "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttaranchal", "West Bengal"};

        district = new String[]{"Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", "Bhojpur", "Buxar", "Darbhanga", "Gaya", "Gopalganj",
                "Jamui", "Jehanabad", "Kaimur", "Katihar", "Khagaria", "Kishanganj", "Lakhisarai", "Madhepura", "Madhubani", "Munger", "Muzaffarpur",
                "Nalanda", "Nawada", "Pashchim Champaran", "Patna", "Purbi Champaran", "Purnia", "Rohtas", "Saharsa", "Samastipur", "Saran", "Sheikhpura",
                "Sitamarhi", "Siwan", "Supaul", "Vaishali"};
        station=new String[]{"Agamkuan","Bihta","Barh","Digha","Dhanarua","Hathidah","Chowk","Maner","Punpun","S K Puri","Sahpur"};
        ArrayAdapter<String> stationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, station);
        dstation_spinner.setAdapter(stationAdapter);
        dstation_spinner2.setAdapter(stationAdapter);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, state);
        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, district);

        dstate_spinner.setAdapter(stateAdapter);

        ddistrict_spinner.setAdapter(districtsAdapter);
        dstate_spinner2.setAdapter(stateAdapter);

        ddistrict_spinner2.setAdapter(districtsAdapter);
        nextactivity=getIntent().getStringExtra("NOC");
        if(nextactivity.contains("NOC"))
            getSupportActionBar().setTitle("NOC PERSONAL DETAILS");
        else
            getSupportActionBar().setTitle("FIR PERSONAL DETAILS");
        next=findViewById(R.id.pd_personal_details_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nextactivity.equalsIgnoreCase("NOC PROCESSION"))
                    startActivity(new Intent(PersonalDetails.this,ProcessionRequest.class));
                else if(nextactivity.equalsIgnoreCase("NOC PROTEST"))
                    startActivity(new Intent(PersonalDetails.this,ProtestRequest.class));
                else if(nextactivity.equalsIgnoreCase("NOC EVENT"))
                    startActivity(new Intent(PersonalDetails.this,EventPerformance.class));
                else if(nextactivity.equalsIgnoreCase("fir registry"))
                    startActivity(new Intent(PersonalDetails.this,RegisterFIR.class));
                else
                    startActivity(new Intent(PersonalDetails.this,FilmShooting.class));
            }
        });
    }
    private void initView()
    {

        dname=findViewById(R.id.dname);
        dage=findViewById(R.id.pd_age);
        ddob=findViewById(R.id.pd_dob);
        dmale=findViewById(R.id.pd_male);
        dfemale=findViewById(R.id.pd_female);
        dother=findViewById(R.id.pd_other);
        dphone=findViewById(R.id.pd_phone);
        demail=findViewById(R.id.pd_email);
        dflat1=findViewById(R.id.pd_flat1);
        dlandmark1=findViewById(R.id.pd_landmark1);
        dcity1=findViewById(R.id.pd_city1);
        dflat2=findViewById(R.id.pd_flat2);
        dlandmark2=findViewById(R.id.pd_landmark2);
        dcity2=findViewById(R.id.pd_city2);
        ddistrict_spinner=findViewById(R.id.pd_district_spinner);
        dstate_spinner=findViewById(R.id.pd_state_spinner);
        dstation_spinner=findViewById(R.id.pd_station_spinner);
        ddistrict_spinner2=findViewById(R.id.pd_district_spinner2);
        dstate_spinner2=findViewById(R.id.pd_state_spinner2);
        dstation_spinner2=findViewById(R.id.pd_station_spinner2);
        dcheck=findViewById(R.id.pd_dcheck);
       dphoto=findViewById(R.id.pd_dphoto);

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
