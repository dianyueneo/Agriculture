package com.cxwl.agriculture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cxwl.agriculture.db.AssetsUtil;
import com.cxwl.agriculture.listener.MyNotifier;

import cn.com.weather.api.PushAgent;


public class SplashActivity extends Activity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.setNotifier(new MyNotifier(this));
        pushAgent.enable();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);

        AssetsUtil.initDb(this);


    }


}
