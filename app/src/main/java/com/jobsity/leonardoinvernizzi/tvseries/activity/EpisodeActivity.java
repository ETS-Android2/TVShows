package com.jobsity.leonardoinvernizzi.tvseries.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsity.leonardoinvernizzi.tvseries.R;
import com.jobsity.leonardoinvernizzi.tvseries.Utils;
import com.jobsity.leonardoinvernizzi.tvseries.model.Episode;
import com.squareup.picasso.Picasso;

public class EpisodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        ImageView imageView = findViewById(R.id.imageViewEpisode);
        TextView textViewEpisodeTitle = findViewById(R.id.textViewEpisodeTitle);
        TextView textViewEpisodeNumber = findViewById(R.id.textViewEpisodeNumber);
        TextView textViewEpisodeSummary = findViewById(R.id.textViewEpisodeSummary);

        Intent intent = getIntent();
        Episode episode = (Episode) intent.getSerializableExtra(Utils.EXTRA_MESSAGE);
        try {
            Picasso.get().load(episode.getImage().getOriginal()).into(imageView);
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        textViewEpisodeTitle.setText(episode.getName());
        textViewEpisodeNumber.setText("Season " + episode.getSeason() + " ● Episode " + episode.getNumber());
        textViewEpisodeSummary.setText(episode.getSummary());
        getSupportActionBar().setTitle(episode.getName());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}