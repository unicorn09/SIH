package com.saibaba.hackathon.SignUp;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.saibaba.hackathon.R;

import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {

    private static final String TAG = "Registration";
    private TextView verifyNumber,male,female,others;
    private EditText infoPhone,age,dob,address1,address2,city,mobile;
    private String verificationID;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
        verifyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: verify clicked");
                verifyNumberUsingOTP();
            }
        });
    }

    private void initView() {
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
    }

    public void verifyNumberUsingOTP(){
        String phoneNumber=infoPhone.getText().toString();
        phoneNumber="+91"+phoneNumber;
        onVerificationStateChangedCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d(TAG, "onVerificationFailed: "+e.getMessage());
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationID=s;
                goForOTP();
            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                120,
                TimeUnit.SECONDS,
                this,
                onVerificationStateChangedCallbacks
        );
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
                String otpCode=otpEditText.getText().toString();
                validateNumber(otpCode);
            }
        });
    }

    private void validateNumber(String otpCode){
        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verificationID,otpCode);
        mDialog.dismiss();
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: phone number verified");
                }else{
                    Log.d(TAG, "onComplete: invalid otp");
                }
            }
        });
    }
}
