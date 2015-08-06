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
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = context.getResources().getString(R.string.app_name);
        notification.defaults = Notification.DEFAULT_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
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
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);
        return builder.getNotification();
    }

    @TargetApi(16)
    private Notification getNotification_3(String title, String message, PendingIntent contentIntent){
        Notification.Builder builder = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);
        return builder.build();
    }



}
