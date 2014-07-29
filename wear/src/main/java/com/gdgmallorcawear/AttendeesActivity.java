package com.gdgmallorcawear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class AttendeesActivity extends Activity implements WearableListView.ClickListener,
        DelayedConfirmationView.DelayedConfirmationListener {

    private WearableListView mDataItemList;
    private DelayedConfirmationView mDelayedConfirmationView;
    private Button mButton;
    private EventsAdapter mAdapter;
    private static Activity sContext;
    private ArrayList<Event> mEvents = new ArrayList<Event>();
    private static final int NUM_SECONDS = 2;
    private static final int SPEECH_REQUEST_CODE = 0;



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

        //Start the speech recognition
        displaySpeechRecognizer();

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


    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            Log.d("JM","Texto:"+spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
