package com.cxwl.agriculture.listener;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cxwl.agriculture.MainActivity;
import com.cxwl.agriculture.R;
import com.cxwl.agriculture.util.NotificationUtil;
import com.smartapi.pn.client.Notifier;

public class MyNotifier implements Notifier{

    private Context context;

    public MyNotifier(Context context) {
        this.context = context;

    }

    public void notify(String type, int id, String title, String message, String detail) {
        Log.i("PN", "notify()...");

        Log.i("PN", "notificationType=" + type);
        Log.i("PN", "notificationId=" + id);
        Log.i("PN", "notificationTitle=" + title);
        Log.i("PN", "notificationContent=" + message);
        Log.i("PN", "notificationDetail=" + detail);

        Intent intent = new Intent(context, MainActivity.class);

        NotificationUtil.nofity(context, id, null, title, message,intent);
    }







}
