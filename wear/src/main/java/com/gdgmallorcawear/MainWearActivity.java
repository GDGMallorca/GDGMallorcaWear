package com.gdgmallorcawear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;


public class MainWearActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, DataApi.DataListener, MessageApi.MessageListener,
        NodeApi.NodeListener, WearableListView.ClickListener {

    private static final String PATH = "/calendar";
    private static final String EVENT_CODE = "id";
    private static final String EVENT_BEGIN = "begin";
    private static final String EVENT_TITLE = "title";
    private static final String EVENT_ATTENDEES = "attendes";


    private GoogleApiClient mGoogleApiClient;
    private WearableListView mDataItemList;
    private EventsAdapter mAdapter;
    private static Activity sContext;
    private ArrayList<Event> mEvents = new ArrayList<Event>();

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_my);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDataItemList = (WearableListView) findViewById(R.id.pager);
        mDataItemList.setClickListener(this);
        mDataItemList.setAdapter(mAdapter);

        sContext = this;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.DataApi.removeListener(mGoogleApiClient, this);
        Wearable.MessageApi.removeListener(mGoogleApiClient, this);
        Wearable.NodeApi.removeListener(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
        Wearable.MessageApi.addListener(mGoogleApiClient, this);
        Wearable.NodeApi.addListener(mGoogleApiClient, this);

    }

    @Override
    public void onConnectionSuspended(int cause) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    }


    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.close();
        for (DataEvent event : events) {
            DataMapItem mapDataItem = DataMapItem.fromDataItem(event.getDataItem());
            DataMap data = mapDataItem.getDataMap();
            if (event.getDataItem().getUri().getPath().toString().equals(PATH)) {
                final Event getEvent = new Event();
                getEvent.setEventCode(data.getLong(EVENT_CODE));
                getEvent.setEventName(data.getString(EVENT_TITLE));
                getEvent.setAttendeesMail(data.getStringArrayList(EVENT_ATTENDEES));
                getEvent.setEventName(data.getString(EVENT_TITLE));
                getEvent.setBegin(data.getLong(EVENT_BEGIN));
                mEvents.add(getEvent);
            }
        }

        drawAdapter();
    }


    @Override
    public void onMessageReceived(MessageEvent event) {
    }

    @Override
    public void onPeerConnected(Node node) {
    }

    @Override
    public void onPeerDisconnected(Node node) {
    }


    private void drawAdapter() {
        mAdapter = new EventsAdapter(sContext, mEvents);
        mAdapter.notifyDataSetChanged();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDataItemList.setAdapter(mAdapter);
            }
        });

    }


    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        startAttendeesActivity();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    private void startAttendeesActivity() {
        startActivity(new Intent(this, AttendeesActivity.class));

    }
}
