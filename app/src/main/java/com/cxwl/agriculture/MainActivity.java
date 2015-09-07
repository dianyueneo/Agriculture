package com.cxwl.agriculture;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Class fragmentArray[] = {HomeFragment.class , AskPriceFragment.class, WeatherFragment.class, UploadWeatherFragment.class, AboutFragment.class};
    private int imageViewArray[] = {R.drawable.home_sel,R.drawable.ask,R.drawable.weather,R.drawable.uploadweather,R.drawable.about};
    private int textArray[] = {R.string.home, R.string.askprice, R.string.weather, R.string.uploadweather, R.string.about};

    private HomeFragment homeFragment;
    private AskPriceFragment askPriceFragment;
    private WeatherFragment weatherFragment;
    private UploadWeatherFragment uploadWeatherFragment;
    private AboutFragment aboutFragment;

    private TextView btn1, btn2, btn3, btn4, btn5;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("test", "onCreate======" + this.getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
    }

    private void initView(){

        fragmentManager = getSupportFragmentManager();

//        homeFragment = new HomeFragment();
//        askPriceFragment = new AskPriceFragment();
//        weatherFragment = new WeatherFragment();
//        uploadWeatherFragment = new UploadWeatherFragment();
//        aboutFragment = new AboutFragment();

        homeFragment = (HomeFragment)fragmentManager.findFragmentById(R.id.tab1);
        askPriceFragment = (AskPriceFragment)fragmentManager.findFragmentById(R.id.tab2);
        weatherFragment = (WeatherFragment)fragmentManager.findFragmentById(R.id.tab3);
        uploadWeatherFragment = (UploadWeatherFragment)fragmentManager.findFragmentById(R.id.tab4);
        aboutFragment = (AboutFragment)fragmentManager.findFragmentById(R.id.tab5);

        fragmentManager.beginTransaction()
//                .add(R.id.realcontent, aboutFragment, "tab5")
//                .add(R.id.realcontent, uploadWeatherFragment, "tab4")
//                .add(R.id.realcontent, weatherFragment, "tab5")
//                .add(R.id.realcontent, askPriceFragment, "tab2")
//                .add(R.id.realcontent, homeFragment, "tab1")
//                .hide(homeFragment)
                .hide(askPriceFragment)
                .hide(weatherFragment)
                .hide(uploadWeatherFragment)
                .hide(aboutFragment)
                .commit();

        btn1 = (TextView)findViewById(R.id.btn1);
        btn2 = (TextView)findViewById(R.id.btn2);
        btn3 = (TextView)findViewById(R.id.btn3);
        btn4 = (TextView)findViewById(R.id.btn4);
        btn5 = (TextView)findViewById(R.id.btn5);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.btn1:
                if(homeFragment.isHidden()){
                    fragmentTransaction.show(homeFragment);
                }
                fragmentTransaction.hide(askPriceFragment).hide(weatherFragment).hide(uploadWeatherFragment).hide(aboutFragment).commit();

                break;
            case R.id.btn2:
                if(askPriceFragment.isHidden()){
                    fragmentTransaction.show(askPriceFragment);
                }
                fragmentTransaction.hide(homeFragment).hide(weatherFragment).hide(uploadWeatherFragment).hide(aboutFragment).commit();
                break;
            case R.id.btn3:
                if(weatherFragment.isHidden()){
                    fragmentTransaction.show(weatherFragment);
                }
                fragmentTransaction.hide(homeFragment).hide(askPriceFragment).hide(uploadWeatherFragment).hide(aboutFragment).commit();
                break;
            case R.id.btn4:
                if(uploadWeatherFragment.isHidden()){
                    fragmentTransaction.show(uploadWeatherFragment);
                }
                fragmentTransaction.hide(homeFragment).hide(askPriceFragment).hide(weatherFragment).hide(aboutFragment).commit();
                break;
            case R.id.btn5:
                if(aboutFragment.isHidden()){
                    fragmentTransaction.show(aboutFragment);
                }
                fragmentTransaction.hide(homeFragment).hide(askPriceFragment).hide(weatherFragment).hide(uploadWeatherFragment).commit();
                break;
        }
    }
}
