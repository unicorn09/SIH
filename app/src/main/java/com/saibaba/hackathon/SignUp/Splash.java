package com.saibaba.hackathon.SignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.saibaba.hackathon.NavigationDrawer;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.StringVariable;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences(StringVariable.SHAREDPREFERNCE, MODE_PRIVATE);
                String check = prefs.getString(StringVariable.USER_NAME, "def");
                if (!check.equals("def")) {
                    startActivity(new Intent(Splash.this, NavigationDrawer.class));
                    Log.e("onstart-->", "reached");
                } else {
                    startActivity(new Intent(Splash.this,Login.class));

                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}
