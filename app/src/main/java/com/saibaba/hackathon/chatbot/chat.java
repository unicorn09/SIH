package com.saibaba.hackathon.chatbot;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.saibaba.hackathon.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
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
                userInput.onEditorAction(EditorInfo.IME_ACTION_DONE);

                    Log.d("yuyyuy","empty");
                    ModelChat responseMessage = new ModelChat(userInput.getText().toString(), true);
                    responseMessageList.add(responseMessage);
                RetrieveFeedTask task = new RetrieveFeedTask();
                task.execute();
                if (!isLastVisible())
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);


            }});
                //harsh-------> This will write your question to json





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
    public void GetText() throws UnsupportedEncodingException {

        String text = "";
        BufferedReader reader = null;

        try {
            // Defined URL  where to send data
            URL url = new URL("https://crimeassistbot.azurewebsites.net/qnamaker//knowledgebases/29dd3d2c-2f2f-4fb6-924a-fabbfe937d5a/generateAnswer");

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Authorization", " EndpointKey 5d29ff99-88af-469d-9e7a-d72693244963");
            conn.setRequestProperty("Content-Type", "application/json");

            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("question",userInput.getText().toString());
            userInput.setText("");
            userInput.setHint("");

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonParam.toString());
            wr.flush();
            Log.d("karma", "json is " + jsonParam);
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            // harsh--------->     Read Server Response
            while ((line = reader.readLine()) != null) {
                // harsh------>      Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
            //harsh-----> pulling data from json

            JSONObject object=new JSONObject(text);
            Log.e("harsh",object.toString());
            JSONObject object1=object.getJSONArray("answers").getJSONObject(0);
            //Log.d("test",test);
            String object2=object1.getString("answer");
            Log.e("harsh",object2);
            responseMessageList.add(new ModelChat(object2,false));
            messageAdapter.notifyDataSetChanged();
            /*String harsh=object2.getJSONObject(0).getString("answer");
            Log.e("harsh",harsh);
            JSONObject jsonObject=object2.getJSONObject(0).getJSONObject("context");
            JSONArray jsonArray=jsonObject.getJSONArray("prompts");
*/

           /* for (int i=0;i<jsonArray.length();i++) {
                String harshu = jsonArray.getJSONObject(i).getString("displayText");
                Log.d("payal", harshu);
                responseMessageList.add(new ModelChat(harshu, false));
                messageAdapter.notifyDataSetChanged();
            }
           */
           if (!isLastVisible())
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        }
        catch (Exception ex) {
            Log.d("karma", "exception at last " + ex);
        }

        finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("Harsh------->", "called");
                GetText();
                Log.d("Harsh-------->", "after called");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(chat.this, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Harsh-------->", "Exception occurred " + e);
            }
            return null;
        }
    }
    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }

}
