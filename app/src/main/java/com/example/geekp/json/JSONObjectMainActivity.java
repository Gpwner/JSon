package com.example.geekp.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONObjectMainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btnsend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRequestwithOkhttp();
            }
        });
    }

    private void sendRequestwithOkhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient httpclient = new OkHttpClient();
//                10.0.2.2对于模拟器来讲就是PC的IP地址
                Request request = new Request.Builder()
                        .url("http://10.0.2.2/data.json")
                        .build();
                try {
                    Response response = httpclient.newCall(request).execute();
                    String data = response.body().string();

                    parseJSonWithJsonObject(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSonWithJsonObject(final String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                System.out.println("id is:" + object.getString("id"));
                System.out.println("name is :" + object.getString("name"));
                System.out.println("version is:" + object.getString("version"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
