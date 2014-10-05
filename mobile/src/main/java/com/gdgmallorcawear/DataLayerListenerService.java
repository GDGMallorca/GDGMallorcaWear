package com.gdgmallorcawear;


import android.content.Intent;
import android.util.Log;

import com.gdgmallorcawear.utils.CalendarUtils;
import com.gdgmallorcawear.utils.Event;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;

/**
 * Listens to DataItems and Messages from the local node.
 */
public class DataLayerListenerService extends WearableListenerService {

    GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        sendEvents();
    }


    private void sendEvents() {
        ArrayList<Event> events = CalendarUtils.getEvents(this);
        for (Event event : events) {
            final PutDataMapRequest putDataMapRequest = CalendarUtils.toPutDataMapRequest(event);
            if (mGoogleApiClient.isConnected()) {
                Wearable.DataApi.putDataItem(
                        mGoogleApiClient, putDataMapRequest.asPutDataRequest());
            }
        }
    }

}
