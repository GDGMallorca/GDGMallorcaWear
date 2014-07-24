package com.gdgmallorcawear;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public final class EventsAdapter extends WearableListView.Adapter {
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final ArrayList<Event> mEvents;

    public EventsAdapter(Context context, ArrayList<Event> events) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mEvents = events;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(
                mInflater.inflate(R.layout.item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        TextView view = (TextView) holder.itemView.findViewById(R.id.text);
        TextView date = (TextView) holder.itemView.findViewById(R.id.date);
        view.setText(mEvents.get(position).getEventName());
        String dateString = DateFormat.format("dd/MM/yyyy hh:mm", new Date(mEvents.get(position).getBegin())).toString();
        date.setText(dateString);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }


}
