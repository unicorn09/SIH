package com.saibaba.hackathon.Forms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.saibaba.hackathon.Adapters.ModelPersonalDetails;
import com.saibaba.hackathon.HomeDesc;
import com.saibaba.hackathon.NavigationDrawer;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.SignUp.Login;
import com.saibaba.hackathon.StringVariable;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        initView();

         final ArrayAdapter<String> stationAdapter = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, stationlist);
         final ArrayAdapter<String> districtsAdapter = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, districtlist);
        final ArrayAdapter<String> stationAdapter2 = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, stationlist2);
        final ArrayAdapter<String> districtsAdapter2 = new ArrayAdapter<String>(PersonalDetails.this, android.R.layout.simple_list_item_1, districtlist2);

       final SharedPreferences sp=getSharedPreferences(StringVariable.SHAREDPREFERNCE,MODE_PRIVATE);

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
                if(validate(semail)) {
                    ModelPersonalDetails obj = new ModelPersonalDetails(sname, sage, gender, "", sdob, semail, sphone, sstate, sdistrict, sstation, sstate2, sdistrict2, sstation2, "");
                    if (nextactivity.equalsIgnoreCase("NOC PROCESSION"))
                        startActivity(new Intent(PersonalDetails.this, ProcessionRequest.class));
                    else if (nextactivity.equalsIgnoreCase("NOC PROTEST"))
                        startActivity(new Intent(PersonalDetails.this, ProtestRequest.class));
                    else if (nextactivity.equalsIgnoreCase("NOC EVENT"))
                        startActivity(new Intent(PersonalDetails.this, EventPerformance.class));
                    else if (nextactivity.equalsIgnoreCase("fir registry")) {
                        Intent i = new Intent(PersonalDetails.this, RegisterFIR.class);
                        Gson gson = new Gson();
                        String DataObjectAsAString = gson.toJson(obj);
                        i.putExtra("object", DataObjectAsAString);
                        startActivity(i);
                    } else
                        startActivity(new Intent(PersonalDetails.this, FilmShooting.class));
                }else{
                    demail.setError("invalid email");
                    demail.requestFocus();
                }
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
                            stationlist.add(dataSnapshot1.getKey()+" ("+dataSnapshot1.getValue()+")");
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
                            stationlist2.add(dataSnapshot1.getKey()+" ("+dataSnapshot1.getValue()+")");
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
                sstation=sstation.substring(0,7);
                Log.d("divyansh>>>", "onItemSelected: "+sstation);
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
               sstation2=sstation2.substring(0,7);
                Log.d("divyansh>>>", "onItemSelected: "+sstation2);
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
            case R.id.action_mic1:
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-IN");
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN");

                    if (intent.resolveActivity(PersonalDetails.this.getPackageManager()) != null) {
                        startActivityForResult(intent, 10);
                    } else {
                        Toast.makeText(PersonalDetails.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                    }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10&&resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if((result.get(0).contains("name")))
            {
                int index=result.get(0).indexOf("name");
                index+=4;
                dname.setText(result.get(0).substring(index+1));
            }
            else if((result.get(0).contains("age")))
            {
                int index=result.get(0).indexOf("age");
                index+=3;
                dage.setText(result.get(0).substring(index+1));
            }
            else if(result.get(0).contains("date of birth"))
            {
                int index=result.get(0).indexOf("date");
                index+=13;
                ddob.setText(result.get(0).substring(index+1));
            }
            else if((result.get(0).contains("email")))
            {
                int index=result.get(0).indexOf("email");
                index+=5;
                demail.setText(result.get(0).substring(index+1));
            }
            else if((result.get(0).contains("email")))
            {
                int index=result.get(0).indexOf("email");
                index+=5;
                demail.setText(result.get(0).substring(index+1));
            }
            else if((result.get(0).contains("email")))
            {
                int index=result.get(0).indexOf("email");
                index+=5;
                demail.setText(result.get(0).substring(index+1));
            }
        }
    }
}
