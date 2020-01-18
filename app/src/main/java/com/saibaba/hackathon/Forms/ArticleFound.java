package com.saibaba.hackathon.Forms;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.saibaba.hackathon.R;
import com.saibaba.hackathon.StringVariable;

import java.util.ArrayList;
import java.util.List;

public class ArticleFound extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int GALLERY_REQUEST = 9;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private List<String> ls;
    private String selecteddistrict;
    private String dis[];
    int i = 0;
    private Button publish;
    private Dialog mdialog;
    private ImageView close;
    private TextView name, content;
    private ImageView imageView;
    private SharedPreferences prefs;
    private Uri uri;
    private String mUri;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_found);

        prefs = getSharedPreferences(StringVariable.SHAREDPREFERNCE, MODE_PRIVATE);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("ArticleFound").child(prefs.getString(StringVariable.USER_DISTRICT,"def")).push();
        getSupportActionBar().setTitle("Article Found");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerarticlefound);
        spinner = findViewById(R.id.articlefoundspinner);
        spinner.setOnItemSelectedListener(this);
        ls = new ArrayList<>();

        mdialog = new Dialog(this);
        mdialog.setContentView(R.layout.pop_up_articlefound);
        mdialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        mdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        close = mdialog.findViewById(R.id.close_view);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
            }
        });
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("ArticleFound");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
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
        selecteddistrict = parent.getItemAtPosition(position).toString();
        Log.e("harsh", selecteddistrict);
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
            case R.id.action_add: {
                mdialog.show();
                name = mdialog.findViewById(R.id.pop_up_blog_username);
                name.setText(prefs.getString(StringVariable.USER_NAME, "def"));
                imageView = mdialog.findViewById(R.id.upload_btn);
                publish=mdialog.findViewById(R.id.publish_btn);
                content = mdialog.findViewById(R.id.your_blog_here);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getimage();
                    }
                });
                publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("name").setValue(prefs.getString(StringVariable.USER_NAME,""));
                        databaseReference.child("content").setValue(content.getText().toString());
                        databaseReference.child("image").setValue(mUri);
                        databaseReference.child(StringVariable.USER_UID).setValue(FirebaseAuth.getInstance().getUid());
                        mdialog.dismiss();
                        Toast.makeText(ArticleFound.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST  && data!=null && data.getData()!=null){
            uri=data.getData();
            Log.e("harshuri",uri.toString());
            if(uri!=null){
                final StorageReference filereference= FirebaseStorage.getInstance().getReference().child("ArticleFound").child(System.currentTimeMillis()+"");
                UploadTask uploadTask = filereference.putFile(uri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        return filereference.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){
                            Uri downloadUri=task.getResult();
                            mUri=downloadUri.toString();
                            Toast.makeText(ArticleFound.this, "Image Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ArticleFound.this,"No Image Selected",Toast.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ArticleFound.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
            else{
                Toast.makeText(this,"No Image Selected",Toast.LENGTH_LONG).show();
            }

        }

    }
}
