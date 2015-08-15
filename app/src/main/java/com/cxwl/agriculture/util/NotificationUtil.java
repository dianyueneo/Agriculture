package com.cxwl.agriculture.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.cxwl.agriculture.R;

import java.io.InputStream;

/**
 * Created by admin on 15/8/14.
 */
public class NotificationUtil {

    public static void nofity(Context context, int id, String icon_text, String title, String message, Intent intent){
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        int icon = context.getResources().getIdentifier(icon_text, "drawable", context.getPackageName());

        Notification notification = null;
        if(Build.VERSION.SDK_INT < 11){
            notification = getNotification_1(context, icon, title, message, contentIntent);
        }else if(Build.VERSION.SDK_INT < 16){
            notification = getNotification_2(context, icon, title, message, contentIntent);
        }else{
            notification = getNotification_3(context, icon, title, message, contentIntent);
        }

        notificationManager.notify(id, notification);
    }

    private static Notification getNotification_1(Context context, int icon, String title, String message, PendingIntent contentIntent){

        Notification notification = new Notification();
        notification.icon = icon;
        notification.tickerText = context.getResources().getString(R.string.app_name);
        notification.defaults = Notification.DEFAULT_ALL;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();


        notification.setLatestEventInfo(context, title, message,
                contentIntent);
        return notification;
    }

    @TargetApi(11)
    private static Notification getNotification_2(Context context, int icon, String title, String message, PendingIntent contentIntent){
        Notification.Builder builder = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getIcon(context, icon))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notification = builder.getNotification();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }

    @TargetApi(16)
    private static Notification getNotification_3(Context context, int icon, String title, String message, PendingIntent contentIntent){
        Notification.Builder builder = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getIcon(context, icon))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }

    public static Bitmap getIcon(Context context, int icon){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), icon);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;

        Matrix matrix = new Matrix();
        float scaleWidth = 24*density/bitmap.getWidth();
        float scaleHeight = 24*density/bitmap.getHeight();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0 ,bitmap.getWidth(),bitmap.getHeight(), matrix, true);
    }

}
