package com.gdgmallorcawear;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

import java.util.ArrayList;

public class ServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Recuperar eventos
        ArrayList<Event> eventList = CalendarUtils.getEvents(context);

        // Inflamos las respuestas
        String replyLabel = context.getResources().getString(R.string.reply_label);
        String[] replyChoices = context.getResources().getStringArray(R.array.reply_choices);

        RemoteInput remoteInput = new RemoteInput.Builder(Utils.EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();

        // Notificamos cada evento en Wear y Mobile haciendo uso de STACK
        for (Event event : eventList) {
            Intent viewIntent = new Intent(context, SendMailsActivity.class);
            viewIntent.putExtra(Utils.EXTRA_EVENT_ID, event.getEventCode());
            viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent sendPendingIntent =
                    PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_ONE_SHOT);

            // Create the reply action and add the remote input
            NotificationCompat.Action replyAction =
                    new NotificationCompat.Action.Builder(R.drawable.ic_reply,
                            context.getString(R.string.reply_label), sendPendingIntent)
                            .addRemoteInput(remoteInput)
                            .build();

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(event.eventName)
                            .setContentText(context.getString(R.string.warning_late))
//                            .setContentIntent(viewPendingIntent)
                            .setGroup(Utils.GROUP_KEY_MESSAGES)

                            // ADD Auto-reply action
                            .addAction(R.drawable.ic_launcher,
                                    context.getString(R.string.notify_people), sendPendingIntent)

                            // ADD Custom Reply Action
                            .addAction(replyAction);
//                            .extend(new NotificationCompat.WearableExtender().addAction(replyAction));
//
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
