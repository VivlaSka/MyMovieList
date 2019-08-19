package be.ehb.matheodexelle.mymovielist;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.Result;

import static android.provider.FontsContract.Columns.RESULT_CODE_OK;

public class MoviesService extends IntentService {

    private static final String TAG = "MoviesService";
    private PowerManager.WakeLock wakeLock;
    private String movieTitle;
    private ResultReceiver moviesServiceResult;
    private JSONObject jsonMovie;


    public MoviesService(){
        super("MoviesService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        PowerManager powerManager =(PowerManager) getSystemService(POWER_SERVICE);
        wakeLock =  powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ExampleApp:WakeLock");
        wakeLock.acquire(600000);
        Log.d(TAG, "onCreate: Wakelock aquired");

        //make notification !
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        wakeLock.release();
        Log.d(TAG, "onDestroy: Wakelock released");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        moviesServiceResult = intent.getParcelableExtra("MoviesServiceResult");
        String line = "";
            try {
                String userInput = intent.getStringExtra("movieTitle");
                URL url = new URL("http://www.omdbapi.com/?s=" + userInput + "&apikey=70f9559d&page=1");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream is = connection.getInputStream();
                BufferedReader bfReader = new BufferedReader(new InputStreamReader(is));

                StringBuilder sb = new StringBuilder();

                while ((line = bfReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                jsonMovie = new JSONObject(sb.toString());
                handleWithDraw(moviesServiceResult);




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }
    private void handleWithDraw(ResultReceiver resultReceiver){
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", jsonMovie.toString());
        moviesServiceResult.send(RESULT_CODE_OK, bundle);
    }
}
