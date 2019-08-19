package be.ehb.matheodexelle.mymovielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.provider.FontsContract.Columns.RESULT_CODE_OK;

public class SearchMovieActivity extends AppCompatActivity {

    public static final String TAG = SearchMovieActivity.class.getSimpleName();

    private EditText editTextMovieTitle;
    private Button buttonStartSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editTextMovieTitle = findViewById(R.id.edit_text_search_title);
        buttonStartSearch = findViewById(R.id.button_start_search);

        buttonStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextMovieTitle.getText().toString().trim().isEmpty()){
                    //getString(R.string.app_name);
                    Toast.makeText(SearchMovieActivity.this,getString(R.string.toast_not_empty), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SearchMovieActivity.this,getString(R.string.toast_processing), Toast.LENGTH_SHORT).show();
                    MoviesServiceResult moviesServiceResult = new MoviesServiceResult(null);
                    Intent intent =  new Intent(SearchMovieActivity.this, MoviesService.class);
                    intent.putExtra("MoviesServiceResult", moviesServiceResult);
                    intent.putExtra("movieTitle", editTextMovieTitle.getText().toString());
                    startService(intent);
                }
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

    public class MoviesServiceResult extends ResultReceiver{


        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MoviesServiceResult(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            ArrayList<MovieEntity> movies = new ArrayList<>();
            super.onReceiveResult(resultCode, resultData);
            //resultData.get("result");
            if(resultCode == RESULT_CODE_OK){
                //String test = resultData.getString("result");
                Intent intent = new Intent(SearchMovieActivity.this, ResultSearchActivity.class);
                JSONObject tempJSON = null;

                try {
                    tempJSON = new JSONObject(resultData.getString("result"));
                    //JSONObject jsonObject = tempJSON.getJSONObject("Search");
                    JSONArray jsonArray = tempJSON.getJSONArray("Search");
                    for(int i = 0; i < jsonArray.length(); i++){
                        MovieEntity tempMovie = new MovieEntity(jsonArray.getJSONObject(i).getString("Title"), jsonArray.getJSONObject(i).getString("Poster"));
                        tempMovie.setImdbID(jsonArray.getJSONObject(i).getString("imdbID"));
                        movies.add(tempMovie);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                intent.putExtra("search_results", movies);

                startActivity(intent);
            }
        }

    }
}
