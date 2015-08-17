package com.cxwl.agriculture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxwl.agriculture.util.WarningParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.com.weather.api.WeatherAPI;

/**
 * Created by admin on 15/8/14.
 */
public class WarningActivity extends Activity{

    private TextView title, time, content, guide, sm;
    private ImageView back, icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        title = (TextView)findViewById(R.id.title);
        time = (TextView)findViewById(R.id.time);
        content = (TextView)findViewById(R.id.content);
        guide = (TextView)findViewById(R.id.guide);
        sm = (TextView)findViewById(R.id.sm);
        icon = (ImageView)findViewById(R.id.icon);

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HashMap<String, String> warinig = ((MyApplication) getApplication()).getWarning();

        if(warinig != null){
            String icon_text = warinig.get("icon");
            String title_text = warinig.get("title");
            String time_text = warinig.get("time");
            String content_text = warinig.get("content");
            String guide_text = warinig.get("guide");
            String sm_text = warinig.get("sm");

            int icon_int = getResources().getIdentifier(icon_text, "drawable", getPackageName());
            icon.setImageResource(icon_int);
            title.setText(title_text);
            time.setText(time_text);
            content.setText(content_text);
            guide.setText(guide_text);
            sm.setText(sm_text);
        }else{
            finish();
        }




    }


}
