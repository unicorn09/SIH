package com.saibaba.hackathon;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.saibaba.hackathon.Forms.ArticleFound;
import com.saibaba.hackathon.Forms.ComplaintForm;
import com.saibaba.hackathon.Forms.PersonalDetails;
import com.saibaba.hackathon.Forms.RegisterFIR;
import com.saibaba.hackathon.SignUp.Login;
import com.saibaba.hackathon.chatbot.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView name,state,district;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private Dialog mdialog;

    public NavigationDrawer() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        if (((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)))
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 8865);


            //getting data from shared preference
        SharedPreferences prefs = getSharedPreferences(StringVariable.SHAREDPREFERNCE, MODE_PRIVATE);
        String sname = prefs.getString(StringVariable.USER_NAME, "def");
        String sdistrict=prefs.getString(StringVariable.USER_DISTRICT,"def");
        sdistrict=sdistrict+", ";
        String sstate=prefs.getString(StringVariable.USER_STATE,"def");


        BroadcastReceiver mReceiver = new MyBroadcastReciever();
            registerReceiver(mReceiver, filter);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
             fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   startActivity(new Intent(NavigationDrawer.this, chat.class));     }
            });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationDrawer.this, chat.class));     }
        });
        fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationDrawer.this, MapsActivity.class));     }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.navigationView);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            //setting data in app bar

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_navigation_drawer);
            name=(TextView)headerView.findViewById(R.id.nav_head_name);
            name.setText(sname);
            district=(TextView)headerView.findViewById(R.id.nav_head_city);
            district.setText(sdistrict);
            state=(TextView)headerView.findViewById(R.id.nav_head_state);
            state.setText(sstate);

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                    R.id.nav_tools)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            SharedPreferences preferences =getSharedPreferences(StringVariable.SHAREDPREFERNCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(NavigationDrawer.this, Login.class));
        }
        else if(item.getItemId()==R.id.action_mic)
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-IN");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN");

            if (intent.resolveActivity(NavigationDrawer.this.getPackageManager()) != null) {
                startActivityForResult(intent, 10);
            } else {
                Toast.makeText(NavigationDrawer.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10&&resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if((result.get(0).contains("FIR")))
            {
                Intent i=new Intent(this, PersonalDetails.class);
                i.putExtra("NOC","FIR REGISTRY");
                startActivity(i);
            }
            else if((result.get(0).contains("complaint"))||(result.get(0).contains("file complaint")))
            {
                Intent intent=new Intent(this, HomeDesc.class);
                intent.putExtra("Name","Complain");
                startActivity(intent);
            }
            else if(result.get(0).contains("article found"))
            {
                startActivity(new Intent(NavigationDrawer.this, ArticleFound.class));

            }
            else if((result.get(0).contains("NOC")))
            {
                Intent i=new Intent(NavigationDrawer.this,HomeDesc.class);

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

