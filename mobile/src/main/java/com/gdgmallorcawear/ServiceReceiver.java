package com.gdgmallorcawear;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.ArrayList;

/**
 * Created by jmliras on 13/07/14.
 */
public class ServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Recuperar eventos
        ArrayList<Event> eventList = CalendarUtils.getEvents(context);

        // Notificamos cada evento en Wear y Mobile haciendo uso de STACK
        for (Event event : eventList) {
            Intent viewIntent = new Intent(context, SendMailsActivity.class);
            viewIntent.putExtra(Utils.EXTRA_EVENT_ID, event.getEventCode());
            PendingIntent sendPendingIntent =
                    PendingIntent.getActivity(context, 0, viewIntent, 0);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(event.eventName)
                            .setContentText(context.getString(R.string.warning_late))
//                            .setContentIntent(viewPendingIntent)
                            .setGroup(Utils.GROUP_KEY_MESSAGES)
                            .addAction(R.drawable.ic_launcher,
                                    context.getString(R.string.notify_people), sendPendingIntent);

            // Get an instance of the NotificationManager service
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(context);

//  SET BACKGROUND?
//            NotificationCompat.WearableExtender wearableExtender =
//                    new NotificationCompat.WearableExtender()
//                            .setBackground(background);

            // Build the notification and issues it with notification manager.
            notificationManager.notify((int)event.eventCode, notificationBuilder.build());
        }
    }
}
