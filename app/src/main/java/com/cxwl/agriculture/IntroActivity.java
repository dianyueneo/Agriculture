package com.cxwl.agriculture;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.cxwl.agriculture.adapter.IntroImagePagerAdapter;

/**
 * Created by hongge on 15/8/1.
 */
public class IntroActivity extends Activity implements View.OnClickListener{

    private ViewPager viewPager;
    private IntroImagePagerAdapter adapter;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_intro);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        adapter = new IntroImagePagerAdapter(this);
        int images[] = {R.drawable.intro1, R.drawable.intro2, R.drawable.intro3, R.drawable.intro4, R.drawable.intro5, R.drawable.intro6, R.drawable.intro7};
        adapter.setImages(images);
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
