package com.example.android.sunshine.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.sunshine.data.WeatherForecastResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pqin on 19/11/14.
 */
public class WeatherApiTask extends AsyncTask<String, Void, WeatherForecastResponse> {

    public interface IWeatherForecastResultObserver {
        void onForecastResultReady(WeatherForecastResponse response);
    }

    public static WeatherApiTask weatherForecastApiTaskWithLocation(IWeatherForecastResultObserver observer, String location) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.authority("api.openweathermap.org");
        builder.path("/data/2.5/forecast/daily");
        builder.appendQueryParameter("q", location);
        builder.appendQueryParameter("cnt", "7");           // 7 days
        builder.appendQueryParameter("units", "metric");
        builder.appendQueryParameter("mode", "json");
        String apiURL = builder.build().toString();
        WeatherApiTask task = new WeatherApiTask(observer);
        task.execute(apiURL);
        return task;
    }

    private final IWeatherForecastResultObserver resultObserver;

    public WeatherApiTask(IWeatherForecastResultObserver observer) {
        resultObserver = observer;
    }

    @Override
    protected WeatherForecastResponse doInBackground(String... urls) {
        String weatherApiURL = urls[0];
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        WeatherForecastResponse apiResult = null;
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL(weatherApiURL);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() > 0) {
                forecastJsonStr = buffer.toString();
                JSONObject responseObj = new JSONObject(forecastJsonStr);
                apiResult = new WeatherForecastResponse(responseObj);
            }
        } catch (IOException e) {
            Log.e("WeatherApiTask", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
        } catch (JSONException e) {
            e.printStackTrace();
            apiResult = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("WeatherApiTask", "Error closing stream", e);
                }
            }
        }
        return apiResult;
    }

    // Post execute on UI thread.
    @Override
    protected void onPostExecute(WeatherForecastResponse apiResult) {
        if(resultObserver != null) {
            resultObserver.onForecastResultReady(apiResult);
        }
    }
}
