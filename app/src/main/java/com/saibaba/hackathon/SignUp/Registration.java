package com.saibaba.hackathon.SignUp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saibaba.hackathon.NavigationDrawer;
import com.saibaba.hackathon.OTPVerification;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.StringVariable;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Registration extends AppCompatActivity {

    private static final String TAG = "Registration";
    private TextView verifyNumber,male,female,others;
    private EditText infoPhone,age,dob,address1,address2,city,mobile,infoName;
    private String verificationID;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;
    private SearchableSpinner mstate,mdistrict;
    Dialog mDialog;
    private String[] states,districts;
    private String gender;
    private boolean isVerified;
    private Button registerButton;
    private ImageView verifiedImageView;
    private ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    String otp,passotp;
    private String[] stations;
    private Spinner mstation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
        states = new String[]{"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh",
                "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand",
                "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Pondicherry",
                "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttaranchal", "West Bengal"};

        districts = new String[]{"Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", "Bhojpur", "Buxar", "Darbhanga", "Gaya", "Gopalganj",
                "Jamui", "Jehanabad", "Kaimur", "Katihar", "Khagaria", "Kishanganj", "Lakhisarai", "Madhepura", "Madhubani", "Munger", "Muzaffarpur",
                "Nalanda", "Nawada", "Pashchim Champaran", "Patna", "Purbi Champaran", "Purnia", "Rohtas", "Saharsa", "Samastipur", "Saran", "Sheikhpura",
                "Sitamarhi", "Siwan", "Supaul", "Vaishali"};
        stations=new String[]{"BR26001","BR26002","BR26003","BR26004","BR26005","BR26006"};
        verifyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: verify clicked");
                verifyNumberUsingOTP();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateUserNode();
            }
        });

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, states);
        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, districts);
        ArrayAdapter<String> stationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stations);

        mstate.setAdapter(stateAdapter);
        mdistrict.setAdapter(districtsAdapter);
        mstation.setAdapter(stationAdapter);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "male";
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "female";
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittext));

            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "other";
                others.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
                female.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
                male.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
            }
        });
    }

    private void initView() {
        otp="";
        isVerified=false;
        gender=null;
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("saving data");
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        infoName=findViewById(R.id.info_name);
        registerButton=findViewById(R.id.basic_info_next);
        verifiedImageView=findViewById(R.id.userverified_success);
        age=findViewById(R.id.info_age);
        address1=findViewById(R.id.info_Address1);
        address2=findViewById(R.id.info_Address2);
        verifyNumber=findViewById(R.id.verify_number);
        infoPhone=findViewById(R.id.info_phone);
        city=findViewById(R.id.info_city);
        dob=findViewById(R.id.info_dob);
        male=findViewById(R.id.info_male);
        female=findViewById(R.id.info_female);
        others=findViewById(R.id.info_other);
        mobile=findViewById(R.id.info_phone);
        mdistrict=findViewById(R.id.district_spinner1);
        mstate=findViewById(R.id.state_spinner);
        mstation=findViewById(R.id.station_spinner);
        gender = "male";
        male.setBackground(getResources().getDrawable(R.drawable.bg_edittextselected));
        female.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
        others.setBackground(getResources().getDrawable(R.drawable.bg_edittext));
    }

    public void verifyNumberUsingOTP(){
        long number=0;
        long x;
        Random random=new Random();
        for(int i=1;i<=6;i++){
            x=random.nextInt(10)+1;
            number=number*10+x;
        }
        OTPVerification otpVerification=new OTPVerification(this,"Harsh","7254043940");
        otpVerification.execute();
        passotp=otpVerification.getOtp();
        goForOTP();

    }

    private void goForOTP(){
        mDialog=new Dialog(this);
        mDialog.setContentView(R.layout.otp_dialog_view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.show();
        Button verifyOTPButton=mDialog.findViewById(R.id.verifyOTPButton);
        final EditText otpEditText = mDialog.findViewById(R.id.otpEditText);
        verifyOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                String otpCode=otpEditText.getText().toString();
                validateNumber(otpCode);
            }
        });
    }

    private void validateNumber(String otpCode){
        Log.e("otp",otpCode);
        Log.e("otppass",passotp);
        if(passotp.equals(otpCode)){
            Log.d(TAG, "onComplete: phone number verified");
            isVerified=true;
            verifyNumber.setVisibility(View.GONE);
            verifiedImageView.setVisibility(View.VISIBLE);
        }else
            {
            Log.d(TAG, "onComplete: invalid otp");
            displayToast("invalid otp");
            }
    }

    private void populateUserNode() {
        if (isVerified == false) {
            displayToast("Mobile number not verified");
            infoPhone.requestFocus();
            return;
        }

        String name = infoName.getText().toString();
        if (name.equals("")) {
            displayError(infoName);
            return;
        }
        String ageText = age.getText().toString();
        if (ageText.equals("")) {
            displayError(age);
            return;
        }
        String dobText = dob.getText().toString();
        if (dobText.equals("")) {
            displayError(dob);
            return;
        }
        String address1Text = address1.getText().toString();
        if (address1Text.equals("")) {
            displayError(address1);
            return;
        }
        String address2Text = address2.getText().toString();
        if (address2Text.equals("")) {
            displayError(address2);
            return;
        }
        String cityText = city.getText().toString();
        if (cityText.equals("")) {
            displayError(city);
            return;}

            SharedPreferences.Editor editor = getSharedPreferences(StringVariable.SHAREDPREFERNCE, MODE_PRIVATE).edit();
            editor.putString(StringVariable.USER_NAME, name);
            editor.putString(StringVariable.USER_EMAIL, firebaseUser.getEmail());
            editor.putString("station", mstation.getSelectedItem().toString());
            editor.putString(StringVariable.USER_DISTRICT,mdistrict.getSelectedItem().toString());
            editor.putString(StringVariable.USER_STATE,mstate.getSelectedItem().toString());
            editor.putString(StringVariable.USER_MOBILE,mobile.getText().toString());
            editor.apply();
            String stateText = mstate.getSelectedItem().toString();
            String districtText = mdistrict.getSelectedItem().toString();
            String mobileNumber = infoPhone.getText().toString();
            Map<String, Object> mp = new HashMap<>();
            mp.put(StringVariable.USER_NAME, name);
            mp.put(StringVariable.USER_AGE, ageText);
            mp.put(StringVariable.USER_DOB, dobText);
            mp.put(StringVariable.USER_GENDER, gender);
            mp.put(StringVariable.USER_ADDRESS, address1Text + " " + address2Text);
            mp.put(StringVariable.USER_CITY, cityText);
            mp.put(StringVariable.USER_STATE, stateText);
            mp.put(StringVariable.USER_DISTRICT, districtText);
            mp.put(StringVariable.USER_MOBILE, mobileNumber);
            mp.put(StringVariable.USER_IMAGE, firebaseUser.getPhotoUrl());
            mp.put(StringVariable.USER_EMAIL, firebaseUser.getEmail());
            progressDialog.show();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(firebaseUser.getUid());
            databaseReference.setValue(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        displayToast("Some Error Occurred");
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        startActivity(new Intent(Registration.this, NavigationDrawer.class));
                    }
                }
            });
        }

        private void displayError (EditText view){
            view.setError("empty");
            view.requestFocus();
        }

        private void displayToast (String msg){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
