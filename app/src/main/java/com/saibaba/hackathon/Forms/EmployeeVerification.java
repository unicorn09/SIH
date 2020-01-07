package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class EmployeeVerification extends AppCompatActivity {
TextView govt_org,private_org,male,female,other;
    EditText flat1,landmark1,city1,flat2,landmark2,city2,pbirth;
EditText dname,name,dob,rname;
String []relation;
    String[] station,district,state;

CheckBox checkbox;
SearchableSpinner relation_spinner;
    SearchableSpinner district_spinner2,state_spinner2,station_spinner2,station_spinner,district_spinner,state_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_verification);
        initView();
        getSupportActionBar().setTitle("Employee Verification");
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
        station_spinner.setAdapter(stationAdapter);
        station_spinner2.setAdapter(stationAdapter);
    ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, state);
    ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, district);

       state_spinner.setAdapter(stateAdapter);

       district_spinner.setAdapter(districtsAdapter);
        state_spinner2.setAdapter(stateAdapter);

        district_spinner2.setAdapter(districtsAdapter);
        relation=new String[]{"Father","Guardian","Husband","Mother","Wife"};
        ArrayAdapter<String> relationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,relation);
        relation_spinner.setAdapter(relationAdapter);
    }
    private void initView()
    {
        govt_org=findViewById(R.id.govt_org);
        private_org=findViewById(R.id.private_org);
        dname=findViewById(R.id.dname);
        name=findViewById(R.id.name);
        dob=findViewById(R.id.dob);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        other=findViewById(R.id.other);
        relation_spinner=findViewById(R.id.relation_spinner);
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
        checkbox=findViewById(R.id.checkbox);
        pbirth=findViewById(R.id.pbirth);
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
