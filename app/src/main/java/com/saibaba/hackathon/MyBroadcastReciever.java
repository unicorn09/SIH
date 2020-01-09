package com.saibaba.hackathon;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    public void onReceive(final Context context, Intent intent) {

        db = FirebaseDatabase.getInstance().getReference().child("Emergency").push();
       //  gpsTracker = new GPSTracker(context);
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
                                    if(gpsTracker.canGetLocation)
                                    {
                                        Log.e("loc--->",gpsTracker.getLocation().toString());
                                        Log.e("harsh-->",Double.toString(gpsTracker.getLongitude()));

                                    }
                                    else
                                        Log.e("harsh-->","Cant fetch Location");
                                    Toast.makeText(cntx, "POWER BUTTON CLICKED 2 TIMES", Toast.LENGTH_LONG).show();
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
                                    if(gpsTracker.canGetLocation)
                                    {
                                        //Log.e("loc--->",gpsTracker.getLocation().toString());
                                        Log.e("harsh-->",Double.toString(gpsTracker.getLongitude()));

                                    }
                                    else
                                        Log.e("harsh-->","Cant fetch Location");
                                    context.startActivity(new Intent(context, NavigationDrawer.class));
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