package com.xn121.scjg.nmt.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by hongge on 15/8/2.
 */
public class IntroImagePagerAdapter extends PagerAdapter{

    private int[] images;
    private Context context;

    public IntroImagePagerAdapter(Context context) {
        this.context = context;
    }

    public void setImages(int[] images) {
        this.images = images;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
