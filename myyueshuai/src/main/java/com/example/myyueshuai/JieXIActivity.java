package com.example.myyueshuai;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myyueshuai.bean.User;
import com.example.myyueshuai.okhttp.MyOkHttp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JieXIActivity extends AppCompatActivity {

    private RecyclerView Recyc;
    private String mUrl = "http://172.16.45.23/text/json.txt";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String mStr = (String) msg.obj;
                Gson gson = new Gson();
                Type type = new TypeToken<List<User>>() {
                }.getType();
                mList = gson.fromJson(mStr, type);
                MyRecycAdapter adapter = new MyRecycAdapter(mList,JieXIActivity.this);
                Recyc.setAdapter(adapter);
            }
        }
    };
    private List<User> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_xi);
        initView();
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = MyOkHttp.getMyOkHttp().sendGet(mUrl);
                    handler.obtainMessage(1, s).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void initView() {
        Recyc = (RecyclerView) findViewById(R.id.Recyc);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        Recyc.setLayoutManager(manager);
    }
}
