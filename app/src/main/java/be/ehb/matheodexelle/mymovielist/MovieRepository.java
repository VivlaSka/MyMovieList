package be.ehb.matheodexelle.mymovielist;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.room.Update;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private MovieDAO movieDAO;
    private LiveData<List<MovieEntity>> allMovies;
    private LiveData<MovieEntity> singleMovie;
    public MovieRepository(Application application) {
        MovieDatabase database = MovieDatabase.getInstance(application);
        movieDAO = database.movieDAO();
        allMovies = movieDAO.getAll();
        //if(movieDB != null){
        //    movieDB = Room.databaseBuilder(application.getApplicationContext(), MovieDatabase.class, "TestDatabase").build();
        //}//singleton, delete the one before inside the singleton
    }

    public void insert(MovieEntity movie){
        new InsertMovieAsyncTask(movieDAO).execute(movie);
    }
    public void update(MovieEntity movie){
        new UpdateMovieAsyncTask(movieDAO).execute(movie);
    }
    public void delete(MovieEntity movie){
        new DeleteMovieAsyncTask(movieDAO).execute(movie);
    }
    public void findByIMDBID(MovieEntity movie){
        new FindByIMDBIDAsyncTask().execute(movie);
    }
    public void deleteAllMovies(){
        //
    }

    private static class InsertMovieAsyncTask extends AsyncTask<MovieEntity, Void, Void>{
        private MovieDAO movieDao;

        private InsertMovieAsyncTask(MovieDAO movieDao){
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            movieDao.insert(movieEntities[0]);
            return null;
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<MovieEntity, Void, Void>{
        private MovieDAO movieDao;

        private UpdateMovieAsyncTask(MovieDAO movieDao){
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            movieDao.update(movieEntities[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<MovieEntity, Void, Void>{
        private  MovieDAO movieDao;

        private DeleteMovieAsyncTask(MovieDAO movieDao){
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            movieDao.delete(movieEntities[0]);
            return null;
        }
    }
    private static class FindByIMDBIDAsyncTask extends AsyncTask<MovieEntity, Void, Void>{

        private MovieDAO movieDao;

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            movieDao.findByIMDBID(movieEntities[0].getImdbID());
            return null;
        }
    }

    public LiveData<List<MovieEntity>> getAll(){
        return allMovies;
        //return liveData; //WATCH DOCUMENTATION
    }

}
