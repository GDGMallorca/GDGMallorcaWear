package com.gdgmallorcawear.views;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdgmallorcawear.R;

import java.util.ArrayList;

public final class ViewAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private final ArrayList<String> mViews;

    public ViewAdapter(Context context, ArrayList<String> views) {
        mInflater = LayoutInflater.from(context);
        mViews = views;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(
                mInflater.inflate(R.layout.item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        TextView view = (TextView) holder.itemView.findViewById(R.id.text);
        view.setText(mViews.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mViews.size();
    }


}
