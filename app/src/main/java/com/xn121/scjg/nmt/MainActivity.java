package com.xn121.scjg.nmt;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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


public class MainActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater inflater;


    private Class fragmentArray[] = {HomeFragment.class , AskPriceFragment.class, WeatherFragment.class, AboutFragment.class, IntroductionsFragment.class};
    private int imageViewArray[] = {R.drawable.home_sel,R.drawable.home_sel,R.drawable.home_sel,R.drawable.home_sel,R.drawable.home_sel};
    private int textArray[] = {R.string.home, R.string.askprice, R.string.weather, R.string.about, R.string.introductions};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        View view = inflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageview);
        imageView.setImageResource(imageViewArray[index]);

        TextView textView = (TextView)view.findViewById(R.id.textview);
        textView.setText(this.getResources().getString(textArray[index]));

        return view;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
