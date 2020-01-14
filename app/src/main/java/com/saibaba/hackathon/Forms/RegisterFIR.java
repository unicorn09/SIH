package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class RegisterFIR extends AppCompatActivity implements View.OnClickListener {
Spinner nationality_spinner,nature_spinner,sub_spinner,file_spinner,filesub_spinner;
TextView present,not_present,yes,no;
ImageView uploadapp,uploadsign;
EditText content;
Button basic_info_text;
FirebaseDatabase firebaseDatabase;
ArrayList<String> natureArrayList;
DatabaseReference databaseReference;
ArrayAdapter<String> natureArrayAdapter;

    private static final String TAG = "RegisterFIR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fir);
        initView();
        getSupportActionBar().setTitle("Register FIR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initView()
    {
        firebaseDatabase=FirebaseDatabase.getInstance();
        nature_spinner=findViewById(R.id.nature_spinner);
        sub_spinner=findViewById(R.id.sub_spinner);
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
        natureArrayList=new ArrayList<>();
        natureArrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,natureArrayList);
        natureArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nature_spinner.setAdapter(natureArrayAdapter);
        getNatureList();
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
        if((Button)v==basic_info_text){

        }
    }

    private void getNatureList(){
        databaseReference=firebaseDatabase.getReference().child("nature-of-complaint");
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

}
