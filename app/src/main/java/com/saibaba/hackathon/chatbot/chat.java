package com.saibaba.hackathon.chatbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.saibaba.hackathon.R;

import java.util.ArrayList;

public class chat extends AppCompatActivity {

    private EditText userInput;
    private RecyclerView recyclerView;
    private ArrayList<ModelChat> responseMessageList;
    private AdapterChat messageAdapter;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setTitle("Chat Bot");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userInput = findViewById(R.id.userinput);
        recyclerView = findViewById(R.id.conversation);
        imageView=findViewById(R.id.btn_send);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        responseMessageList = new ArrayList<>();
        messageAdapter = new AdapterChat(responseMessageList, this);

        recyclerView.setAdapter(messageAdapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseMessageList.add(new ModelChat(userInput.getText().toString(),false));
                responseMessageList.add(new ModelChat(userInput.getText().toString(),true));
                messageAdapter.notifyDataSetChanged();
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
