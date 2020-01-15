package com.saibaba.hackathon.Forms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.saibaba.hackathon.Adapters.ModelPersonalDetails;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.Signature;
import com.saibaba.hackathon.StringVariable;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.HashMap;

public class ProcessionRequest extends AppCompatActivity {

    private static final String TAG = "ProcessionRequest";

    EditText oname,mobile,flat1,landmark1,city1,desc,sdate,edate,stime,saddress,eaddress,paddress,oaddress,time,names,crowd;
    SearchableSpinner state_spinner,district_spinner,type_spinner;
    Button uploadButton;
    ImageView signUpload;
    String[] district,state;
    ProgressBar progressBar;
    HashMap<String,Object> dataHashMap;
    DatabaseReference baseReference;
    String instituionName,flatno,landmark,city,address,stateName,districtName,timeLimit,majorParticipants,description,endDate,startDate,crowdDetails,mobileNumber,startAdd,endAdd,majorAdd,otherAdd,signURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procession_request);
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
        getSupportActionBar().setTitle("Procession Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        baseReference= FirebaseDatabase.getInstance().getReference();
        dataHashMap=new HashMap<>();
        signURL="";
        progressBar=findViewById(R.id.info_progress);
        type_spinner=findViewById(R.id.type_spinner);
        district_spinner=findViewById(R.id.district_spinner);
        state_spinner=findViewById(R.id.state_spinner);
        flat1=findViewById(R.id.flat1);
        landmark1=findViewById(R.id.landmark1);
        city1=findViewById(R.id.city1);
        oname=findViewById(R.id.oname);
        mobile=findViewById(R.id.mobile);
        desc=findViewById(R.id.desc);
        signUpload=findViewById(R.id.uploadsign);
        sdate=findViewById(R.id.sdate);
        edate=findViewById(R.id.edate);
        stime=findViewById(R.id.stime);
        time=findViewById(R.id.time);
        names=findViewById(R.id.names);
        crowd=findViewById(R.id.crowd);
        saddress=findViewById(R.id.saddress);
        eaddress=findViewById(R.id.eaddress);
        paddress=findViewById(R.id.paddress);
        oaddress=findViewById(R.id.oaddress);
        uploadButton=findViewById(R.id.basic_info_next);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        signUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ProcessionRequest.this, Signature.class),1);
            }
        });
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

    private void getData(){
        instituionName=oname.getText().toString();
        flatno = flat1.getText().toString();
        landmark=landmark1.getText().toString();
        city=city1.getText().toString();
        stateName=state_spinner.getSelectedItem().toString();
        districtName=district_spinner.getSelectedItem().toString();
        timeLimit=time.getText().toString();
        majorParticipants=names.getText().toString();
        description=desc.getText().toString();
        endDate=edate.getText().toString();
        startDate=sdate.getText().toString();
        crowdDetails=crowd.getText().toString();
        mobileNumber=mobile.getText().toString();
        startAdd=saddress.getText().toString();
        endAdd=eaddress.getText().toString();
        majorAdd=paddress.getText().toString();
        otherAdd=oaddress.getText().toString();
        checkEmpty();
    }

    private void setError(EditText editText){
        editText.setError("can't be empty");
        editText.requestFocus();
    }

    private void checkEmpty(){
        if(instituionName.equals("")){
            setError(oname);
            return;
        }else if(flatno.equals("")){
            setError(flat1);
            return;
        }else if(landmark.equals("")){
            setError(landmark1);
            return;
        }else if(city.equals("")){
            setError(city1);
            return;
        }else if(timeLimit.equals("")){
            setError(time);
            return;
        }else if(majorParticipants.equals("")){
            setError(names);
            return;
        }else if(description.equals("")){
            setError(desc);
            return;
        }else if(endDate.equals("")){
            setError(edate);
            return;
        }else if(startDate.equals("")){
            setError(sdate);
            return;
        }else if(crowdDetails.equals("")){
            setError(crowd);
            return;
        }else if(mobileNumber.equals("")){
            setError(mobile);
            return;
        }else if(startAdd.equals("")){
            setError(saddress);
            return;
        }else if(endAdd.equals("")){
            setError(eaddress);
            return;
        }else if(majorAdd.equals("")){
            setError(paddress);
            return;
        }else if(otherAdd.equals("")){
            setError(oaddress);
            return;
        }else if(signURL.equals("")){
            showToast("Sign not uploaded");
            return;
        }
        address=flatno+" , "+landmark+" , "+city;
        uploadData();
    }

    private void uploadData(){
        long timestamp=System.currentTimeMillis();

        HashMap<String,Object> addressHashmap;
        Intent intent=getIntent();
        String dataObjectAsString=intent.getStringExtra("object");
        Gson gson=new Gson();
        ModelPersonalDetails modelPersonalDetails=gson.fromJson(dataObjectAsString,new ModelPersonalDetails().getClass());

        //complainant hashmap
        HashMap<String,Object> complainantHashmap=new HashMap<>();

        //permanent address hasmap
        addressHashmap=new HashMap<>();
        addressHashmap.put("address",modelPersonalDetails.getAddress2());
        addressHashmap.put("district",modelPersonalDetails.getDistrict2());
        addressHashmap.put("state",modelPersonalDetails.getState2());
        addressHashmap.put("ps",modelPersonalDetails.getPolice2());

        complainantHashmap.put("perm-address",addressHashmap);

        //address hashmap
        addressHashmap.put("address",modelPersonalDetails.getAddress());
        addressHashmap.put("district",modelPersonalDetails.getDistrict());
        addressHashmap.put("state",modelPersonalDetails.getState());
        addressHashmap.put("ps",modelPersonalDetails.getPolice());

        complainantHashmap.put("address",addressHashmap);

        //setting dob
        complainantHashmap.put("dob",modelPersonalDetails.getDOB());

        //setting email
        complainantHashmap.put("email",modelPersonalDetails.getEmail());

        //setting name
        complainantHashmap.put("name",modelPersonalDetails.getName());

        //setting phone
        complainantHashmap.put("phone",modelPersonalDetails.getMobile());

        //setting sex
        complainantHashmap.put("sex",modelPersonalDetails.getGender());

        HashMap<String,Object> signatureMap=new HashMap<>();
        signatureMap.put("type","image/jpeg");
        signatureMap.put("url",signURL);

        dataHashMap.put("accepted",0);
        dataHashMap.put("complainant",complainantHashmap);
        dataHashMap.put("signature",signatureMap);
        dataHashMap.put("institution_name",instituionName);
        dataHashMap.put("state",stateName);
        dataHashMap.put("district",districtName);
        dataHashMap.put("timelimit",timeLimit);
        dataHashMap.put("majorParticipants",majorParticipants);
        dataHashMap.put("description",description);
        dataHashMap.put("endDate",endDate);
        dataHashMap.put("startDate",startDate);
        dataHashMap.put("crowd",crowdDetails);
        dataHashMap.put("mobile",mobileNumber);
        dataHashMap.put("startAddress",startAdd);
        dataHashMap.put("endAddress",endAdd);
        dataHashMap.put("majorAddress",majorAdd);
        dataHashMap.put("otherAddress",otherAdd);
        dataHashMap.put("id",timestamp);
        dataHashMap.put("date",timestamp);
        dataHashMap.put("status","pending");

        baseReference.child("NOC").child(StringVariable.PROCESSION_REQUEST).child(String.valueOf(timestamp)).setValue(dataHashMap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: failed");
                showToast("some error occurred");
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: data uploaded");
                showToast("Procession request submitted");
                finish();
            }
        });
        baseReference.child(StringVariable.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("NOC").child(StringVariable.PROCESSION_REQUEST).child(String.valueOf(timestamp)).setValue("0");

    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&requestCode==RESULT_OK){
            try{
                signURL=data.getStringExtra("result");
                Log.d(TAG, "onActivityResult: "+signURL);
                if(signURL!=""){
                    Picasso.get().load(signURL).into(signUpload);
                }
            }catch (Exception e){
                Log.e(TAG, "onActivityResult: "+e.getMessage() );
            }
        }
    }
}
