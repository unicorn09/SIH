package com.saibaba.hackathon;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
int color[]={R.color.list_color2,R.color.list_color3,R.color.list_color4,R.color.list_color5,R.color.list_color6,R.color.list_color7,R.color.list_color8,R.color.list_color9,R.color.list_color10};
int i=0;
    private GoogleMap mMap;
    private static final String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: starts");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(25.6164, 85.1411);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        getDataOfAlerts();
    }

    private void getDataOfAlerts(){
        i=0;
        FirebaseDatabase.getInstance().getReference().child("crime-alert").child("Bihar").child("Patna")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                CircleOptions circleOptions=new CircleOptions();
                                circleOptions.radius(1000);
                                circleOptions.center(new LatLng((double)dataSnapshot1.child("latitude").getValue(),(double)dataSnapshot1.child("longitude").getValue()));
                                circleOptions.strokeWidth(0);
                                long crimes=(long)dataSnapshot1.child("alerts").getValue();
                                if(crimes<200){
                                    circleOptions.fillColor(0x50ffc107);
                                }else if(crimes<400){
                                    circleOptions.fillColor(0x50FF5722);
                                }else{
                                    circleOptions.fillColor(0x50ff0000);
                                }
                                mMap.addCircle(circleOptions);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
