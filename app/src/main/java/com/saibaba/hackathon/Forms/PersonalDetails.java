package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
CheckBox dcheck;
    String[] station,district,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
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
        dstation_spinner.setAdapter(stationAdapter);
        dstation_spinner2.setAdapter(stationAdapter);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, state);
        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, district);

        dstate_spinner.setAdapter(stateAdapter);

        ddistrict_spinner.setAdapter(districtsAdapter);
        dstate_spinner2.setAdapter(stateAdapter);

        ddistrict_spinner2.setAdapter(districtsAdapter);
    }
    private void initView()
    {

        dname=findViewById(R.id.dname);
        dage=findViewById(R.id.dage);
        ddob=findViewById(R.id.ddob);
        dmale=findViewById(R.id.dmale);
        dfemale=findViewById(R.id.dfemale);
        dother=findViewById(R.id.dother);
        dphone=findViewById(R.id.dphone);
        demail=findViewById(R.id.demail);
        dflat1=findViewById(R.id.dflat1);
        dlandmark1=findViewById(R.id.dlandmark1);
        dcity1=findViewById(R.id.dcity1);
        dflat2=findViewById(R.id.dflat2);
        dlandmark2=findViewById(R.id.dlandmark2);
        dcity2=findViewById(R.id.dcity2);
        ddistrict_spinner=findViewById(R.id.ddistrict_spinner);
        dstate_spinner=findViewById(R.id.dstate_spinner);
        dstation_spinner=findViewById(R.id.dstation_spinner);
        ddistrict_spinner2=findViewById(R.id.ddistrict_spinner2);
        dstate_spinner2=findViewById(R.id.dstate_spinner2);
        dstation_spinner2=findViewById(R.id.dstation_spinner2);
        dcheck=findViewById(R.id.dcheck);
       dphoto=findViewById(R.id.dphoto);

    }
}
