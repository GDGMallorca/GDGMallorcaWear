package com.gdgmallorcawear;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;

import com.gdgmallorcawear.MockData.Mocks;
import com.gdgmallorcawear.MockData.ViewAdapter;

public class ViewsActivity extends Activity implements WearableListView.ClickListener {

    private WearableListView mDataItemList;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_my);
        mDataItemList = (WearableListView) findViewById(R.id.wear_wlv);
        mDataItemList.setAdapter(new ViewAdapter(this, Mocks.getExamplesViews()));
        mDataItemList.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        startActivity(new Intent(this, CircledDelayedActivity.class));
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
