package com.cxwl.agriculture;

import android.app.Application;
import android.os.Environment;
import android.test.ApplicationTestCase;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test(){
        DisplayMetrics dm = this.getContext().getResources().getDisplayMetrics();
        Log.i("test", "=====" + dm.widthPixels);
    }

    public void test2(){
        Log.i("test", "=====" + Environment.getExternalStorageDirectory().getPath());
    }
}