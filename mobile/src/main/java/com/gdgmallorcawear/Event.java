package com.gdgmallorcawear;

import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;

import java.util.ArrayList;

/**
 * Created by jmliras on 13/07/14.
 */
public class Event {

    String eventName;
    ArrayList<String> attendeesMail;
    long eventCode;
    long begin;


    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setAttendeesMail(ArrayList<String> attendeesMail) {
        this.attendeesMail = attendeesMail;
    }

    public void setEventCode(long eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public ArrayList<String> getAttendeesMail() {
        return attendeesMail;
    }

    public long getEventCode() {
        return eventCode;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }
}
