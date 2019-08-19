package be.ehb.matheodexelle.mymovielist;

import androidx.lifecycle.ViewModelProviders;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
   @Test
   public void is_it_MovieEntity(){
        MovieEntity singleMovie = new MovieEntity("");
       singleMovie.setTitle("TEST");
       singleMovie.setActors("TEST");
       singleMovie.setWriters("TEST");
       singleMovie.setDescription("TEST");
       singleMovie.setYear("TEST");
       singleMovie.setRated("TEST");
       singleMovie.setReleased("TEST");
       singleMovie.setRuntime("TEST");
       singleMovie.setGenre("TEST");
       singleMovie.setDirector("TEST");
       singleMovie.setPlot("TEST");
       singleMovie.setLanguage("TEST");
       singleMovie.setCountry("TEST");
       singleMovie.setAwards("TEST");
       singleMovie.setImdbID("TEST");
       singleMovie.setUrlPoster("TEST");
       assertEquals(singleMovie.getTitle(),"TEST");
   }
}