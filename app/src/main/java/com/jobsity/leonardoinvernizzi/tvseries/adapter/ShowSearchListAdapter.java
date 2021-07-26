package com.jobsity.leonardoinvernizzi.tvseries.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jobsity.leonardoinvernizzi.tvseries.model.ShowSearch;

import java.util.List;

public class ShowSearchListAdapter extends BaseAdapter {

    private final Context context;
    private final List<ShowSearch> showSearchList;

    public ShowSearchListAdapter(Context context, List<ShowSearch> showSearchList) {
        this.context = context;
        this.showSearchList = showSearchList;
    }

    @Override
    public int getCount() {
        return showSearchList.size();
    }

    @Override
    public ShowSearch getItem(int position) {
        return showSearchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return ShowListAdapter.getViewShow(context, getItem(position).getShow(), convertView, parent);
    }
}
