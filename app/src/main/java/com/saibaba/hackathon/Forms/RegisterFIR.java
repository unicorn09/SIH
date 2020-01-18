package com.saibaba.hackathon.Forms;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.saibaba.hackathon.Adapters.ModelPersonalDetails;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.Signature;
import com.saibaba.hackathon.StringVariable;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterFIR extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner nature_spinner,sub_spinner,file_spinner,filesub_spinner;
    TextView present,not_present,yes,no;
    ImageView uploadsign;
    EditText content;
    Button basic_info_text;
    DatabaseReference baseReference;
    ArrayList<String> natureArrayList,subNatureArrayList;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    ArrayAdapter<String> natureArrayAdapter,subNatureArrayAdapter;
    int victimPresent;
    int occurenceKnown;
    ImageView mic;
    String signURL,natureComplaint,subNatureComplaint,contentComplaint,cont="";
    private static final String TAG = "RegisterFIR";
    private static final int REQUEST_SIGN=1;
    HashMap<String,Object> dataHashMap;
    private Dialog mdialog;
    private Button yesbtn;
    private ImageView nobtn;
    String lan[]={"bn-IN","en-IN","gu-IN","ta-IN","te-IN","ur-IN","hi-IN","ml-IN","mr-IN","kn-IN"};
    String lan1;
    private Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fir);
        initView();
        getSupportActionBar().setTitle("Register FIR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mdialog=new Dialog(this);


        Fresco.initialize(this);
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.setContentView(R.layout.popup_language);
                try {
                    mdialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                    mdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                } catch (Exception e) {

                }
                mdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mdialog.show();
                mdialog.setCanceledOnTouchOutside(false);
                mdialog.setCancelable(false);
                yesbtn = mdialog.findViewById(R.id.dialog_submit);
                nobtn = mdialog.findViewById(R.id.close);
                spinner=mdialog.findViewById(R.id.languagespinner);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterFIR.this,
                        android.R.layout.simple_spinner_item,lan);
                spinner.setAdapter(adapter);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        lan1=parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mdialog.dismiss();
                        Toast.makeText(RegisterFIR.this,"Language Selected",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,lan1);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lan1);

                        if (intent.resolveActivity(RegisterFIR.this.getPackageManager()) != null) {
                            startActivityForResult(intent, 10);
                        } else {
                            Toast.makeText(RegisterFIR.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mdialog.dismiss();

                    }
                });

            }
        });
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
        uploadsign=findViewById(R.id.uploadsign);
        present=findViewById(R.id.present);
        not_present=findViewById(R.id.not_present);
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        basic_info_text=findViewById(R.id.basic_info_next);
        progressBar=findViewById(R.id.info_progress);
        basic_info_text.setOnClickListener(this);
        nature_spinner.setAdapter(natureArrayAdapter);
        nature_spinner.setOnItemSelectedListener(this);
        sub_spinner.setAdapter(subNatureArrayAdapter);
        mic=findViewById(R.id.registerfir_mic);
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
                   // Picasso.get().load(signURL).into(uploadsign);
                }
            }catch (Exception e){
                Log.e(TAG, "onActivityResult: "+e.getMessage() );
            }
        }
        if (requestCode==10&&resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            cont=content.getText().toString()+" "+(result.get(0));
            content.setText("");
            content.setText(cont);
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
        progressBar.setVisibility(View.VISIBLE);
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
        dataHashMap.put("content",contentComplaint);
        dataHashMap.put("crime-sub-type",subNatureComplaint);
        dataHashMap.put("date",timestamp);
        dataHashMap.put("fir-no",timestamp);
        dataHashMap.put("is-crime-present",occurenceKnown);
        dataHashMap.put("is-victim-present",victimPresent);
        dataHashMap.put("nature-of-complaint",natureComplaint);
        dataHashMap.put("signature",signatureMap);
        dataHashMap.put("status","pending");

        baseReference.child("fir").child(String.valueOf(timestamp)).setValue(dataHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: fir uploaded");
                progressBar.setVisibility(View.GONE);
                showToast("Fir registered");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: failed");
                showToast("Some error occurred");
            }
        });
        Log.d(TAG, "uploadDataToFirebase: user uid is "+FirebaseAuth.getInstance().getCurrentUser().getUid());
        baseReference.child(StringVariable.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("fir").child(""+timestamp).setValue("0");
        baseReference.child("police-station").child(modelPersonalDetails.getPolice()).child("fir").child(String.valueOf(timestamp)).setValue("0");
    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
