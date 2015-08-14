package com.cxwl.agriculture.listener;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.cxwl.agriculture.R;
import com.cxwl.agriculture.util.WarningParser;
import com.smartapi.pn.client.Notifier;

public class MyNotifier implements Notifier{

    private Context context;
    private NotificationManager notificationManager;

    public MyNotifier(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notify(String type, int id, String title, String message, String detail) {
        Log.i("PN", "notify()...");

        Log.i("PN", "notificationType=" + type);
        Log.i("PN", "notificationId=" + id);
        Log.i("PN", "notificationTitle=" + title);
        Log.i("PN", "notificationContent=" + message);
        Log.i("PN", "notificationDetail=" + detail);

        Intent intent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;

        if(Build.VERSION.SDK_INT < 11){
            notification = getNotification_1(title, message, contentIntent);
        }else if(Build.VERSION.SDK_INT < 16){
            notification = getNotification_2(title, message, contentIntent);
        }else{
            notification = getNotification_3(title, message, contentIntent);
        }

        notificationManager.notify(id, notification);

    }

    private Notification getNotification_1(String title, String message, PendingIntent contentIntent){
        Notification notification = new Notification();
        notification.icon = getIcon(title);
        notification.tickerText = context.getResources().getString(R.string.app_name);
        notification.defaults = Notification.DEFAULT_ALL;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();


        notification.setLatestEventInfo(context, title, message,
                contentIntent);
        return notification;
    }

    @TargetApi(11)
    private Notification getNotification_2(String title, String message, PendingIntent contentIntent){
        Notification.Builder builder = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setSmallIcon(getIcon(title))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notification = builder.getNotification();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }

    @TargetApi(16)
    private Notification getNotification_3(String title, String message, PendingIntent contentIntent){
        Notification.Builder builder = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setSmallIcon(getIcon(title))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }


    private int getIcon(String title){
        String weatherkey = WarningParser.getWeatherKey(title);
        String colorkey = WarningParser.getColorKey(title);
        if(weatherkey == null || colorkey == null){
            return R.mipmap.ic_launcher;
        }
        return context.getResources().getIdentifier("hf_warning_"+ weatherkey +"_"+ colorkey, "drawable", context.getPackageName());
    }


}
