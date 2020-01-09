package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class ArticleLost extends AppCompatActivity {
    String[] station,district;
    EditText name,address,mobile,email,date,time,desc;
    ImageView doc;
    SearchableSpinner station_spinner,district_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_lost);
        initView();
        district = new String[]{"Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", "Bhojpur", "Buxar", "Darbhanga", "Gaya", "Gopalganj",
                "Jamui", "Jehanabad", "Kaimur", "Katihar", "Khagaria", "Kishanganj", "Lakhisarai", "Madhepura", "Madhubani", "Munger", "Muzaffarpur",
                "Nalanda", "Nawada", "Pashchim Champaran", "Patna", "Purbi Champaran", "Purnia", "Rohtas", "Saharsa", "Samastipur", "Saran", "Sheikhpura",
                "Sitamarhi", "Siwan", "Supaul", "Vaishali"};
        station=new String[]{"Agamkuan","Bihta","Barh","Digha","Dhanarua","Hathidah","Chowk","Maner","Punpun","S K Puri","Sahpur"};
        ArrayAdapter<String> stationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, station);
        station_spinner.setAdapter(stationAdapter);

        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, district);



        district_spinner.setAdapter(districtsAdapter);
    }
    private void initView() {
        district_spinner=findViewById(R.id.district_spinner);
       name=findViewById(R.id.name);
        address=findViewById(R.id.address);
       mobile=findViewById(R.id.mobile);
       email=findViewById(R.id.email);
        date=findViewById(R.id.date);
       time=findViewById(R.id.time);
        desc=findViewById(R.id.desc);
        doc=findViewById(R.id.doc);
        station_spinner=findViewById(R.id.station_spinner);
    }
    }