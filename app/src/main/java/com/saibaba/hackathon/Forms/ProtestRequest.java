package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class ProtestRequest extends AppCompatActivity {
EditText oname,mobile,flat1,landmark1,city1,desc,loc,sdate,edate,stime,address;
SearchableSpinner state_spinner,district_spinner,type_spinner,structure_spinner;
    String[] district,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protest_request);
        initView();
        state = new String[]{"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh",
                "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand",
                "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Pondicherry",
                "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttaranchal", "West Bengal"};

        district = new String[]{"Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", "Bhojpur", "Buxar", "Darbhanga", "Gaya", "Gopalganj",
                "Jamui", "Jehanabad", "Kaimur", "Katihar", "Khagaria", "Kishanganj", "Lakhisarai", "Madhepura", "Madhubani", "Munger", "Muzaffarpur",
                "Nalanda", "Nawada", "Pashchim Champaran", "Patna", "Purbi Champaran", "Purnia", "Rohtas", "Saharsa", "Samastipur", "Saran", "Sheikhpura",
                "Sitamarhi", "Siwan", "Supaul", "Vaishali"};
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, state);
        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, district);

        state_spinner.setAdapter(stateAdapter);

        district_spinner.setAdapter(districtsAdapter);
        getSupportActionBar().setTitle("Protest Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initView()
    {
        type_spinner=findViewById(R.id.type_spinner);
        district_spinner=findViewById(R.id.district_spinner);
        state_spinner=findViewById(R.id.state_spinner);
        flat1=findViewById(R.id.flat1);
        landmark1=findViewById(R.id.landmark1);
        city1=findViewById(R.id.city1);
        oname=findViewById(R.id.oname);
        mobile=findViewById(R.id.mobile);
        desc=findViewById(R.id.desc);
        loc=findViewById(R.id.loc);
        sdate=findViewById(R.id.sdate);
        edate=findViewById(R.id.edate);
        stime=findViewById(R.id.stime);
        structure_spinner=findViewById(R.id.structure_spinner);
        address=findViewById(R.id.address);
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
