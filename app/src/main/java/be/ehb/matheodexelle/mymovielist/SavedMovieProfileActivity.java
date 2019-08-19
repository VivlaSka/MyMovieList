package be.ehb.matheodexelle.mymovielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SavedMovieProfileActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewGenre;
    private TextView textViewReleaseDate;
    private TextView textViewLanguage;
    private TextView textViewYear;
    private TextView textViewWriters;
    private TextView textViewDirector;
    private TextView textViewAwards;
    private TextView textViewCountry;
    private Button buttonAddMovie;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movie_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent bundleIntent = getIntent();
        final MovieEntity movie = (MovieEntity) bundleIntent.getSerializableExtra("selected_movie");

        textViewCountry = findViewById(R.id.text_view_country);
        textViewAwards = findViewById(R.id.text_view_awards);
        textViewYear = findViewById(R.id.text_view_year);
        textViewWriters = findViewById(R.id.text_view_writers);
        textViewDirector = findViewById(R.id.text_view_director);
        buttonAddMovie = findViewById(R.id.button_delete_movie);
        textViewTitle = findViewById(R.id.text_view_title);
        textViewDescription = findViewById(R.id.text_view_description);
        textViewGenre = findViewById(R.id.text_view_genre);
        textViewReleaseDate = findViewById(R.id.text_view__release_date);
        textViewLanguage = findViewById(R.id.text_view_language );

        textViewCountry.setText(movie.getCountry());
        textViewAwards.setText(movie.getAwards());
        textViewYear.setText(movie.getYear());
        textViewWriters.setText(movie.getWriters());
        textViewDirector.setText(movie.getDirector());
        textViewTitle.setText(movie.getTitle());
        textViewDescription.setText(movie.getPlot());
        textViewGenre.setText(movie.getGenre());
        textViewReleaseDate.setText(movie.getReleased());
        textViewLanguage.setText(movie.getLanguage());
        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieViewModel = ViewModelProviders.of(SavedMovieProfileActivity.this).get(MovieViewModel.class);
                movieViewModel.delete(movie);
                Toast.makeText(SavedMovieProfileActivity.this, "Movie removed from your list !", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
