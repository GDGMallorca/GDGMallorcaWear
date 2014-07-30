package com.gdgmallorcawear.views;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;

import com.gdgmallorcawear.R;

import java.util.ArrayList;

public class ViewsActivity extends Activity implements WearableListView.ClickListener {

    private WearableListView mDataItemList;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_wearablelistview);
        mDataItemList = (WearableListView) findViewById(R.id.wear_wlv);
        mDataItemList.setAdapter(new ViewAdapter(this, getExamplesViews()));
        mDataItemList.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Integer position = (Integer) viewHolder.itemView.getTag();
        Intent intent = new Intent();
        if (position == 1 || position == 2) {
            intent.putExtra("extra", position);
            intent.setClass(this, CircledDelayedActivity.class);
            startActivity(intent);
        } else if (position == 3) {
            intent.setClass(this, GridViewPagerViewActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    public static ArrayList<String> getExamplesViews() {
        ArrayList<String> views = new ArrayList<String>();
        views.add("WearableListView");
        views.add("CircledImageView");
        views.add("DelayedConfirmationView");
        views.add("FragmentGridPagerAdapter");
        return views;
    }
}
