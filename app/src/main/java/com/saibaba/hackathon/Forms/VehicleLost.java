package com.saibaba.hackathon.Forms;


import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.Signature;
import com.saibaba.hackathon.StringVariable;


import java.util.HashMap;
import java.util.Map;

public class VehicleLost extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 9;
    private EditText regno,engno,chassisno;
private String reg,eng,chas;
private DatabaseReference db;
private Button save;
private ImageView upload;
private ProgressBar progressbar,progressBar2;
private TextView imagename;
Uri uri;
private UploadTask uploadTask;
private String mUri;
    private String signature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_lost);
        initView();
        getSupportActionBar().setTitle("Vehicle Lost");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //updating to database   ---->harsh
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkforfieldempty();
                progressbar.setVisibility(View.VISIBLE);
                save.setText("Save");

            }
        });

        //
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }

    private void checkforfieldempty() {
        //extracting string --->harsh
        reg=regno.getText().toString();
        eng=engno.getText().toString();
        chas=chassisno.getText().toString();

        //database refernce-->harsh
        db= FirebaseDatabase.getInstance().getReference().child("Vehicle Lost").child(reg);


        if(reg.equals(""))
        {
            regno.setError("EMPTY FIELD");
            regno.requestFocus();
            return;
        }
        else if(eng.equals(""))
        {
            engno.setError("EMPTY FIELD");
            engno.requestFocus();
            return;
        }
        else if(chas.equals(""))
        {
            chassisno.setError("EMPTY FIELD");
            chassisno.requestFocus();
            return;
        }
        else if(imagename.getText().toString().contains("Owner Book Copy"))
        {
            imagename.setError("Please Upload Owner Book");
            imagename.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
        save.setText("");
        updatedata();
        progressbar.setVisibility(View.GONE);
        regno.setText("");
        engno.setText("");
        chassisno.setText("");
        save.setText("Save");
    }

    private void updatedata() {
        Map<String,Object> mp=new HashMap<>();
        mp.put(StringVariable.REGISTRATION_NO,reg);
        mp.put(StringVariable.ENGINE_NO,eng);
        mp.put(StringVariable.CHASIS_NO,chas);
        mp.put(StringVariable.IMAGE,mUri);
        mp.put("Signature",signature);
        db.setValue(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(VehicleLost.this, "Vehicle Added to Stolen List", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initView()
    {
        regno=findViewById(R.id.info_reg);
        engno=findViewById(R.id.info_engine);
        chassisno=findViewById(R.id.info_chassis);
        save=findViewById(R.id.btn_vehicle_lost_save);
        progressbar=findViewById(R.id.vehicle_lost_progress);
        progressBar2=findViewById(R.id.vehicle_lostIMG_progress);
        upload=findViewById(R.id.btn_upload_ownerdetails);
        imagename=findViewById(R.id.ownerbooktext);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==GALLERY_REQUEST  && data!=null && data.getData()!=null){
//            uri=data.getData();
//            Log.e("harshuri",uri.toString());
//            progressBar2.setVisibility(View.VISIBLE);
//            upload.setVisibility(View.GONE);
//            if(uri!=null){
//                final StorageReference filereference= FirebaseStorage.getInstance().getReference().child("OWNER BOOK DETAILS").child(System.currentTimeMillis()+"");
//                uploadTask=filereference.putFile(uri);
//                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                    @Override
//                    public Task then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                        if(!task.isSuccessful())
//                        {
//                            throw task.getException();
//                        }
//                        return filereference.getDownloadUrl();
//
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//
//                        if(task.isSuccessful()){
//                            Uri downloadUri=task.getResult();
//                            mUri=downloadUri.toString();
//                            progressBar2.setVisibility(View.GONE);
//                            upload.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));
//                            upload.setVisibility(View.VISIBLE);
//                            imagename.setText(System.currentTimeMillis()+".jpg");
//                        }
//                        else{
//                            Toast.makeText(VehicleLost.this,"No Image Selected",Toast.LENGTH_LONG).show();
//                            progressBar2.setVisibility(View.GONE);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(VehicleLost.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                        progressBar2.setVisibility(View.GONE);
//                        upload.setVisibility(View.VISIBLE);
//                    }
//                });
//            }
//            else{
//                Toast.makeText(this,"No Image Selected",Toast.LENGTH_LONG).show();
//            }
//
//        }
        if(requestCode==2&&resultCode== Activity.RESULT_OK)
        {
            Log.e("harsh","reached");
            try {
                signature=data.getStringExtra("result");
            }
            catch (Exception e)
            {
                Log.e("harshexp",e.toString());
            }

            Log.e("harshsign",signature);
        }
            }
    private void uploadImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, GALLERY_REQUEST);
       Intent intent=new Intent(VehicleLost.this, Signature.class);
        intent.putExtra("harsh","VehicleLost");
        startActivityForResult(intent,2);
    }
    }
