package com.gdgmallorcawear;

import android.app.Activity;
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


public class AttendeesActivity extends Activity implements WearableListView.ClickListener {

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
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        Log.e("inaki", "xxxxxx");
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
