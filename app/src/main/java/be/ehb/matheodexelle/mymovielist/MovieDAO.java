package be.ehb.matheodexelle.mymovielist;

import android.graphics.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MovieDAO {

    @Query("SELECT * FROM movie_entity")
    LiveData<List<MovieEntity>> getAll();

    @Query("SELECT * FROM movie_entity WHERE imdbID LIKE :imdbID LIMIT 1")
    MovieEntity findByIMDBID(String imdbID);

    @Insert
    void insert(MovieEntity movie);

    @Update
    void update(MovieEntity movie);

    @Delete
    void delete(MovieEntity movie);



}
