package com.cxwl.agriculture;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.cxwl.agriculture.fragement.AboutFragment;
import com.cxwl.agriculture.fragement.AskPriceFragment;
import com.cxwl.agriculture.fragement.HomeFragment;
import com.cxwl.agriculture.fragement.UploadWeatherFragment;
import com.cxwl.agriculture.fragement.WeatherFragment;

import cn.com.weather.api.StatAgent;


public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater inflater;

    private Class fragmentArray[] = {HomeFragment.class , AskPriceFragment.class, WeatherFragment.class, UploadWeatherFragment.class, AboutFragment.class};
    private int imageViewArray[] = {R.drawable.home_sel,R.drawable.ask,R.drawable.weather,R.drawable.uploadweather,R.drawable.about};
    private int textArray[] = {R.string.home, R.string.askprice, R.string.weather, R.string.uploadweather, R.string.about};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.getWindow().setBackgroundDrawable(null);

        initView();
    }

    private void initView(){
        inflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost)this.findViewById(android.R.id.tabhost);
        mTabHost.setup(this, this.getSupportFragmentManager(), R.id.realcontent);
        if(Build.VERSION.SDK_INT > 10){
            mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }

        for(int i=0;i<fragmentArray.length;i++){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(this.getResources().getString(textArray[i])).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }

    }

    private View getTabItemView(int index){
        View view = inflater.inflate(R.layout.item_tab, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageview);
        imageView.setImageResource(imageViewArray[index]);

        TextView textView = (TextView)view.findViewById(R.id.textview);
        textView.setText(this.getResources().getString(textArray[index]));

        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        StatAgent.onPause(this, null);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        StatAgent.onResume(this);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        StatAgent.onStop(this);
    }
}
