package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class FilmShooting extends AppCompatActivity {
EditText ptype,splace,rspace;
    EditText lname,mobile,larea,flat1,city1,landmark1,sdate,edate,stime,ptime,syn;
    TextView yes,no;
    String[] station,district,state;

    SearchableSpinner nature_spinner,type_spinner,state_spinner,district_spinner,station_spinner,tevent_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_shooting);
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

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, state);
        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, district);

        state_spinner.setAdapter(stateAdapter);

        district_spinner.setAdapter(districtsAdapter);
    }
    private  void initView()
    {
        district_spinner=findViewById(R.id.district_spinner);
        state_spinner=findViewById(R.id.state_spinner);
        station_spinner=findViewById(R.id.station_spinner);
        nature_spinner=findViewById(R.id.nature_spinner);
        type_spinner=findViewById(R.id.type_spinner);
        tevent_spinner=findViewById(R.id.tevent_spinner);
        flat1=findViewById(R.id.flat1);
        landmark1=findViewById(R.id.landmark1);
        city1=findViewById(R.id.city1);
        lname=findViewById(R.id.lname);
        mobile=findViewById(R.id.mobile);
        larea=findViewById(R.id.larea);

        sdate=findViewById(R.id.sdate);
        edate=findViewById(R.id.edate);
        stime=findViewById(R.id.stime);
        ptime=findViewById(R.id.ptime);

        syn=findViewById(R.id.syn);
        yes=findViewById(R.id.edate);
        no=findViewById(R.id.no);
        ptype=findViewById(R.id.ptype);
        splace=findViewById(R.id.splace);
        rspace=findViewById(R.id.rspace);
    }
}
