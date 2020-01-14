package com.saibaba.hackathon;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

public class OTPVerification {
    Context cntxt;
    String nam,num;
    static String otp;String s=generateotp();
    public OTPVerification(Context context,String Name,String Number) {
        cntxt=context;
        nam=Name;
        num=Number;
    }
public void execute()
{
    RetrieveFeedTask task = new RetrieveFeedTask();
    task.execute();
}
    public String sendSms() {

        try {
            // Construct data
            String apiKey = "apikey=" + URLEncoder.encode("6+8Mx1q7ERw-ETKOm14HmRm0glNtZTAPSj0BjoJLKd", "UTF-8");
            String message = "&message=" + URLEncoder.encode("Hi "+nam+ " your OTP for NCRA verification is "+s,"UTF-8");
            String sender = "&sender=" + URLEncoder.encode("TXTLCL", "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode("917254043940", "UTF-8");

            // Send data
            String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult="";
            while ((line = rd.readLine()) != null) {
                // Process line...
                sResult=sResult+line+" ";
            }
            rd.close();

            return s;
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("Harsh------->", "called");
                Log.d("Harsh",sendSms());
                Log.d("Harsh-------->", "after called");

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(cntxt, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Harsh-------->", "Exception occurred " + e);
            }
            return null;
        }
    }
public String getOtp()
{
    return s;
}
    public String generateotp()
    {
        Random rand = new Random();
        otp = ""+rand.nextInt(9999);
        return otp;
    }
}
