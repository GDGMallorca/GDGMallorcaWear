package com.gdgmallorcawear.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.gdgmallorcawear.BuildConfig;
import com.gdgmallorcawear.ServiceReceiver;

import java.util.Date;

/**
 * Created by jmliras on 13/07/14.
 */
public class Utils {

    public static long REFRESH_TIME = 600000; // 10 minutos
    public static long DEBUG_REFRESH_TIME = 60000; // 1 minuto
    public static String EXTRA_EVENT_ID = "EXTRA_EVENT_ID";
    public final static String GROUP_KEY_MESSAGES = "group_key_messages";
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    public static void startService(Context context) {

        Intent intent = new Intent(context, ServiceReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long horaInicio = new Date().getTime();
        if (horaInicio < new Date().getTime()) {
            horaInicio += REFRESH_TIME;
        }

        if (BuildConfig.DEBUG) am.setRepeating(AlarmManager.RTC_WAKEUP, horaInicio, DEBUG_REFRESH_TIME, sender);
        else am.setRepeating(AlarmManager.RTC_WAKEUP, horaInicio, REFRESH_TIME, sender);
    }

    public static void stopService(Context context) {

        Intent intent = new Intent(context, ServiceReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
}
