package com.saibaba.hackathon.SignUp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.oob.SignUp;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import com.saibaba.hackathon.MainActivity;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.StringVariable;


public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText enter_email, enter_password, enter_reset_email;
    private Button login_getin;
    private TextView enter_signup_page, forgot_password;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener authStateListener;
    Tracker mTracker;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private String Email = "";
    private String Password = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        init();

    }

    private void init() {


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        enter_password = findViewById(R.id.enter_password);
        enter_email = findViewById(R.id.enter_email);
        enter_signup_page = findViewById(R.id.enter_signup_page);
        login_getin = findViewById(R.id.login_getin);
        forgot_password = findViewById(R.id.forgot_password);

        enter_signup_page.setOnClickListener(this);
        login_getin.setOnClickListener(this);
        forgot_password.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter_signup_page:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.login_getin:
                check_Fields();
                break;
        }

    }

    private void check_Fields() {
        try {
            Email = enter_email.getText().toString();

            if (Email.length() == 0 || !Email.contains("@")) {
                Toast.makeText(this, "Please Enter Email-Id Correctly", Toast.LENGTH_SHORT).show();
            }
            else{

                try {
                    Password = enter_password.getText().toString();

                    if (Password.length() == 0) {
                        Toast.makeText(this, "Please Enter Password Correctly", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.setMessage("Signing In...");
                        progressDialog.show();
                        try_signin();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "OPPS You Hadn't Entered the Password", Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e) {
            Toast.makeText(this, "OPPS You Hadn't Entered the email", Toast.LENGTH_LONG).show();
        }



        // try sign in
    }

    private void try_signin() {
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.setMessage("Checking User...");
                            checkingUserExist(mAuth.getCurrentUser().getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.e("Login Activity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Invalid User Or Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void checkingUserExist(String UID) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);
        db.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();
                    nextActivity();

                } else {
                    String mailid = "";
                    String name = "";
                    String photo = "";
                    try {
                        mailid = mAuth.getCurrentUser().getEmail();
                        name = mAuth.getCurrentUser().getDisplayName();
                        photo = mAuth.getCurrentUser().getPhotoUrl().toString();
                    } catch (Exception e) {
                        Log.e("Getting Started", e.getMessage());
                    }
                    progressDialog.setMessage("Creating New User...");
                    startActivity(new Intent(Login.this,SignUp.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nextActivity() {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}

