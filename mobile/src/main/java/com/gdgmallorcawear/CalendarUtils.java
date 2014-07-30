package com.gdgmallorcawear;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import android.text.format.DateUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jmliras on 13/07/14.
 */
public class CalendarUtils {

    private static final String[] COLS = new String[] {
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.DESCRIPTION,
            CalendarContract.Instances.EVENT_LOCATION,
            CalendarContract.Instances.EVENT_ID
    };

    public static ArrayList<Event> getEvents(Context context){
        Cursor mCursor = null;

        //Consultamos el calendario para un dia
        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        long now = new Date().getTime();
        ContentUris.appendId(eventsUriBuilder, now);
        ContentUris.appendId(eventsUriBuilder, now
                + DateUtils.WEEK_IN_MILLIS);
        Uri eventsUri = eventsUriBuilder.build();

        ArrayList<Event> eventList = new ArrayList<Event>();

        mCursor = context.getContentResolver().query(eventsUri, COLS, null, null, CalendarContract.Instances.BEGIN + " ASC");

        if(mCursor.getCount() > 0) {
            if (mCursor.moveToFirst()) {
                do {

                    Event event = new Event();
                    event.setEventName(mCursor.getString(0));
                    event.setEventCode(mCursor.getLong(4));

                    Log.d("JM", "Evento-->" + mCursor.getString(0));

                    ArrayList<String> attendeeList = new ArrayList<String>();
                    Cursor eventAttendeesCursor = context.getContentResolver().query(CalendarContract.Attendees.CONTENT_URI, new String[]{CalendarContract.Attendees.ATTENDEE_NAME, CalendarContract.Attendees.ATTENDEE_EMAIL, CalendarContract.Attendees.EVENT_ID, CalendarContract.Attendees.ATTENDEE_STATUS}, CalendarContract.Attendees.EVENT_ID + " = " + mCursor.getLong(4), null, null);
                    if (eventAttendeesCursor.getCount() > 0) {

                        if (eventAttendeesCursor.moveToFirst()) {
                            do {
                                if (eventAttendeesCursor.getInt(3) == CalendarContract.Attendees.ATTENDEE_STATUS_ACCEPTED) {
                                    attendeeList.add(eventAttendeesCursor.getString(1));
                                    Log.d("JM", "aceptado!-->" + eventAttendeesCursor.getString(1));
                                } else {
                                    Log.d("JM", "no aceptado!-->" + eventAttendeesCursor.getString(0));
                                }
                            } while (eventAttendeesCursor.moveToNext());
                        }
                    }
                    eventAttendeesCursor.close();
                    event.setAttendeesMail(attendeeList);


                    eventList.add(event);

                } while (mCursor.moveToNext());
            }
        }

        mCursor.close();

        return eventList;
    }


    public static Event getSingleEvent(Context context, Long eventId){
        Cursor mCursor = null;

        //Consultamos el calendario para un dia
        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        long now = new Date().getTime();
        ContentUris.appendId(eventsUriBuilder, now);
        ContentUris.appendId(eventsUriBuilder, now
                + DateUtils.WEEK_IN_MILLIS);
        Uri eventsUri = eventsUriBuilder.build();

        Event event = null;

        mCursor = context.getContentResolver().query(eventsUri, COLS, null, null, CalendarContract.Instances.BEGIN + " ASC");

        if(mCursor.getCount() > 0) {
            if (mCursor.moveToFirst()) {
                do {
                    if (eventId != mCursor.getLong(4)) continue;
                    event = new Event();
                    event.setEventName(mCursor.getString(0));
                    event.setEventCode(mCursor.getLong(4));

                    Log.d("JM", "Evento-->" + mCursor.getString(0));

                    ArrayList<String> attendeeList = new ArrayList<String>();
                    Cursor eventAttendeesCursor = context.getContentResolver().query(CalendarContract.Attendees.CONTENT_URI, new String[]{CalendarContract.Attendees.ATTENDEE_NAME, CalendarContract.Attendees.ATTENDEE_EMAIL, CalendarContract.Attendees.EVENT_ID, CalendarContract.Attendees.ATTENDEE_STATUS}, CalendarContract.Attendees.EVENT_ID + " = " + mCursor.getLong(4), null, null);
                    if (eventAttendeesCursor.getCount() > 0) {

                        if (eventAttendeesCursor.moveToFirst()) {
                            do {
                                if (eventAttendeesCursor.getInt(3) == CalendarContract.Attendees.ATTENDEE_STATUS_ACCEPTED) {
                                    attendeeList.add(eventAttendeesCursor.getString(1));
                                    Log.d("JM", "aceptado!-->" + eventAttendeesCursor.getString(1));
                                } else {
                                    if (BuildConfig.DEBUG) attendeeList.add(eventAttendeesCursor.getString(1));
                                    Log.d("JM", "no aceptado!-->" + eventAttendeesCursor.getString(0));
                                }
                            } while (eventAttendeesCursor.moveToNext());
                        }
                    }
                    eventAttendeesCursor.close();
                    event.setAttendeesMail(attendeeList);
                    break;
                } while (mCursor.moveToNext());
            }
        }

        mCursor.close();

        return event;
    }

}
