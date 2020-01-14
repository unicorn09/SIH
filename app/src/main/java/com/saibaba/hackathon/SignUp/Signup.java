package com.saibaba.hackathon.SignUp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tasks.OnFailureListener;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.SignUp.Login;
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
import com.saibaba.hackathon.StringVariable;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Signup";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    Tracker mTracker;
    private EditText enter_email_signup,enter_password_signup,retype_password;
    private Button Signup_getin;
    private TextView enter_login_page;
    private AlertDialog dialog;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private ProgressDialog progressDialog;

    private String Email = "",Password = "",RePassword = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        enter_email_signup = findViewById(R.id.enter_emailSignup);
        enter_password_signup = findViewById(R.id.enter_passwordSignup);
        retype_password = findViewById(R.id.confirm_passwordSignup);

        Signup_getin = findViewById(R.id.signup_getin);
        enter_login_page = findViewById(R.id.enter_login_page);

        Signup_getin.setOnClickListener(this);
        enter_login_page.setOnClickListener(this);
        dialog=new AlertDialog.Builder(this)
                .setTitle("Verification Link Sent")
                .setMessage("Please verify your email")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(Signup.this,Login.class));
                        finish();
                    }
                }).create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup_getin:
                check_Fields();
                break;
            case R.id.enter_login_page:
                startActivity(new Intent(this, Login.class));
                finish();
                break;
        }
    }

    private void check_Fields() {
        try {
            Email = enter_email_signup.getText().toString();

            if (Email.length() == 0 || !Email.contains("@")||!Email.contains(".")) {
                Toast.makeText(this, "Please Enter EmailId Correctly", Toast.LENGTH_SHORT).show();
            }else{
                try {
                    Password = enter_password_signup.getText().toString();

                    if (Password.length() == 0) {
                        Toast.makeText(this, "Please Enter Password Correctly", Toast.LENGTH_SHORT).show();
                    }else{

                        try{
                            RePassword = retype_password.getText().toString();

                            if(!Password.equals(RePassword)){
                                Toast.makeText(this, "Password Doesn't Match", Toast.LENGTH_LONG).show();
                            }
                            else if(Password.equals(RePassword)){
                                if(Password.length()<6){
                                    Toast.makeText(this, "Password Should Contain Atleast 6 Characters", Toast.LENGTH_LONG).show();
                                }else {
                                    progressDialog.setMessage("Checking User...");
                                    progressDialog.show();
                                    checking_Exsiting_user();
                                }
                            }
                        }catch (Exception e){
                            Toast.makeText(this, "Please Confirm Your Password", Toast.LENGTH_LONG).show();
                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(this, "OPPS You Hadn't Entered the Password", Toast.LENGTH_LONG).show();
                }


            }
        } catch (Exception e) {
            Toast.makeText(this, "OPPS You Hadn't Entered the email", Toast.LENGTH_LONG).show();
        }


    }

    private void checking_Exsiting_user() {

        //
        auth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(),"Email Id Already In Our Database",Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),"Email Already Exists, Please Sign In",Toast.LENGTH_LONG).show();
                            auth.signOut();
                            progressDialog.dismiss();
                        } else {
                            progressDialog.setMessage("Signing up...");
                            try_signup();
                        }

                        // ...
                    }
                });
    }

    private void try_signup() {
        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                            dialog.show();
                        } 
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: "+e.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void nextActivity() {

        startActivity(new Intent(getApplicationContext(), Registration.class));
        finish();
    }
}

