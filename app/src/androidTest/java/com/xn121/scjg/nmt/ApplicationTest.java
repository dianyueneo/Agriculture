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
        int a = 1 << 0;//0001
        int b = 1 << 1;//0010
        int c = 1 << 2;//0100
        int d = 1 << 3;//1000


        Log.i("test", "====="+((9 & a) == a));
        Log.i("test", "====="+((9 & b) == b));
        Log.i("test", "====="+((9 & c) == c));
        Log.i("test", "====="+((9 & d) == d));
    }
}