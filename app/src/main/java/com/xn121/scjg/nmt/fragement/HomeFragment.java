package com.xn121.scjg.nmt.fragement;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.adapter.TabsViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by admin on 7/16/15.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private RequestQueue queue;
    private ViewPager viewPager;
    private ImageView btn_sell, btn_buy;
    private TabsViewPagerAdapter tabsViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_home, null);
            initView();
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void initView(){
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new HomeSellFragment());
        fragments.add(new HomeBuyFragment());
        tabsViewPagerAdapter = new TabsViewPagerAdapter(this.getChildFragmentManager(), fragments);
        viewPager.setAdapter(tabsViewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_sell = (ImageView)rootView.findViewById(R.id.btn_sell);
        btn_buy = (ImageView)rootView.findViewById(R.id.btn_buy);
        btn_sell.setOnClickListener(this);
        btn_buy.setOnClickListener(this);

        setSelected(0);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        queue = Volley.newRequestQueue(activity);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sell:
                setSelected(0);
                break;
            case R.id.btn_buy:
                setSelected(1);
                break;
            default:
                break;
        }

    }

    private void setSelected(int position){
        switch (position){
            case 0:
                btn_sell.setSelected(true);
                btn_buy.setSelected(false);
                viewPager.setCurrentItem(0);
                break;
            case 1:
                btn_sell.setSelected(false);
                btn_buy.setSelected(true);
                viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tabsViewPagerAdapter.getItem(viewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
    }
}
