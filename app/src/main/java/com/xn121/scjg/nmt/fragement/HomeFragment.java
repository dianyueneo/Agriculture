package com.xn121.scjg.nmt.fragement;


import android.app.Activity;
import android.common.view.SlidingTabLayout;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_home, null);
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        initView();

        return rootView;
    }

    private void initView(){
        slidingTabLayout = (SlidingTabLayout)rootView.findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new HomeSellFragment());
        fragments.add(new HomeBuyFragment());
        viewPager.setAdapter(new TabsViewPagerAdapter(this.getChildFragmentManager(), fragments));
        int[] resIds = {R.layout.custom_tab,R.layout.custom_tab};
        int[] resIds_img = {R.layout.custom_tab_img_sell,R.layout.custom_tab_img_buy};
        slidingTabLayout.setCustomTabView(resIds, resIds_img);
//        slidingTabLayout.setCustomTabView(R.layout.custom_tab, 0);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.indicatorcolor);
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.dividercolor);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        queue = Volley.newRequestQueue(activity);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case 0:
                break;
            default:
                break;
        }

    }
}
