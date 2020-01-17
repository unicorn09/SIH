package com.saibaba.hackathon.ui.gallery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saibaba.hackathon.NavigationDrawer;
import com.saibaba.hackathon.PoliceStation.PoliceStat;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.SignUp.Login;
import com.saibaba.hackathon.StringVariable;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GalleryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    DatabaseReference db;
    private List<String> ls;
    private String state,district;
    private GalleryViewModel galleryViewModel;
    private String policestations[];
    private Spinner spinner;
    private TextView dis,stat,policestat;
    private ImageView img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        ls=new ArrayList<String>();

        //sharedpreference
         SharedPreferences prefs = getActivity().getSharedPreferences(StringVariable.SHAREDPREFERNCE, MODE_PRIVATE);
         state = prefs.getString(StringVariable.USER_STATE, "def");
         district=prefs.getString(StringVariable.USER_DISTRICT,"def");



        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        //init
        //img=root.findViewById(R.id.frag_gallery_btn_search);
        //spinner=root.findViewById(R.id.spinnerpa);
        //spinner.setOnItemSelectedListener(this);
        dis=root.findViewById(R.id.frag_gallery_district);
        dis.setText(district);
        stat=root.findViewById(R.id.frag_gallery_state);
        stat.setText(state);
       // policestat=root.findViewById(R.id.textview_gallerypolicest);
        db= FirebaseDatabase.getInstance().getReference().child("places").child("State").child(state).child(district);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    ls.add(dataSnapshot1.getValue().toString()+" ("+dataSnapshot1.getKey().toString()+")");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      /*  img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PoliceStat.class));
            }
        });*/

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, ls);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        return root;


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view==spinner)
        {
            Log.e("spin","reached");
            policestat.setText(parent.getItemAtPosition(position).toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}