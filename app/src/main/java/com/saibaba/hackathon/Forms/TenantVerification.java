package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class TenantVerification extends AppCompatActivity {
EditText name,dob,rname,flat1,landmark1,city1,flat2,landmark2,city2;
TextView male,female,other;
SearchableSpinner district_spinner2,state_spinner2,station_spinner2,station_spinner,district_spinner,state_spinner,occupation_spinner,relation_spinner,purpose_spinner;
   String[] station,district,state,occupation,relation,purpose;

    CheckBox checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_verification);
        initView();

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
        station_spinner.setAdapter(stationAdapter);
        station_spinner2.setAdapter(stationAdapter);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, state);
        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, district);

       state_spinner.setAdapter(stateAdapter);

       district_spinner.setAdapter(districtsAdapter);
        state_spinner2.setAdapter(stateAdapter);

        district_spinner2.setAdapter(districtsAdapter);
        occupation=new String[]{"Academician","Accountant","Agent","Air Lines Staff","airport Staff","Architect","Artisan","Artist","Asst. Supervisor","Bank Employee","Other"};
        ArrayAdapter<String> occupationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, occupation);
        occupation_spinner.setAdapter(occupationAdapter);
       relation=new String[]{"Father","Guardian","Husband","Mother","Wife"};
        ArrayAdapter<String> relationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,relation);
        relation_spinner.setAdapter(relationAdapter);
        purpose=new String[]{"Residential","Commercial"};
        ArrayAdapter<String> purposeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, purpose);
        purpose_spinner.setAdapter(purposeAdapter);

    }
    private void initView()
    {
        name=findViewById(R.id.name);
        dob=findViewById(R.id.dob);
        rname=findViewById(R.id.rname);
        flat1=findViewById(R.id.flat1);
        landmark1=findViewById(R.id.landmark1);
        city1=findViewById(R.id.city1);
        flat2=findViewById(R.id.flat2);
        landmark2=findViewById(R.id.landmark2);
        city2=findViewById(R.id.city2);
        district_spinner=findViewById(R.id.district_spinner);
        state_spinner=findViewById(R.id.state_spinner);
        station_spinner=findViewById(R.id.station_spinner);
        district_spinner2=findViewById(R.id.district_spinner2);
        state_spinner2=findViewById(R.id.state_spinner2);
        station_spinner2=findViewById(R.id.station_spinner2);
        occupation_spinner=findViewById(R.id.occupation_spinner);
        relation_spinner=findViewById(R.id.relation_spinner);
        purpose_spinner=findViewById(R.id.purpose_spinner);
    }
}
