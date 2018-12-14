package com.me.dine.dineme;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.dine.dineme.ViewModel.Models.Event;
import com.me.dine.dineme.ViewModel.Models.Group;

import java.util.ArrayList;



public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.NewsItemViewHolder> {

    Context mContext;
    ArrayList<Event> mNews;


    public EventRecyclerViewAdapter(Context context, ArrayList<Event> news) {
        this.mContext = context;
        this.mNews = news;
    }

    @Override
    public EventRecyclerViewAdapter.NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.popular_group_items, parent, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventRecyclerViewAdapter.NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView events_title;
        TextView events_description;


        public NewsItemViewHolder(View itemView) {
            super(itemView);
            events_title = (TextView) itemView.findViewById(R.id.events_title);
            events_description = (TextView) itemView.findViewById(R.id.events_description);
        }

        void bind(final int listIndex) {
            events_title.setText(mNews.get(listIndex).getName());
            events_description.setText(mNews.get(listIndex).getDescription());

        }
    }
}
