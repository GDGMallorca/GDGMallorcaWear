package com.gdgmallorcawear;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.gdgmallorcawear.views.ViewAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class AttendeesActivity extends Activity implements WearableListView.ClickListener,
        DelayedConfirmationView.DelayedConfirmationListener, DataApi.DataListener,
        MessageApi.MessageListener, NodeApi.NodeListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private WearableListView mDataItemList;
    private DelayedConfirmationView mDelayedConfirmationView;
    private Button mButton;
    private ViewAdapter mAdapter;
    private static Activity sContext;
    private ArrayList<String> mAttendes = new ArrayList<String>();
    private static final int NUM_SECONDS = 2;
    private static final String EXTRA_ARGS = "args";
    private GoogleApiClient mGoogleApiClient;


    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_attendees);
        sContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mAttendes = getIntent().getStringArrayListExtra(EXTRA_ARGS);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        initViews();
        sContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void initViews() {
        mDataItemList = (WearableListView) findViewById(R.id.pager);

        mDelayedConfirmationView = (DelayedConfirmationView) findViewById(R.id.delayed_confirmation);
        mDelayedConfirmationView.setTotalTimeMs(NUM_SECONDS * 1000);
        mDelayedConfirmationView.setListener(this);

        mButton = (Button) findViewById(R.id.btn_send_later);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new sendMails().execute();
                mDataItemList.setVisibility(View.GONE);
                mDelayedConfirmationView.setVisibility(View.VISIBLE);
                mDelayedConfirmationView.start();

            }
        });
        mDataItemList.setClickListener(this);
        mDataItemList.setAdapter(new ViewAdapter(this, mAttendes));
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
        Wearable.MessageApi.addListener(mGoogleApiClient, this);
        Wearable.NodeApi.addListener(mGoogleApiClient, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

    }

    @Override
    public void onPeerConnected(Node node) {

    }

    @Override
    public void onPeerDisconnected(Node node) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private Collection<String> getNodes() {
        HashSet<String> results = new HashSet<String>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();

        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }

        return results;
    }

    private class sendMails extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            Collection<String> nodes = getNodes();
            Log.e("inaki", "*******************");
            for (String node : nodes) {
                Log.e("inaki", "*******************");
                Wearable.MessageApi.sendMessage(
                        mGoogleApiClient, node, "cc", new byte[0]).setResultCallback(
                        new ResultCallback<MessageApi.SendMessageResult>() {
                            @Override
                            public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                Log.e("inaki","**"+sendMessageResult.getStatus().isSuccess());
                                if (!sendMessageResult.getStatus().isSuccess()) {
                                }
                            }

                        }
                );

            }
            return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


}
