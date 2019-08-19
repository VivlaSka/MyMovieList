package be.ehb.matheodexelle.mymovielist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private MutableLiveData<MovieEntity> selectedItem;
    private LiveData<List<MovieEntity>> allMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        allMovies = movieRepository.getAll();

    }

    public void insert(MovieEntity movie){
        movieRepository.insert(movie);
    }
    public void findByIMDBID(MovieEntity movie){movieRepository.findByIMDBID(movie);}
    public void update(MovieEntity movie){
        movieRepository.update(movie);
    }
    public void delete(MovieEntity movie){
        movieRepository.delete(movie);
    }

    public LiveData<List<MovieEntity>> getAll(){
        return allMovies;
    }

}
