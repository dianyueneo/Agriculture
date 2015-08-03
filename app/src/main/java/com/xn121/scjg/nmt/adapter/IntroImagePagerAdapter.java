package com.xn121.scjg.nmt.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by hongge on 15/8/2.
 */
public class IntroImagePagerAdapter extends PagerAdapter{

    private List<View> views;
    private Context context;

    public IntroImagePagerAdapter(Context context) {
        this.context = context;
    }

    public void setViews(List<View> views) {
        this.views = views;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(views.get(position));

        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
