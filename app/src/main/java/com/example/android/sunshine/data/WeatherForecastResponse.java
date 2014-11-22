package com.example.android.sunshine.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pqin on 21/11/14.
 */
public class WeatherForecastResponse extends ApiResponse {
    City city;
    Forecast[] forecasts;
    int count;

    public WeatherForecastResponse() {}

    public WeatherForecastResponse(JSONObject json) throws JSONException {
        super(json);
        this.city = new City(json.getJSONObject("city"));
        this.count = json.getInt("cnt");

        JSONArray forecastArray = json.getJSONArray("list");
        Forecast[] fs = new Forecast[this.count];
        for(int i=0; i<this.count; ++i) {
            fs[i] = new Forecast(forecastArray.getJSONObject(i));
        }
        this.forecasts = fs;
    }

    public City getCity() {
        return city;
    }

    public Forecast[] getForecasts() {
        return forecasts;
    }

    public int getCount() {
        return count;
    }
}
