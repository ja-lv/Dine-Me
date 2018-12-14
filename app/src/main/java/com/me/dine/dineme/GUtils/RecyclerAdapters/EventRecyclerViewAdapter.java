package com.me.dine.dineme.GUtils.RecyclerAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.dine.dineme.R;
import com.me.dine.dineme.ViewModel.Models.Event;

import java.util.ArrayList;



public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.NewsItemViewHolder> {

    Context mContext;
    ArrayList<Event> mData;


    public EventRecyclerViewAdapter(Context context, ArrayList<Event> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public EventRecyclerViewAdapter.NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.events_items, parent, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventRecyclerViewAdapter.NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewsList(ArrayList<Event> data){
        mData = data;
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView events_title;
        TextView events_description;
        TextView events_date;
        TextView events_foods;
        TextView events_location;
        TextView events_usersEmails;
        TextView events_groupName;


        public NewsItemViewHolder(View itemView) {
            super(itemView);
            events_title = (TextView) itemView.findViewById(R.id.events_title);
            events_description = (TextView) itemView.findViewById(R.id.events_description);
            events_date = (TextView) itemView.findViewById(R.id.events_date);
            events_foods = (TextView) itemView.findViewById(R.id.events_foods);
            events_location = (TextView) itemView.findViewById(R.id.events_location);
            events_usersEmails = (TextView) itemView.findViewById(R.id.events_usersEmails);
            events_groupName = (TextView) itemView.findViewById(R.id.events_groupName);
        }

        void bind(final int listIndex) {
            events_title.setText(mData.get(listIndex).getName());
            events_description.setText(mData.get(listIndex).getDescription());
            events_date.setText("Date " + mData.get(listIndex).getDate());
            events_foods.setText("Foods in event: " +mData.get(listIndex).getFoods().get(0));
            events_location.setText("Location: " + mData.get(listIndex).getLocation());
            events_usersEmails.setText("Contact: " + mData.get(listIndex).getUsersEmails().get(0));
            events_groupName.setText("Hosted by: " + mData.get(listIndex).getGroupName());
        }
    }
}
