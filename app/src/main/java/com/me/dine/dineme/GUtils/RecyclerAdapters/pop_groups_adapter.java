package com.me.dine.dineme.GUtils.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.dine.dineme.R;
import com.me.dine.dineme.ViewModel.Models.Group;

import java.util.ArrayList;
import java.util.List;

public class pop_groups_adapter extends RecyclerView.Adapter<pop_groups_adapter.NewsItemViewHolder> {

    Context mContext;
    ArrayList<Group> mGroups;

    public pop_groups_adapter(Context context, ArrayList<Group> groups) {
        this.mContext = context;
        this.mGroups = groups;
    }

    public void setNewList(List<Group> group){
        mGroups = (ArrayList) group;
    }

    @Override
    public pop_groups_adapter.NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.popular_group_items, parent, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(pop_groups_adapter.NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView pop_groups_title;
        TextView pop_groups_desc;
        TextView pop_groups_foods;
        TextView pop_groups_location;


        public NewsItemViewHolder(View itemView) {
            super(itemView);
            pop_groups_title = (TextView) itemView.findViewById(R.id.pop_groups_title);
            pop_groups_desc = (TextView) itemView.findViewById(R.id.pop_groups_desc);
            pop_groups_foods = (TextView) itemView.findViewById(R.id.pop_groups_foods);
            pop_groups_location = (TextView) itemView.findViewById(R.id.pop_groups_location);
        }

        void bind(final int listIndex) {
            pop_groups_title.setText(mGroups.get(listIndex).getName());
            pop_groups_desc.setText(mGroups.get(listIndex).getDescription());
            pop_groups_foods.setText(mGroups.get(listIndex).getFoods().get(0));
            pop_groups_location.setText(mGroups.get(listIndex).getLocation());
        }
    }
}