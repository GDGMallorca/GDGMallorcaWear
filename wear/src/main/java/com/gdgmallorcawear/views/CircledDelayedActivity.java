package com.gdgmallorcawear.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;
import android.widget.Button;

import com.gdgmallorcawear.R;

public class CircledDelayedActivity extends Activity implements DelayedConfirmationView.DelayedConfirmationListener {

    private CircledImageView mCircledImageView;
    private DelayedConfirmationView mDelayedConfirmationView;
    private Button mButton;
    private static final int NUM_SECONDS = 1;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.views);
        initViews();
        int type = getIntent().getIntExtra("extra", 0);
        if (type == 1) {
            mDelayedConfirmationView.setVisibility(View.GONE);
            mButton.setVisibility(View.GONE);
        } else if (type == 2) {
            mCircledImageView.setVisibility(View.GONE);
        }
    }

    private void setListeners() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelayedConfirmationView.setImageResource(R.drawable.ic_launcher);
                mDelayedConfirmationView.start();
            }
        });
    }

    private void initViews() {
        mCircledImageView = (CircledImageView) findViewById(R.id.views_civ);
        mDelayedConfirmationView = (DelayedConfirmationView) findViewById(R.id.views_dcv);
        mButton = (Button) findViewById(R.id.btn);
        mDelayedConfirmationView.setTotalTimeMs(NUM_SECONDS * 1000);
        mDelayedConfirmationView.setListener(this);
        setListeners();
    }

    @Override
    public void onTimerFinished(View view) {
        mDelayedConfirmationView.setImageResource(R.drawable.bugdroid);
    }

    @Override
    public void onTimerSelected(View view) {
        view.setPressed(true);
        ((DelayedConfirmationView) view).setListener(null);
    }
}
