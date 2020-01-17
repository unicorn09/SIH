package com.saibaba.hackathon.Forms;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saibaba.hackathon.R;

import java.util.ArrayList;
import java.util.List;

public class ArticleFound extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
private RecyclerView recyclerView;
private Spinner spinner;
private List<String> ls;
private String selecteddistrict;
private String dis[];int i=0;
    private Dialog mdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_found);

        getSupportActionBar().setTitle("Article Found");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerarticlefound);
        spinner=findViewById(R.id.articlefoundspinner);
        spinner.setOnItemSelectedListener(this);
        ls=new ArrayList<>();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("ArticleFound");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    ls.add(dataSnapshot1.getKey().toString());
                    //dis[i++]=dataSnapshot1.getKey().toString();
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(ArticleFound.this, android.R.layout.simple_spinner_item, ls);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selecteddistrict=parent.getItemAtPosition(position).toString();
        Log.e("harsh",selecteddistrict);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.articefound, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_add:
            {
                mdialog = new Dialog(this);
                mdialog.setContentView(R.layout.pop_up_articlefound);
                mdialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                mdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            }
        }
        return super.onOptionsItemSelected(item);
    }

}
