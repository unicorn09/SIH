package com.saibaba.hackathon.Forms;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.Signature;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterFIR extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner nature_spinner,sub_spinner,file_spinner,filesub_spinner;
    TextView present,not_present,yes,no;
    ImageView uploadapp,uploadsign;
    EditText content;
    Button basic_info_text;
    DatabaseReference baseReference;
    ArrayList<String> natureArrayList,subNatureArrayList;
    DatabaseReference databaseReference;
    ArrayAdapter<String> natureArrayAdapter,subNatureArrayAdapter;
    int victimPresent;
    int occurenceKnown;
    String signURL,natureComplaint,subNatureComplaint,contentComplaint;
    private static final String TAG = "RegisterFIR";
    private static final int REQUEST_SIGN=1;
    private static final int REQUEST_APP=2;
    HashMap<String,Object> dataHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fir);
        initView();
        getSupportActionBar().setTitle("Register FIR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Fresco.initialize(this);
    }
    private void initView(){
        baseReference=FirebaseDatabase.getInstance().getReference();
        natureArrayList=new ArrayList<>();
        subNatureArrayList=new ArrayList<>();
        natureArrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,natureArrayList);
        subNatureArrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,subNatureArrayList);
        getNatureList();
        natureArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subNatureArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nature_spinner=findViewById(R.id.nature_spinner);
        sub_spinner=findViewById(R.id.sub_spinner);
        content=findViewById(R.id.content);
        file_spinner=findViewById(R.id.file_spinner);
        filesub_spinner=findViewById(R.id.filesub_spinner);
        uploadapp=findViewById(R.id.uploadapp);
        uploadsign=findViewById(R.id.uploadsign);
        present=findViewById(R.id.present);
        not_present=findViewById(R.id.not_present);
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        basic_info_text=findViewById(R.id.basic_info_next);
        basic_info_text.setOnClickListener(this);
        nature_spinner.setAdapter(natureArrayAdapter);
        nature_spinner.setOnItemSelectedListener(this);
        sub_spinner.setAdapter(subNatureArrayAdapter);
        dataHashMap=new HashMap<>();

        not_present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                not_present.setBackgroundResource(R.drawable.bg_edittextselected);
                present.setBackgroundResource(R.drawable.bg_edittext);
                victimPresent=0;
            }
        });
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.setBackgroundResource(R.drawable.bg_edittextselected);
                not_present.setBackgroundResource(R.drawable.bg_edittext);
                victimPresent=1;
            }
        });
        present.performClick();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setBackgroundResource(R.drawable.bg_edittextselected);
                no.setBackgroundResource(R.drawable.bg_edittext);
                occurenceKnown=1;
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no.setBackgroundResource(R.drawable.bg_edittextselected);
                yes.setBackgroundResource(R.drawable.bg_edittext);
                occurenceKnown=0;
            }
        });
        yes.callOnClick();

        signURL="";
        uploadsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RegisterFIR.this, Signature.class),REQUEST_SIGN);
            }
        });

        uploadapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent,REQUEST_APP);
            }
        });

        natureComplaint=subNatureComplaint=contentComplaint="";

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

    @Override
    public void onClick(View v) {
        if(v==basic_info_text){
            getData();
            uploadDataToFirebase();
        }
    }

    private void getNatureList(){
        databaseReference=baseReference.child("nature-of-complaint");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        natureArrayList.add(dataSnapshot1.getKey());
                    }
                }
                Log.d(TAG, "onDataChange: "+natureArrayList);
                natureArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subNatureArrayList.clear();
        databaseReference.child(natureArrayList.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        subNatureArrayList.add(dataSnapshot1.getKey());
                    }
                    subNatureArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+requestCode+" result code "+resultCode);
        if(requestCode==REQUEST_SIGN&&resultCode== Activity.RESULT_OK){
            try{
                signURL=data.getStringExtra("result");
                Log.d(TAG, "onActivityResult: "+signURL);
                if(signURL!=""){
                    Picasso.get().load(signURL).into(uploadsign);
                }
            }catch (Exception e){
                Log.e(TAG, "onActivityResult: "+e.getMessage() );
            }
        }else if(requestCode==REQUEST_APP&&resultCode==Activity.RESULT_OK){
                Uri uri=data.getData();
            Log.d(TAG, "onActivityResult: "+uri);
        }
    }

    private void getData(){
        natureComplaint=nature_spinner.getSelectedItem().toString();
        subNatureComplaint=sub_spinner.getSelectedItem().toString();
        contentComplaint=content.getText().toString();
        if(contentComplaint.length()==0){
            content.setError("can't be empty");
            content.requestFocus();
        }
    }

    private void uploadDataToFirebase(){
        dataHashMap.put("accepted",0);
        HashMap<String,Object> childDataHashMap;
        childDataHashMap=new HashMap<>();
        childDataHashMap.put("state","uttar pradesh");
        childDataHashMap.put("district","agra");
        dataHashMap.put("address",childDataHashMap);
        baseReference.child("fir").child(String.valueOf(System.currentTimeMillis())).setValue(dataHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: data uploaded ");
            }
        });
    }

}
