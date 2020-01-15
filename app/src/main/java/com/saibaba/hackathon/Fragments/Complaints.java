package com.saibaba.hackathon.Fragments;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saibaba.hackathon.Adapters.Model;
import com.saibaba.hackathon.Adapters.Model_Adapter;
import com.saibaba.hackathon.NavigationDrawer;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.StringVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class Complaints extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Model> arrayList;
    Model_Adapter adapter;
    ImageView imageView;
    EditText complaint,subject,date,time;
    String Complaint="";
    String compliants[]={"Defamation","Public Drinking","Drunk Driving","Threat/Injury","Cyber Crime"};
    Button save;
    String scomplaint,ssubject,sdate,stime,sname,dis,stat,sage,typecomplaint;
    DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Complaints").child(""+System.currentTimeMillis());
    private Spinner spinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_complaints,container,false);

        SharedPreferences prefs = getActivity().getSharedPreferences(StringVariable.SHAREDPREFERNCE, MODE_PRIVATE);
         sname = prefs.getString(StringVariable.USER_NAME, "def");
         dis=prefs.getString(StringVariable.USER_DISTRICT,"def");
         stat=prefs.getString(StringVariable.USER_STATE,"def");
         sage=prefs.getString(StringVariable.USER_MOBILE,"def");
        imageView=v.findViewById(R.id.complaint_mic);
        complaint=v.findViewById(R.id.ccomplaint);
        subject=v.findViewById(R.id.csub);
        date=v.findViewById(R.id.cdate);
        time=v.findViewById(R.id.ctime);
        save=v.findViewById(R.id.complain_next);
        spinner=v.findViewById(R.id.complain_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, compliants);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typecomplaint=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typecomplaint=parent.getItemAtPosition(0).toString();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettext();
                checkforempty();
                Map<String,Object> map=new HashMap<>();
                map.put("Subject",ssubject);
                map.put("Date",sdate);
                map.put("Time",stime);
                map.put("Complaint",scomplaint);
                map.put("Name",sname);
                map.put("District",dis);
                map.put("State",stat);
                map.put("Mobile",sage);
                map.put("UID", FirebaseAuth.getInstance().getUid());
                map.put("TypeOfComplaint",typecomplaint);
                db.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), NavigationDrawer.class));
                    }
                });
            }

        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"hi-IN");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN");

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(getActivity(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void gettext() {
        ssubject=subject.getText().toString();
        sdate=date.getText().toString();
        stime=time.getText().toString();
        scomplaint=complaint.getText().toString();
    }

    private void checkforempty() {
       if(ssubject.equals(""))
       {
           subject.setError("Missing Field");
           return;
       }
       else if(sdate.equals(""))
       {
           date.setError("Missing Field");
           return;
       }
       else if(stime.equals(""))
       {
           time.setError("Missing Field");
           return;
       }
       else if(scomplaint.equals(""))
       {
           complaint.setError("Missing Field");
           return;
       }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Complaint=complaint.getText().toString()+" "+(result.get(0));
                    complaint.setText("");
                    complaint.setText(Complaint);
                }
                break;
        }
    }


}
