package com.jobsity.leonardoinvernizzi.tvseries.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsity.leonardoinvernizzi.tvseries.R;
import com.jobsity.leonardoinvernizzi.tvseries.ServiceGenerator;
import com.jobsity.leonardoinvernizzi.tvseries.Utils;
import com.jobsity.leonardoinvernizzi.tvseries.adapter.EpisodeListAdapter;
import com.jobsity.leonardoinvernizzi.tvseries.dao.EpisodeDAO;
import com.jobsity.leonardoinvernizzi.tvseries.dao.FavouritesDAO;
import com.jobsity.leonardoinvernizzi.tvseries.dao.FavouritesDAO_Impl;
import com.jobsity.leonardoinvernizzi.tvseries.dao.SeasonDAO;
import com.jobsity.leonardoinvernizzi.tvseries.model.Episode;
import com.jobsity.leonardoinvernizzi.tvseries.model.Season;
import com.jobsity.leonardoinvernizzi.tvseries.model.Show;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleShowActivity extends AppCompatActivity {

    private Spinner spinnerSeasons;
    private EpisodeDAO episodeDAO;
    private List<Season> seasons;
    private ListView listViewEpisodes;
    private MenuItem menu_item_open_star;
    private MenuItem menu_item_closed_star;
    private boolean is_fav;
    private Show show;
    FavouritesDAO favouritesDAO;
    private int fav_changed = RESULT_CANCELED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_show);

        ImageView imageView = findViewById(R.id.imageViewSingleShow);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewGenre = findViewById(R.id.textViewGenre);
        TextView textViewSchedule = findViewById(R.id.textViewSchedule);
        TextView textViewSummary = findViewById(R.id.textViewSummary);
        spinnerSeasons = findViewById(R.id.spinnerSeason);
        listViewEpisodes = findViewById(R.id.listViewEpisodes);
        //ShowDAO showDAO = ServiceGenerator.createService(ShowDAO.class);
        SeasonDAO seasonDAO = ServiceGenerator.createService(SeasonDAO.class);
        episodeDAO = ServiceGenerator.createService(EpisodeDAO.class);
        favouritesDAO = new FavouritesDAO_Impl(Utils.getDatabase(getApplicationContext()));

        Intent intent = getIntent();
        show = (Show) intent.getSerializableExtra(Utils.EXTRA_MESSAGE);

        getSupportActionBar().setTitle(show.getName());

        Call<List<Season>> call = seasonDAO.getSeasons(show.getId());
        call.enqueue(getSeasonCallBack());

        try {
            Picasso.get().load(show.getImage().getOriginal()).into(imageView);
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        textViewTitle.setText(show.getName());
        textViewGenre.setText(TextUtils.join(", ", show.getGenres()));
        textViewSchedule.setText(TextUtils.join(" ● ", new String[]{show.getSchedule().getTime(), TextUtils.join(", ", show.getSchedule().getDays())}));
        textViewSummary.setText(show.getSummary());

        listViewEpisodes.setOnItemClickListener(onEpisodeClickListener());

        //FavouritesDAO favouritesDAO = Utils.getDatabase(getApplicationContext()).favouritesDAO();
        Show ids = favouritesDAO.findById(show.getId());
        is_fav = ids != null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favourites_menu, menu);
        menu_item_open_star = menu.findItem(R.id.menu_fav_open);
        menu_item_closed_star = menu.findItem(R.id.menu_fav_closed);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                setResult(RESULT_OK);
                onBackPressed();
                return true;
            case R.id.menu_fav_open:
                is_fav = true;
                favouritesDAO.insert(show);
                invalidateOptionsMenu();
                return true;
            case R.id.menu_fav_closed:
                is_fav = false;
                favouritesDAO.delete(show);
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu_item_closed_star.setVisible(is_fav);
        menu_item_open_star.setVisible(!is_fav);
        return true;
    }

    private AdapterView.OnItemClickListener onEpisodeClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EpisodeActivity.class);
                Episode episode = (Episode) parent.getItemAtPosition(position);
                intent.putExtra(Utils.EXTRA_MESSAGE, episode);
                startActivity(intent);
            }
        };
    }

    private Callback<List<Season>> getSeasonCallBack() {
        return new Callback<List<Season>>() {
            @Override
            public void onResponse(@NonNull Call<List<Season>> call, @NonNull Response<List<Season>> response) {
                seasons = response.body();
                ArrayAdapter<Season> seasonArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, seasons);
                seasonArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSeasons.setAdapter(seasonArrayAdapter);
                spinnerSeasons.setOnItemSelectedListener(getSpinnerItemSelected());
            }

            @Override
            public void onFailure(@NonNull Call<List<Season>> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        };
    }

    private AdapterView.OnItemSelectedListener getSpinnerItemSelected() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Call<List<Episode>> call = episodeDAO.getEpisodes(seasons.get(position).getId());
                call.enqueue(getEpisodeCallBack());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private Callback<List<Episode>> getEpisodeCallBack() {
        return new Callback<List<Episode>>() {
            @Override
            public void onResponse(@NonNull Call<List<Episode>> call, @NonNull Response<List<Episode>> response) {
                List<Episode> episodes = response.body();
                EpisodeListAdapter episodeListAdapter = new EpisodeListAdapter(getApplicationContext(), episodes);
                listViewEpisodes.setAdapter(episodeListAdapter);
                Utils.setListViewHeight(listViewEpisodes, episodeListAdapter);
                episodeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Episode>> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        };
    }

}