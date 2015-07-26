package com.xn121.scjg.nmt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.xn121.scjg.nmt.fragement.AboutFragment;
import com.xn121.scjg.nmt.fragement.AskPriceFragment;
import com.xn121.scjg.nmt.fragement.HomeFragment;
import com.xn121.scjg.nmt.fragement.IntroductionsFragment;
import com.xn121.scjg.nmt.fragement.WeatherFragment;


public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater inflater;
    private Toolbar toolbar;
    private TextView toolbartitle;


    private Class fragmentArray[] = {HomeFragment.class , AskPriceFragment.class, WeatherFragment.class, AboutFragment.class, IntroductionsFragment.class};
    private int imageViewArray[] = {R.drawable.home_sel,R.drawable.ask,R.drawable.weather,R.drawable.about,R.drawable.explan};
    private int textArray[] = {R.string.home, R.string.askprice, R.string.weather, R.string.about, R.string.introductions};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.getWindow().setBackgroundDrawable(null);

        initToolbar();
        initView();
    }

    private void initToolbar(){
        toolbar = (Toolbar)this.findViewById(R.id.id_toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbartitle = (TextView)this.findViewById(R.id.toolbartitle);
        toolbartitle.setText(this.getResources().getString(textArray[0]));
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

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                toolbartitle.setText(tabId);
            }
        });

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
}
