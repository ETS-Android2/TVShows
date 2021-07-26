package com.jobsity.leonardoinvernizzi.tvseries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jobsity.leonardoinvernizzi.tvseries.R;
import com.jobsity.leonardoinvernizzi.tvseries.model.PersonSearch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PersonSearchListAdapter extends BaseAdapter {

    private final Context context;
    private final List<PersonSearch> people = new ArrayList<>();

    public PersonSearchListAdapter(Context context, List<PersonSearch> people) {
        this.context = context;
        //this.people;
        for (PersonSearch person: people) {
            if (person.getPerson().getImage() != null) {
                this.people.add(person);
            }
        }
    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public PersonSearch getItem(int position) {
        return people.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.show_list, parent, false);
        }

            ImageView imageView = convertView.findViewById(R.id.show_list_image);
            TextView textViewName = (TextView) convertView.findViewById(R.id.show_list_name);
            //TextView textViewYear = (TextView) convertView.findViewById(R.id.show_list_year);
            //TextView textViewRating = (TextView) convertView.findViewById(R.id.show_list_rating);

            try {
                Picasso.get().load(getItem(position).getPerson().getImage().getMedium()).into(imageView);
            } catch (NullPointerException e) {
                System.out.println(e.toString());
            }
            textViewName.setText(getItem(position).getPerson().getName());
            //textViewYear.setText(shows.get(position).getPremieredDate().get(Calendar.YEAR));
            //textViewRating.setText(shows.get(position).getRating().getAverage());

        return convertView;
    }
}