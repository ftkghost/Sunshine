package com.example.android.sunshine.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.sunshine.data.Forecast;
import com.example.android.sunshine.data.WeatherForecastResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pqin on 19/11/14.
 */
public class WeatherForecastFragment extends Fragment {

    ListView mWeatherForecastListView;
    LayoutInflater mInflater;
    public WeatherForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mWeatherForecastListView = (ListView)rootView.findViewById(R.id.listview_forecast);

        mWeatherForecastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = (String)mWeatherForecastListView.getAdapter().getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                String weather = (String)parent.getAdapter().getItem(position);
                intent.putExtra("weather", weather);
                startActivity(intent);
            }
        });

        String[] weatherForecast = new String[] {
                "Today - Sunny - 28 / 15",
                "Tomorrow - Cloudy - 21 / 12",
                "Weds - Cloudy - 22 / 11",
                "Thurs - Rain - 20 / 9",
                "Fri - Rain - 21 / 9",
                "Sat - Cloudy - 23 / 14",
                "Sun - Sunny - 25 / 16",
                "Mon - Sunny - 28 / 15",
                "Tues - Cloudy - 21 / 12",
                "Weds - Cloudy - 22 / 11",
                "Thurs - Rain - 20 / 9",
                "Fri - Rain - 21 / 9",
                "Sat - Cloudy - 23 / 14",
                "Sun - Sunny - 25 / 16"
        };
        setListViewDataSource(weatherForecast);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    void setListViewDataSource(String[] forecast) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, forecast);
        mWeatherForecastListView.setAdapter(arrayAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId() == R.id.action_refresh) {
           updateWeather();
           return true;
       }
       else {
            return super.onOptionsItemSelected(item);
       }
    }

    private void updateWeather() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = sharedPrefs.getString(SettingsActivity.KEY_PREF_LOCATION, getString(R.string.pref_location_default));
        WeatherApiTask.weatherForecastApiTaskWithLocation(new WeatherApiTask.IWeatherForecastResultObserver() {
            @Override
            public void onForecastResultReady(WeatherForecastResponse response) {
                if(response != null) {
                    String[] forecasts = toForecastString(response);
                    setListViewDataSource(forecasts);
                }
            }

            private String[] toForecastString(WeatherForecastResponse response) {
                int count = response.getCount();
                String[] forecasts = new String[count];
                for(int i=0; i<count; ++i) {
                    Forecast forecastObj = response.getForecasts()[i];
                    Date date = new Date(forecastObj.getTime() * 1000);
                    SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
                    String day = format.format(date).toString();
                    String weather = forecastObj.getWeathers()[0].getDescription();
                    float max = forecastObj.getTemperature().getMax();
                    float min = forecastObj.getTemperature().getMin();
                    forecasts[i] = String.format("%s - %s - %.1f / %.1f", day, weather, max, min);
                }
                return forecasts;
            }
        }, location);
    }
}