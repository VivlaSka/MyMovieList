package be.ehb.matheodexelle.mymovielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.provider.FontsContract.Columns.RESULT_CODE_OK;

public class ResultSearchActivity extends AppCompatActivity {

    private MovieViewModel allMovies;
    private ArrayList<MovieEntity> allMoviesInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent bundleIntent = getIntent();
        allMoviesInformation = (ArrayList<MovieEntity>) bundleIntent.getSerializableExtra("search_results");


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ResultSearchAdapter adapter = new ResultSearchAdapter();
        recyclerView.setAdapter(adapter);

        allMovies = ViewModelProviders.of(this).get(MovieViewModel.class);
        allMovies.getAll().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                adapter.setMovies(allMoviesInformation);
            }
        });


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ResultSearchActivity.this, MovieProfileActivity.class);
                        Intent bundleIntent = getIntent();

                        Intent serviceIntent = new Intent(ResultSearchActivity.this, SingleMovieService.class);
                        SingleMovieServiceResult singleMovieServiceResult = new SingleMovieServiceResult(null);
                        serviceIntent.putExtra("SingleMovieServiceResult", singleMovieServiceResult);
                        serviceIntent.putExtra("imdbid", allMoviesInformation.get(position).getImdbID());
                        startService(serviceIntent);

                        //intent.putExtra("selected_movie", allMoviesInformation.get(position));
                        ////intent.putExtra() //info single movie doorgeven
                        //startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(ResultSearchActivity.this, getString(R.string.toast_egg), Toast.LENGTH_SHORT).show();
                    }
                })
        );



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public class SingleMovieServiceResult extends ResultReceiver {


        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public SingleMovieServiceResult(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            ArrayList<MovieEntity> movies = new ArrayList<>();
            MovieEntity singleMovie = new MovieEntity("");
            super.onReceiveResult(resultCode, resultData);
            //resultData.get("result");
            if(resultCode == RESULT_CODE_OK){
                //String test = resultData.getString("result");
                Intent intent = new Intent(ResultSearchActivity.this, MovieProfileActivity.class);
                JSONObject tempJSON = null;

                try {
                    tempJSON = new JSONObject(resultData.getString("result"));
                    singleMovie.setTitle(tempJSON.get("Title").toString());
                    singleMovie.setActors(tempJSON.get("Actors").toString());
                    singleMovie.setWriters(tempJSON.get("Writer").toString());
                    singleMovie.setDescription(tempJSON.get("Plot").toString());
                    singleMovie.setYear(tempJSON.get("Year").toString());
                    singleMovie.setRated(tempJSON.get("Rated").toString());
                    singleMovie.setReleased(tempJSON.get("Released").toString());
                    singleMovie.setRuntime(tempJSON.get("Runtime").toString());
                    singleMovie.setGenre(tempJSON.get("Genre").toString());
                    singleMovie.setDirector(tempJSON.get("Director").toString());
                    singleMovie.setPlot(tempJSON.get("Plot").toString());
                    singleMovie.setLanguage(tempJSON.get("Language").toString());
                    singleMovie.setCountry(tempJSON.get("Country").toString());
                    singleMovie.setAwards(tempJSON.get("Awards").toString());
                    singleMovie.setImdbID(tempJSON.get("imdbID").toString());
                    singleMovie.setUrlPoster(tempJSON.get("Poster").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                intent.putExtra("selected_movie", singleMovie);
                startActivity(intent);
            }
        }

    }
}
