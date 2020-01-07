package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class CharacterCertificate extends AppCompatActivity {
    private SearchableSpinner mrelation,mservice,mmode;
    private String[] relations;
    private String[] services;
    private String[] modes;
    TextView yes,no,male,female,other;
    EditText name,rname,purpose,dob;
    ImageView photo,identity,addressProof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_certificate);
        initView();
        getSupportActionBar().setTitle("Character Certificate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        relations=new String[]{"Select","Father","Guardian","Husband","Mother","Wife"};
        services=new String[]{"Select","Public Services","Government Services"};
        modes=new String[]{"Select","Online"};
        ArrayAdapter<String> relationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, relations);
        mrelation.setAdapter(relationAdapter);
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, services);
        mservice.setAdapter(serviceAdapter);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modes);
        mmode.setAdapter(modeAdapter);

    }
    private void initView()
    {
        mrelation=findViewById(R.id.relation_spinner);
        mservice=findViewById(R.id.service_spinner);
        mmode=findViewById(R.id.mode_spinner);
        yes=findViewById(R.id.yes);
       no=findViewById(R.id.no);
        yes=findViewById(R.id.yes);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        other=findViewById(R.id.other);
        name=findViewById(R.id.name);
        rname=findViewById(R.id.rname);
        purpose=findViewById(R.id.purpose);
       dob=findViewById(R.id.dob);
        photo=findViewById(R.id.photo);
        identity=findViewById(R.id.identity);
        addressProof=findViewById(R.id.address_proof);
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
