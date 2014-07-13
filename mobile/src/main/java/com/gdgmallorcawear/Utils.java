package com.gdgmallorcawear;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

/**
 * Created by jmliras on 13/07/14.
 */
public class Utils {

    public static long REFRESH_TIME = 600000; //10 minutos

    public static void startService(Context context) {

        Intent intent = new Intent(context, ServiceReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long horaInicio = new Date().getTime();
        if (horaInicio < new Date().getTime()) {
            horaInicio += REFRESH_TIME;
        }
        am.setRepeating(AlarmManager.RTC_WAKEUP, horaInicio, REFRESH_TIME, sender);
    }

    public static void stopService(Context context) {

        Intent intent = new Intent(context, ServiceReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
}
