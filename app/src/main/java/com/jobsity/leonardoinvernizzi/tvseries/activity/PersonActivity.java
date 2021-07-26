package com.jobsity.leonardoinvernizzi.tvseries.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jobsity.leonardoinvernizzi.tvseries.R;
import com.jobsity.leonardoinvernizzi.tvseries.ServiceGenerator;
import com.jobsity.leonardoinvernizzi.tvseries.Utils;
import com.jobsity.leonardoinvernizzi.tvseries.adapter.PersonShowAdapter;
import com.jobsity.leonardoinvernizzi.tvseries.dao.PersonDAO;
import com.jobsity.leonardoinvernizzi.tvseries.model.CastCredits;
import com.jobsity.leonardoinvernizzi.tvseries.model.Person;
import com.jobsity.leonardoinvernizzi.tvseries.model.Show;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonActivity extends AppCompatActivity {

    private ListView listViewPersonShows;
    private PersonDAO personDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ImageView imageView = findViewById(R.id.imageViewPerson);
        TextView textViewPersonName = findViewById(R.id.textViewPersonName);
        listViewPersonShows = findViewById(R.id.listViewPersonShows);
        personDAO = ServiceGenerator.createService(PersonDAO.class);

        Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra(Utils.EXTRA_MESSAGE);

        Call<List<CastCredits>> call = personDAO.getPersonShows(person.getId());
        call.enqueue(getPersonShows());

        try {
            Picasso.get().load(person.getImage().getOriginal()).into(imageView);
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
        textViewPersonName.setText(person.getName());

        listViewPersonShows.setOnItemClickListener(onShowClickListener());
    }

    private AdapterView.OnItemClickListener onShowClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SingleShowActivity.class);
                Show show = ((CastCredits) parent.getItemAtPosition(position)).get_embedded().getShow();
                intent.putExtra(Utils.EXTRA_MESSAGE, show);
                startActivity(intent);
            }
        };
    }

    private Callback<List<CastCredits>> getPersonShows() {
        return new Callback<List<CastCredits>>() {
            @Override
            public void onResponse(Call<List<CastCredits>> call, Response<List<CastCredits>> response) {
                List<CastCredits> castCredits = response.body();
                PersonShowAdapter personShowAdapter = new PersonShowAdapter(getApplicationContext(), castCredits);
                listViewPersonShows.setAdapter(personShowAdapter);
                Utils.setListViewHeight(listViewPersonShows, personShowAdapter);
                if (personShowAdapter.getCount() != 0){
                    ImageView imageView = findViewById(R.id.imageViewNotFound);
                    TextView textView = findViewById(R.id.textNotFound);
                    imageView.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                } else {
                    listViewPersonShows.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<CastCredits>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}