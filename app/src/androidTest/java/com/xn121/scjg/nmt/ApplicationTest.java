package com.xn121.scjg.nmt;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test(){
        Log.i("test", "====="+3/3);
        Log.i("test", "====="+4/3);
        Log.i("test", "====="+5/3);
    }
}