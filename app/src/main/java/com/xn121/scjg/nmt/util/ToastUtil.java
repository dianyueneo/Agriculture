package com.xn121.scjg.nmt.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hongge on 15/7/25.
 */
public class ToastUtil {
    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }
}
