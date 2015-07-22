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

    class A{

    }

    class B extends A {

    }

    class C extends B {

    }

    public void test(){
        C c = new C();
        B b = new B();
        A a = new A();

        Log.i("tag",""+A.class.isInstance(c));
        Log.i("tag",""+B.class.isInstance(c));
        Log.i("tag",""+C.class.isInstance(c));

    }
}