package com.xn121.scjg.nmt;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.xn121.scjg.nmt.adapter.IntroImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongge on 15/8/1.
 */
public class IntroActivity extends Activity implements View.OnClickListener{

    private ViewPager viewPager;
    private IntroImagePagerAdapter adapter;

    private ImageView back;

    private List<View> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_intro);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        adapter = new IntroImagePagerAdapter(this);
        int images[] = {R.drawable.intro1, R.drawable.intro2, R.drawable.intro3, R.drawable.intro4, R.drawable.intro5, R.drawable.intro6, R.drawable.intro7};

        list = new ArrayList<View>();

        LayoutInflater inflater = LayoutInflater.from(this);

        for(int i=0;i<images.length;i++){
            View view = inflater.inflate(R.layout.activity_intro_item, null);
            ImageView iv = (ImageView)view.findViewById(R.id.iv);
            iv.setImageResource(images[i]);
            list.add(view);
        }

        adapter.setViews(list);
        viewPager.setAdapter(adapter);

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
