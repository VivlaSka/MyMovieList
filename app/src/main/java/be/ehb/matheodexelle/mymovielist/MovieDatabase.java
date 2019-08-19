package be.ehb.matheodexelle.mymovielist;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDAO movieDAO();

    private static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movie_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>{

        private MovieDAO movieDAO;

        private PopulateDBAsyncTask(MovieDatabase movDB){
            movieDAO = movDB.movieDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //movieDAO.insert(new MovieEntity("TestPopulateDB"));
            //movieDAO.insert(new MovieEntity("TestPopulateDB2"));
            //movieDAO.insert(new MovieEntity("TestPopulateDB3"));
            //movieDAO.insert(new MovieEntity("TestPopulateDB4"));
            return null;
        }
    }
}
