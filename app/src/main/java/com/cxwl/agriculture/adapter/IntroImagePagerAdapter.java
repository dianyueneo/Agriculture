package com.cxwl.agriculture.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by hongge on 15/8/2.
 */
public class IntroImagePagerAdapter extends PagerAdapter{

    private int[] images;
    private Context context;
    private LruCache<Integer, Bitmap> mMemoreyCache;

    public IntroImagePagerAdapter(Context context) {
        this.context = context;
        initLruCache();
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
        imageView.setImageBitmap(showCacheBitmap(context, images[position]));
//        imageView.setImageBitmap(getImageBitmap(context, images[position]));
//        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    private void initLruCache(){
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/8;
        mMemoreyCache = new LruCache<Integer, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    private Bitmap showCacheBitmap(Context context, int resId){
        if(getBitmapFromMemoryCache(resId) != null){
            return getBitmapFromMemoryCache(resId);
        }else{
            Bitmap bitmap = getImageBitmap(context, resId);
            addBitmapToMemoryCache(resId, bitmap);
            return bitmap;
        }
    }

    private void addBitmapToMemoryCache(int resId, Bitmap bitmap){
        if(getBitmapFromMemoryCache(resId) != null && bitmap != null){
            mMemoreyCache.put(resId, bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryCache(int resId){
        return mMemoreyCache.get(resId);
    }

    private Bitmap getImageBitmap(Context context, int resId){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        InputStream is = context.getResources().openRawResource(resId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = getImageScale(context, resId, dm.widthPixels, dm.heightPixels);
        return BitmapFactory.decodeStream(is, null, options);
    }

    private int getImageScale(Context context, int ResId, int reqWidth, int reqHeight) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), ResId, option);

        int scale = 1;
        while (option.outWidth / scale >= reqWidth || option.outHeight / scale >= reqHeight) {
            scale *= 2;
        }
        return scale;
    }
}
