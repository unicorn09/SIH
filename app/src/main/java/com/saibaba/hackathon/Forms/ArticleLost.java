package com.saibaba.hackathon.Forms;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.saibaba.hackathon.StringVariable;
import com.saibaba.hackathon.chatbot.ModelChat;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.HashMap;
import java.util.Map;

public class ArticleLost extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 9;
    String[] station,district;
    private EditText name,address,mobile,email,date,time,desc;
    private SearchableSpinner station_spinner,district_spinner;
    private ProgressBar progressBar,progressBar2;
    private UploadTask uploadTask;
    private Uri uri;
    ImageView upload;
    private String mUri,sname,saddress,smobile,semail,sdate,stime,sdesc,sdistrict,sstation;
    private TextView imagename;
    Button save;
    private DatabaseReference db;



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


        getSupportActionBar().setTitle("Article Lost");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        district_spinner.setAdapter(districtsAdapter);
        station_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstation=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstation=parent.getItemAtPosition(0).toString();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkforfieldempty();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sdistrict=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sdistrict=parent.getItemAtPosition(0).toString();
            }
        });
    }
    private void initView() {
        district_spinner=findViewById(R.id.article_lost_spinner2);
        name=findViewById(R.id.article_lost_name);
        address=findViewById(R.id.article_lost_address);
       mobile=findViewById(R.id.article_lost_mobile);
       email=findViewById(R.id.article_lost_email);
        date=findViewById(R.id.article_lost_date);
       time=findViewById(R.id.article_lost_time);
        desc=findViewById(R.id.article_lost_desc);
        station_spinner=findViewById(R.id.article_lost_spinner1);
        progressBar2=findViewById(R.id.article_lost_progress);
        upload=findViewById(R.id.btn_upload_articlephoto);
        imagename=findViewById(R.id.articlelosttext);
        save=findViewById(R.id.article_lost_next);
        progressBar=findViewById(R.id.article_lost_progress);

    }
    private void checkforfieldempty() {
        //extracting string --->harsh
        sname=name.getText().toString();
        saddress=address.getText().toString();
        smobile=mobile.getText().toString();
        semail=email.getText().toString();
        sdate=date.getText().toString();
        stime=time.getText().toString();
        sdesc=desc.getText().toString();

        //database refernce-->harsh
        db= FirebaseDatabase.getInstance().getReference().child("Article Lost").push();

        //setting error---->
        if(sname.equals(""))
        {seterror(name);
        return;}
        else if (saddress.equals(""))
        {
            seterror(address);
            return;
        }
        else if(smobile.equals(""))
        {
            seterror(mobile);
            return;
        }
        else if(semail.equals(""))
        {
            seterror(email);
            return;
        }
        else if(sdate.equals(""))
        {
            seterror(date);
            return;
        }
        else if(stime.equals(""))
        {
            seterror(time);
            return;
        }
        else if(sdesc.equals(""))
        {
            seterror(desc);
            return;
        }
        else if(imagename.getText().toString().contains("Upload Article"))
        {
            seterror(imagename);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        save.setText("");
        updatedata();
        progressBar.setVisibility(View.GONE);
        name.setText("");
        address.setText("");
        mobile.setText("");
        email.setText("");
        date.setText("");
        time.setText("");
        desc.setText("");
        desc.setHint("Brief description");
        save.setText("Save");
    }

    private void seterror(TextView t) {
        t.setError("Field Empty");
        t.requestFocus();
    }

    private void updatedata() {
        Map<String,Object> mp=new HashMap<>();
        mp.put(StringVariable.USER_NAME,sname);
        mp.put(StringVariable.USER_ADDRESS,saddress);
        mp.put(StringVariable.USER_EMAIL,semail);
        mp.put(StringVariable.USER_DISTRICT,sdistrict);
        mp.put(StringVariable.DATE,sdate);
        mp.put(StringVariable.TIME,stime);
        mp.put(StringVariable.Complaint,sdesc);
        mp.put(StringVariable.Police_Station,sstation);
        mp.put(StringVariable.IMAGE,mUri);
        db.setValue(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(ArticleLost.this, "Article Added to Lost List", Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST  && data!=null && data.getData()!=null){
            uri=data.getData();
            Log.e("harshuri",uri.toString());
            progressBar2.setVisibility(View.VISIBLE);
            upload.setVisibility(View.GONE);
            if(uri!=null){
                final StorageReference filereference= FirebaseStorage.getInstance().getReference().child("ARTICLE LOST DETAILS").child(System.currentTimeMillis()+"");
                uploadTask=filereference.putFile(uri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        return filereference.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){
                            Uri downloadUri=task.getResult();
                            mUri=downloadUri.toString();
                            progressBar2.setVisibility(View.GONE);
                            upload.setImageDrawable(getResources().getDrawable(R.drawable.ic_verified));
                            upload.setVisibility(View.VISIBLE);
                            imagename.setText(System.currentTimeMillis()+".jpg");
                        }
                        else{
                            Toast.makeText(ArticleLost.this,"No Image Selected",Toast.LENGTH_LONG).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ArticleLost.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        progressBar2.setVisibility(View.GONE);
                        upload.setVisibility(View.VISIBLE);
                    }
                });
            }
            else{
                Toast.makeText(this,"No Image Selected",Toast.LENGTH_LONG).show();
            }

        }
    }
    private void uploadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_REQUEST);
    }
}
