package com.saibaba.hackathon;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class MyBroadcastReciever extends BroadcastReceiver {

    int Count = 0;
    private Context cntx;
    private Vibrator vibe;
    private long a;
    private long seconds_screenoff;
    private long OLD_TIME;
    private boolean OFF_SCREEN, ON_SCREEN = true, sent_msg;
    private long seconds_screenon;
    private long actual_diff;
    private long diffrence;
    DatabaseReference db;
    GPSTracker gpsTracker;
    private LocationManager mLocationManager;
    private Location l;
    String address,city,country,postalCode,knownName,state;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    public void onReceive(final Context context, Intent intent) {

        db = FirebaseDatabase.getInstance().getReference().child("Emergency").push();
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(context);
        cntx = context;
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Log.v("onReceive", "Power button is pressed.");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            a = System.currentTimeMillis();
            seconds_screenoff = a;
            OLD_TIME = seconds_screenoff;
            OFF_SCREEN = true;

            new CountDownTimer(5000, 200) {

                public void onTick(long millisUntilFinished) {


                    if (ON_SCREEN) {
                        if (seconds_screenon != 0 && seconds_screenoff != 0) {

                            actual_diff = cal_diff(seconds_screenon, seconds_screenoff);
                            if (actual_diff <= 4000) {
                                sent_msg = true;
                                if (sent_msg) {
                                    vibe.vibrate(300);
                                    fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            updateaddress(location);
                                        }
                                    });
                                    seconds_screenon = 0;
                                    seconds_screenoff = 0;
                                    sent_msg = false;

                                }
                            } else {
                                seconds_screenon = 0;
                                seconds_screenoff = 0;

                            }
                        }
                    }
                }

                public void onFinish() {

                    seconds_screenoff = 0;
                }
            }.start();


        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            a = System.currentTimeMillis();
            seconds_screenon = a;
            OLD_TIME = seconds_screenoff;

            new CountDownTimer(5000, 200) {

                public void onTick(long millisUntilFinished) {
                    if (OFF_SCREEN) {
                        if (seconds_screenon != 0 && seconds_screenoff != 0) {
                            actual_diff = cal_diff(seconds_screenon, seconds_screenoff);
                            if (actual_diff <= 4000) {
                                sent_msg = true;
                                if (sent_msg) {
                                    vibe.vibrate(300);
                                    fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            updateaddress(location);
                                        }
                                    });
                                    seconds_screenon = 0;
                                    seconds_screenoff = 0;
                                    sent_msg = false;
                                }
                            } else {
                                seconds_screenon = 0;
                                seconds_screenoff = 0;

                            }
                        }
                    }

                }

                public void onFinish() {

                    seconds_screenon = 0;
                }
            }.start();


        }
    }
    private void updateaddress(Location l) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(cntx, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(l.getLatitude(), l.getLongitude(), 1);
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
             address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
             city = addresses.get(0).getLocality();
             state = addresses.get(0).getAdminArea();
             country = addresses.get(0).getCountryName();
             postalCode = addresses.get(0).getPostalCode();
             knownName = addresses.get(0).getFeatureName();
        }
        catch (Exception e)
        {
            Log.e("harsh","exception occured");
        }
        Map<String, Object> map = new HashMap<>();
        map.put(StringVariable.USER_UID, FirebaseAuth.getInstance().getUid());
        map.put("Latitude", l.getLatitude());
        map.put("Longitude",l.getLongitude());
        map.put("Address", address);
        map.put("City", city);
        map.put("State", state);
        map.put("Country", country);
        map.put("postalCode", postalCode);
        map.put("KnownName",knownName);
        db.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("harsh","Added to database");
            }
        });

    }

    private long cal_diff(long seconds_screenon2, long seconds_screenoff2) {
        if (seconds_screenon2 >= seconds_screenoff2) {
            diffrence = (seconds_screenon2) - (seconds_screenoff2);
            seconds_screenon2 = 0;
            seconds_screenoff2 = 0;
        } else {
            diffrence = (seconds_screenoff2) - (seconds_screenon2);
            seconds_screenon2 = 0;
            seconds_screenoff2 = 0;
        }

        return diffrence;
    }
}