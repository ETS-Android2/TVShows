package com.jobsity.leonardoinvernizzi.tvseries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jobsity.leonardoinvernizzi.tvseries.R;
import com.jobsity.leonardoinvernizzi.tvseries.model.CastCredits;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonShowAdapter extends BaseAdapter {
    private final Context context;
    private final List<CastCredits> castCredits;

    public PersonShowAdapter(Context context, List<CastCredits> castCredits) {
        this.context = context;
        this.castCredits = castCredits;
    }

    @Override
    public int getCount() {
        return castCredits.size();
    }

    @Override
    public CastCredits getItem(int position) {
        return castCredits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.episode_list, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.imageViewEpisode);
        TextView textView = convertView.findViewById(R.id.textViewEpisode);

        CastCredits castCredits = getItem(position);

        try {
            Picasso.get().load(castCredits.get_embedded().getShow().getImage().getMedium()).into(imageView);
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        textView.setText(castCredits.get_embedded().getShow().getName());
        return convertView;
    }
}
