package com.saibaba.hackathon.Forms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.saibaba.hackathon.Adapters.ModelPersonalDetails;
import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonalDetails extends AppCompatActivity {
EditText dname,dage,dphone,demail,ddob,dflat1,dlandmark1,dcity1,dflat2,dlandmark2,dcity2;
ImageView dphoto;
SearchableSpinner ddistrict_spinner,dstate_spinner,dstation_spinner,ddistrict_spinner2,dstate_spinner2,dstation_spinner2;
TextView male,female,others;
String nextactivity,sdistrict,sstate,sstation,sstate2,sdistrict2,sstation2,sname,sage,sphone,semail,sdob,sfalt1,sflat2,slandmark1,slandmark2,scity1,scity2,imageurl,gender,sadd,sadd2;
CheckBox dcheck;
Button next;
List<String> statelist,districtlist,districtlist2,stationlist,stationlist2;
DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("places").child("State");
    String[] station,district,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        initView();

         final ArrayAdapter<String> stationAdapter = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, stationlist);
         final ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, districtlist);
        final ArrayAdapter<String> stationAdapter2 = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, stationlist2);
        final ArrayAdapter<String> districtsAdapter2 = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, districtlist2);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         station=new String[]{"Agamkuan","Bihta","Barh","Digha","Dhanarua","Hathidah","Chowk","Maner","Punpun","S K Puri","Sahpur"};
         nextactivity=getIntent().getStringExtra("NOC");
        if(nextactivity.contains("NOC"))
            getSupportActionBar().setTitle("NOC PERSONAL DETAILS");
        else
            getSupportActionBar().setTitle("FIR PERSONAL DETAILS");
        next=findViewById(R.id.pd_personal_details_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettext();
                ModelPersonalDetails obj=new ModelPersonalDetails(sname,sage,gender,sadd,sdob,semail,sphone,sstate,sdistrict,sstation,sstate2,sdistrict2,sstation2,sadd2);
                if(nextactivity.equalsIgnoreCase("NOC PROCESSION"))
                    startActivity(new Intent(PersonalDetails.this,ProcessionRequest.class));
                else if(nextactivity.equalsIgnoreCase("NOC PROTEST"))
                    startActivity(new Intent(PersonalDetails.this,ProtestRequest.class));
                else if(nextactivity.equalsIgnoreCase("NOC EVENT"))
                    startActivity(new Intent(PersonalDetails.this,EventPerformance.class));
                else if(nextactivity.equalsIgnoreCase("fir registry"))
                {   Intent i=new Intent(PersonalDetails.this,RegisterFIR.class);
                    Gson gson = new Gson();
                    String DataObjectAsAString = gson.toJson(obj);
                    i.putExtra("object",DataObjectAsAString);
                    startActivity(i);}
                else
                    startActivity(new Intent(PersonalDetails.this,FilmShooting.class));
            }
        });
        ddistrict_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sdistrict=parent.getItemAtPosition(position).toString();
                databaseReference.child(sstate).child(sdistrict).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        stationlist.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            stationlist.add(dataSnapshot1.getValue().toString());
                        }
                        dstation_spinner.setAdapter(stationAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sdistrict=parent.getItemAtPosition(0).toString();
            }
        });
        ddistrict_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sdistrict2=parent.getItemAtPosition(position).toString();
                databaseReference.child(sstate2).child(sdistrict2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        stationlist2.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            stationlist2.add(dataSnapshot1.getValue().toString());
                        }
                        dstation_spinner2.setAdapter(stationAdapter2);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sdistrict2=parent.getItemAtPosition(0).toString();
            }
        });
        dstate_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstate=parent.getItemAtPosition(position).toString();
                databaseReference.child(sstate).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            districtlist.add(dataSnapshot1.getKey().toString());
                            Log.e("district",dataSnapshot1.getKey().toString());
                        }
                        ddistrict_spinner.setAdapter(districtsAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstate=parent.getItemAtPosition(0).toString();
            }
        });
        dstate_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstate2 = parent.getItemAtPosition(position).toString();
                databaseReference.child(sstate2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            districtlist2.add(dataSnapshot1.getKey().toString());
                            ddistrict_spinner2.setAdapter(districtsAdapter2);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, districtlist);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstate2=parent.getItemAtPosition(0).toString();
            }
        });
        dstation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstation=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstation=parent.getItemAtPosition(0).toString();
            }
        });
        dstation_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sstation2=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sstation2=parent.getItemAtPosition(0).toString();
            }
        });
        dcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    dflat2.setText(dflat1.getText().toString());
                    dcity2.setText(dcity1.getText().toString());
                    dlandmark2.setText(dlandmark1.getText().toString());

                }
                else
                {
                    dflat2.setText("");
                    dcity2.setText("");
                    dlandmark2.setText("");
                }

            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "male";
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "female";
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittext));

            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "other";
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
            }
        });
        others.performClick();
    }


    private void gettext() {
        sname=dname.getText().toString();
        sage=dage.getText().toString();
        sdob=ddob.getText().toString();
        semail=demail.getText().toString();
        sphone=dphone.getText().toString();
        sfalt1=dflat1.getText().toString();
        slandmark1=dlandmark1.getText().toString();
        scity1=dcity1.getText().toString();
        sflat2=dflat2.getText().toString();
        slandmark2=dlandmark2.getText().toString();
        scity2=dcity2.getText().toString();
        sadd=sfalt1+" "+slandmark1+" "+scity1;
        sadd2=sflat2+" "+slandmark2+" "+scity2;
    }

    private void initView()
    {

        dname=findViewById(R.id.pd_name);
        dage=findViewById(R.id.pd_age);
        ddob=findViewById(R.id.pd_dob);
        male=findViewById(R.id.pd_male);
        female=findViewById(R.id.pd_female);
        others=findViewById(R.id.pd_other);
        dphone=findViewById(R.id.pd_phone);
        demail=findViewById(R.id.pd_email);
        dflat1=findViewById(R.id.pd_flat1);
        dlandmark1=findViewById(R.id.pd_landmark1);
        dcity1=findViewById(R.id.pd_city1);
        dflat2=findViewById(R.id.pd_flat2);
        dlandmark2=findViewById(R.id.pd_landmark2);
        dcity2=findViewById(R.id.pd_city2);
        ddistrict_spinner=findViewById(R.id.pd_district_spinner);
        dstate_spinner=findViewById(R.id.pd_state_spinner);
        dstation_spinner=findViewById(R.id.pd_station_spinner);
        ddistrict_spinner2=findViewById(R.id.pd_district_spinner2);
        dstate_spinner2=findViewById(R.id.pd_state_spinner2);
        dstation_spinner2=findViewById(R.id.pd_station_spinner2);
        dcheck=findViewById(R.id.pd_dcheck);
        dphoto=findViewById(R.id.pd_dphoto);
        statelist=new ArrayList<String>();
        districtlist=new ArrayList<String>();
        districtlist2=new ArrayList<String>();
        stationlist2=new ArrayList<String>();
        stationlist=new ArrayList<String>();


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
    public void seterror(TextView t) {
        t.setError("Field Empty");
        t.requestFocus();
    }
    private void checkforempty() {
        if(sname.equals(""))
            seterror(dname);
        else if(sage.equals(""))
            seterror(dage);
        else if(sphone.equals(""))
            seterror(dphone);
        else if(sdob.equals(""))
            seterror(ddob);
        else if(sfalt1.equals(""))
            seterror(dflat1);
        else if(slandmark1.equals(""))
            seterror(dlandmark1);
        else if(scity1.equals(""))
            seterror(dcity1);
        else if(sflat2.equals(""))
            seterror(dflat2);
        else if(slandmark2.equals(""))
            seterror(dlandmark2);
        else if(scity2.equals(""))
            seterror(dcity2);

    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    statelist.add(dataSnapshot1.getKey().toString());
                    Log.e("Pers",dataSnapshot1.getKey());
                    ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, statelist);
                    dstate_spinner.setAdapter(stateAdapter);
                    dstate_spinner2.setAdapter(stateAdapter);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
