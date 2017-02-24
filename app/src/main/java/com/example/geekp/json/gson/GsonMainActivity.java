package com.example.geekp.json.gson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.geekp.json.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GsonMainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_main);
        button = (Button) findViewById(R.id.btnsendre);
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
                    parseJSonWithGSON(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSonWithGSON(final String data) {
        Gson gson = new Gson();
        List<Bean> beanlist = gson.fromJson(data, new TypeToken<List<Bean>>() {
        }.getType());
        for(Bean bean:beanlist)
        {
            System.out.println("id is :"+bean.getId());
            System.out.println("name is:"+bean.getName());
            System.out.println("vesrsion is :"+bean.getVersion());
        }
    }
}
