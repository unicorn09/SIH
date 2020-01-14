package com.saibaba.hackathon.Forms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.saibaba.hackathon.Adapters.ModelPersonalDetails;
import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.Serializable;

public class PersonalDetails extends AppCompatActivity {
EditText dname,dage,dphone,demail,ddob,dflat1,dlandmark1,dcity1,dflat2,dlandmark2,dcity2;
ImageView dphoto;
SearchableSpinner ddistrict_spinner,dstate_spinner,dstation_spinner,ddistrict_spinner2,dstate_spinner2,dstation_spinner2;
TextView male,female,others;
String nextactivity,sdistrict,sstate,sstation,sstate2,sdistrict2,sstation2,sname,sage,sphone,semail,sdob,sfalt1,sflat2,slandmark1,slandmark2,scity1,scity2,imageurl,gender,sadd,sadd2;
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
                gettext();
                ModelPersonalDetails obj=new ModelPersonalDetails(sname,sage,gender,sadd,sdob,semail,sphone,sstate,sdistrict,sstation,sstate2,sdistrict2,sstation2,sadd2);
                if(nextactivity.equalsIgnoreCase("NOC PROCESSION"))
                    startActivity(new Intent(PersonalDetails.this,ProcessionRequest.class));
                else if(nextactivity.equalsIgnoreCase("NOC PROTEST"))
                    startActivity(new Intent(PersonalDetails.this,ProtestRequest.class));
                else if(nextactivity.equalsIgnoreCase("NOC EVENT"))
                    startActivity(new Intent(PersonalDetails.this,EventPerformance.class));
                else if(nextactivity.equalsIgnoreCase("fir registry"))
                {   Intent i=new Intent(PersonalDetails.this,RegisterFIR.class);
                    Gson gson = new Gson();
                    String DataObjectAsAString = gson.toJson(obj);
                    i.putExtra("object",DataObjectAsAString);
                    startActivity(i);}
                else
                    startActivity(new Intent(PersonalDetails.this,FilmShooting.class));
            }
        });
        ddistrict_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sdistrict=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sdistrict=parent.getItemAtPosition(0).toString();
            }
        });
        ddistrict_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sdistrict2=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sdistrict2=parent.getItemAtPosition(0).toString();
            }
        });
        dstate_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstate=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstate=parent.getItemAtPosition(0).toString();
            }
        });
        dstate_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstate2=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstate2=parent.getItemAtPosition(0).toString();
            }
        });
        dstation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstation=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstation=parent.getItemAtPosition(0).toString();
            }
        });
        dstation_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstation2=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstation2=parent.getItemAtPosition(0).toString();
            }
        });
        dcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    dflat2.setText(dflat1.getText().toString());
                    dcity2.setText(dcity1.getText().toString());
                    dlandmark2.setText(dlandmark1.getText().toString());

                }
                else
                {
                    dflat2.setText("");
                    dcity2.setText("");
                    dlandmark2.setText("");
                }

            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "male";
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "female";
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittext));

            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "other";
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
            }
        });
    }

    private void gettext() {
        sname=dname.getText().toString();
        sage=dage.getText().toString();
        sdob=ddob.getText().toString();
        semail=demail.getText().toString();
        sphone=dphone.getText().toString();
        sfalt1=dflat1.getText().toString();
        slandmark1=dlandmark1.getText().toString();
        scity2=dcity1.getText().toString();
        sflat2=dflat2.getText().toString();
        slandmark2=dlandmark2.getText().toString();
        scity2=dcity2.getText().toString();
        sadd=sfalt1+" "+slandmark1+" "+scity1;
        sadd2=sflat2+" "+slandmark2+" "+scity2;
    }

    private void initView()
    {

        dname=findViewById(R.id.dname);
        dage=findViewById(R.id.pd_age);
        ddob=findViewById(R.id.pd_dob);
        male=findViewById(R.id.pd_male);
        female=findViewById(R.id.pd_female);
        others=findViewById(R.id.pd_other);
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
    private void seterror(TextView t) {
        t.setError("Field Empty");
        t.requestFocus();
    }
}
