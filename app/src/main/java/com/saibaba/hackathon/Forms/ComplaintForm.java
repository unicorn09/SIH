package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.saibaba.hackathon.R;

public class ComplaintForm extends AppCompatActivity {
EditText csub,cdate,ctime,ccomplaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_complaint_form);
        initView();
    }
    private void initView()
    {
//        csub=findViewById(R.id.csub);
//        cdate=findViewById(R.id.cdate);
//        ctime=findViewById(R.id.ctime);
//        ccomplaint=findViewById(R.id.ccomplaint);
    }
}
