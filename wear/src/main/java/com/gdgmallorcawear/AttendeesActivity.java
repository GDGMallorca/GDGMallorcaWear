package com.gdgmallorcawear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;


public class AttendeesActivity extends Activity implements WearableListView.ClickListener,
        DelayedConfirmationView.DelayedConfirmationListener {

    private WearableListView mDataItemList;
    private DelayedConfirmationView mDelayedConfirmationView;
    private Button mButton;
    private EventsAdapter mAdapter;
    private static Activity sContext;
    private ArrayList<Event> mEvents = new ArrayList<Event>();
    private static final int NUM_SECONDS = 2;


    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.attendees);
        sContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDataItemList = (WearableListView) findViewById(R.id.pager);
        mDelayedConfirmationView = (DelayedConfirmationView) findViewById(R.id.delayed_confirmation);
        mDelayedConfirmationView.setTotalTimeMs(NUM_SECONDS * 1000);
        mDelayedConfirmationView.setListener(this);

        mButton = (Button) findViewById(R.id.btn_send_later);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataItemList.setVisibility(View.GONE);
                mDelayedConfirmationView.setVisibility(View.VISIBLE);
                mDelayedConfirmationView.start();

            }
        });

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
    }

    @Override
    public void onTopEmptyRegionClick() {

    }


    @Override
    public void onTimerFinished(View view) {
        mDelayedConfirmationView.setImageDrawable(getResources().getDrawable(R.drawable.ic_ok));
        mButton.setText(getResources().getString(R.string.sent));
    }

    @Override
    public void onTimerSelected(View view) {
        view.setPressed(true);
        ((DelayedConfirmationView) view).setListener(null);
    }
}
