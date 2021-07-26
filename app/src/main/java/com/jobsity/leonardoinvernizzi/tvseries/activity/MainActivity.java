package com.jobsity.leonardoinvernizzi.tvseries.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jobsity.leonardoinvernizzi.tvseries.AppDatabase;
import com.jobsity.leonardoinvernizzi.tvseries.R;
import com.jobsity.leonardoinvernizzi.tvseries.ServiceGenerator;
import com.jobsity.leonardoinvernizzi.tvseries.Utils;
import com.jobsity.leonardoinvernizzi.tvseries.adapter.PersonSearchListAdapter;
import com.jobsity.leonardoinvernizzi.tvseries.adapter.ShowListAdapter;
import com.jobsity.leonardoinvernizzi.tvseries.adapter.ShowSearchListAdapter;
import com.jobsity.leonardoinvernizzi.tvseries.dao.FavouritesDAO;
import com.jobsity.leonardoinvernizzi.tvseries.dao.PersonDAO;
import com.jobsity.leonardoinvernizzi.tvseries.dao.ShowDAO;
import com.jobsity.leonardoinvernizzi.tvseries.dao.ShowSearchDAO;
import com.jobsity.leonardoinvernizzi.tvseries.model.Person;
import com.jobsity.leonardoinvernizzi.tvseries.model.PersonSearch;
import com.jobsity.leonardoinvernizzi.tvseries.model.Show;
import com.jobsity.leonardoinvernizzi.tvseries.model.ShowSearch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ShowSearchDAO showSearchDAO;
    private ShowDAO showDAO;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;
    private PersonDAO personDAO;
    private SearchView searchView;
    private List<Show> favourites;
    private MenuItem menuItemPeople;
    private MenuItem menuItemShow;
    private MenuItem menuItemFav;
    private AppDatabase db;
    private List<Show> all_shows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.main_grid_view);
        progressBar = findViewById(R.id.main_progress_bar);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        menuItemPeople = bottomNavigationView.getMenu().findItem(R.id.menu_item_people);
        menuItemShow = bottomNavigationView.getMenu().findItem(R.id.menu_item_show);
        menuItemFav = bottomNavigationView.getMenu().findItem(R.id.menu_item_favourites);

        showSearchDAO = ServiceGenerator.createService(ShowSearchDAO.class);
        showDAO = ServiceGenerator.createService(ShowDAO.class);
        personDAO = ServiceGenerator.createService(PersonDAO.class);

        db = Utils.getDatabase(getApplicationContext());
        //getFavourites();

        all_shows = null;
        gridView.setOnItemClickListener(getOnGridviewItemClickListener());
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavItemSelected());
        menuItemPeople.setVisible(false);


        allShows();
    }

    private void getFavourites() {
        FavouritesDAO favouritesDAO = db.favouritesDAO();
        List<Show> ids = favouritesDAO.getAll();
        List<Show> favourites = new ArrayList<>();
        ShowListAdapter showListAdapter = new ShowListAdapter(getApplicationContext(), favourites);

        for (Show id : ids) {
            Call<Show> call = showDAO.getShow(id.getId());

            call.enqueue(new Callback<Show>() {
                @Override
                public void onResponse(@NonNull Call<Show> call, @NonNull Response<Show> response) {
                    Show show = response.body();
                    favourites.add(show);
                    showListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<Show> call, @NonNull Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            });
        }
        gridView.setAdapter(showListAdapter);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavItemSelected() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_show:
                        if (menuItemPeople.isVisible()) {
                            searchShow(searchView.getQuery().toString());
                        } else {
                            ShowListAdapter showListAdapter = new ShowListAdapter(getApplicationContext(), all_shows);
                            gridView.setAdapter(showListAdapter);
                        }
                        break;
                    case R.id.menu_item_people:
                        searchPeople(searchView.getQuery().toString());
                        break;
                    case R.id.menu_item_favourites:
                        getFavourites();
                        //viewFavourites();
                        break;
                }
                return true;
            }
        };
    }

    private void viewFavourites() {
        ShowListAdapter showListAdapter = new ShowListAdapter(getApplicationContext(), favourites);
        gridView.setAdapter(showListAdapter);
        showListAdapter.notifyDataSetChanged();
    }

    private AdapterView.OnItemClickListener getOnGridviewItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (gridView.getAdapter().getClass() == PersonSearchListAdapter.class) {
                    Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
                    Person person = ((PersonSearch) parent.getItemAtPosition(position)).getPerson();
                    intent.putExtra(Utils.EXTRA_MESSAGE, person);
                    startActivity(intent);
                    //TODO
                } else {
                    Intent intent = new Intent(getApplicationContext(), SingleShowActivity.class);
                    Show show;
                    if (gridView.getAdapter().getClass() == ShowSearchListAdapter.class) {
                        ShowSearch showSearch = (ShowSearch) parent.getItemAtPosition(position);
                        show = showSearch.getShow();
                    } else {
                        show = (Show) parent.getItemAtPosition(position);
                    }

                    intent.putExtra(Utils.EXTRA_MESSAGE, show);
                    startActivity(intent);
                }
            }
        };
    }

    public void allShows() {

        if (all_shows == null) {
            progressBar.setVisibility(View.VISIBLE);

            Call<List<Show>> call = showDAO.getShows(0);

            call.enqueue(new Callback<List<Show>>() {
                @Override
                public void onResponse(@NonNull Call<List<Show>> call, @NonNull Response<List<Show>> response) {
                    progressBar.setVisibility(View.VISIBLE);
                    all_shows = response.body();
                    ShowListAdapter showListAdapter = new ShowListAdapter(getApplicationContext(), all_shows);
                    gridView.setAdapter(showListAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(@NonNull Call<List<Show>> call, @NonNull Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem item = menu.findItem(R.id.search);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setFocusable(true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                executeSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                menuItemPeople.setVisible(true);
                menuItemFav.setVisible(false);
                searchView.requestFocus();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                menuItemPeople.setVisible(false);
                menuItemFav.setVisible(true);
                searchView.clearFocus();
                ShowListAdapter showListAdapter = new ShowListAdapter(getApplicationContext(), all_shows);
                gridView.setAdapter(showListAdapter);
                return true;
            }
        });

        return true;
    }

    private void executeSearch(String query) {
        progressBar.setVisibility(View.VISIBLE);
        switch (getBottomNavSelectedItem()) {
            case R.id.menu_item_show:
                searchShow(query);
                break;
            case R.id.menu_item_people:
                searchPeople(query);
                break;
        }
    }

    private int getBottomNavSelectedItem() {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.isChecked()) {
                return menuItem.getItemId();
            }
        }
        return 0;
    }

    private void searchShow(String query) {
        Call<List<ShowSearch>> call = showSearchDAO.getShowsSearch(query);

        call.enqueue(new Callback<List<ShowSearch>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShowSearch>> call, @NonNull Response<List<ShowSearch>> response) {
                List<ShowSearch> showSearchList = response.body();
                ShowSearchListAdapter showSearchListAdapter = new ShowSearchListAdapter(getApplicationContext(), showSearchList);
                gridView.setAdapter(showSearchListAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<List<ShowSearch>> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void searchPeople(String query) {
        Call<List<PersonSearch>> call = personDAO.getPersonSearch(query);

        call.enqueue(new Callback<List<PersonSearch>>() {
            @Override
            public void onResponse(@NonNull Call<List<PersonSearch>> call, @NonNull Response<List<PersonSearch>> response) {
                List<PersonSearch> people = response.body();
                PersonSearchListAdapter personSearchListAdapter = new PersonSearchListAdapter(getApplicationContext(), people);
                gridView.setAdapter(personSearchListAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<List<PersonSearch>> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        ///if (menuItemFav.isChecked()){
            getFavourites();
        //}
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        if (getBottomNavSelectedItem() == R.id.menu_item_favourites) {
            getFavourites();
        }
        super.onResume();
    }
}