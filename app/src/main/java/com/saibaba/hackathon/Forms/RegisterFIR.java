package com.saibaba.hackathon.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saibaba.hackathon.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class RegisterFIR extends AppCompatActivity {
SearchableSpinner nationality_spinner,nature_spinner,sub_spinner,file_spinner,filesub_spinner;
TextView present,not_present,yes,no;
ImageView uploadapp,uploadsign;
EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fir);
        initView();
    }
    private void initView()
    {
        nationality_spinner=findViewById(R.id.nationality_spinner);
        nature_spinner=findViewById(R.id.nature_spinner);
        sub_spinner=findViewById(R.id.sub_spinner);
        file_spinner=findViewById(R.id.file_spinner);
        filesub_spinner=findViewById(R.id.filesub_spinner);
        uploadapp=findViewById(R.id.uploadapp);
        uploadsign=findViewById(R.id.uploadsign);
        present=findViewById(R.id.present);
        not_present=findViewById(R.id.not_present);
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);

    }
}
