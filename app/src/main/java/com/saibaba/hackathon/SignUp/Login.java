package com.saibaba.hackathon.SignUp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.oob.SignUp;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import com.saibaba.hackathon.Home;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.StringVariable;


public class Login extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private EditText enter_email, enter_password, enter_reset_email;
    private Button login_getin;
    private TextView enter_signup_page, forgot_password;
    private AlertDialog dialog;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener authStateListener;
    Tracker mTracker;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private FirebaseUser firebaseUser;

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
        firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            Log.d(TAG, "onCreate: "+firebaseUser.isEmailVerified());
            if(firebaseUser.isEmailVerified()){
                checkingUserExist(firebaseUser.getUid());
            }else{
                firebaseUser.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseUser=mAuth.getCurrentUser();
                    }
                });
                if(!firebaseUser.isEmailVerified()) {
                    dialog.show();
                }else{
                    checkingUserExist(firebaseUser.getUid());
                }
            }
        }
    }

    private void init() {


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        enter_password = findViewById(R.id.enter_passwordLogin);
        enter_email = findViewById(R.id.enter_emailLogin);
        enter_signup_page = findViewById(R.id.enter_signup_page);
        login_getin = findViewById(R.id.login_getin);
        forgot_password = findViewById(R.id.forgot_password);

        enter_signup_page.setOnClickListener(this);
        login_getin.setOnClickListener(this);
        forgot_password.setOnClickListener(this);
        dialog=new AlertDialog.Builder(this)
                .setTitle("Email Not Verified")
                .setMessage("Please verify your email")
                .create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter_signup_page:
                startActivity(new Intent(this,Signup.class));
                finish();
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
                            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                            if(firebaseUser.isEmailVerified()) {
                                checkingUserExist(firebaseUser.getUid());
                            }else{
                                dialog.show();
                            }
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
        progressDialog.setMessage("checking user...");
        progressDialog.show();
        Log.d(TAG, "checkingUserExist: "+UID);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);
        db.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: datasnapshot.exist"+dataSnapshot.exists());
                Log.d(TAG, "onDataChange: datasnapshot"+dataSnapshot.toString());
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(Login.this, Home.class));
                    finish();
                } else {
                    startActivity(new Intent(Login.this,Registration.class));
                    progressDialog.dismiss();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

