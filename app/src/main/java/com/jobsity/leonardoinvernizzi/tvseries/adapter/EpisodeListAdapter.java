package com.jobsity.leonardoinvernizzi.tvseries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jobsity.leonardoinvernizzi.tvseries.R;
import com.jobsity.leonardoinvernizzi.tvseries.model.Episode;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodeListAdapter extends BaseAdapter {

    private final Context context;
    private final List<Episode> episodes;

    public EpisodeListAdapter(Context context, List<Episode> episodes) {
        this.context = context;
        this.episodes = episodes;
    }

    @Override
    public int getCount() {
        return episodes.size();
    }

    @Override
    public Episode getItem(int position) {
        return episodes.get(position);
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

        Episode episode = getItem(position);

        try {
            Picasso.get().load(episode.getImage().getMedium()).into(imageView);
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        textView.setText(episode.getName());
        return convertView;
    }
}
