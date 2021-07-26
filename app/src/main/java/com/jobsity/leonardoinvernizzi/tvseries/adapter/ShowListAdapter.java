package com.jobsity.leonardoinvernizzi.tvseries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jobsity.leonardoinvernizzi.tvseries.R;
import com.jobsity.leonardoinvernizzi.tvseries.model.Show;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowListAdapter extends BaseAdapter {

    private final Context context;
    private final List<Show> shows;

    public ShowListAdapter(Context context, List<Show> shows) {
        this.context = context;
        this.shows = shows;
    }

    @Override
    public int getCount() {
        return shows.size();
    }

    @Override
    public Show getItem(int position) {
        return shows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewShow(context, getItem(position), convertView, parent);
    }

    public static View getViewShow(Context context, Show show, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.show_list, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.show_list_image);
        TextView textViewName = (TextView) convertView.findViewById(R.id.show_list_name);
        //TextView textViewYear = (TextView) convertView.findViewById(R.id.show_list_year);
        //TextView textViewRating = (TextView) convertView.findViewById(R.id.show_list_rating);

        try {
            Picasso.get().load(show.getImage().getMedium()).into(imageView);
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        textViewName.setText(show.getName());
        //textViewYear.setText(shows.get(position).getPremieredDate().get(Calendar.YEAR));
        //textViewRating.setText(shows.get(position).getRating().getAverage());

        return convertView;
    }

}
