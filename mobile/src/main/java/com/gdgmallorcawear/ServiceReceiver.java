package com.gdgmallorcawear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by jmliras on 13/07/14.
 */
public class ServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Recuperar eventos
        ArrayList<Event> eventList = CalendarUtils.getEvents(context);


        //TODO: Si hay algun evento proximamente enviamos una notificacion y la preparamos para que envie mails
        //TODO: Los correos los sacamos de eventList, cada evento tiene un getAttendeesMail

    }
}
