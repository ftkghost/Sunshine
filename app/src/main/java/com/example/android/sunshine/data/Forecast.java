package com.example.android.sunshine.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pqin on 19/11/14.
 */
public class Forecast {
    long time;
    double pressure;
    int humidity;
    double windSpeed;
    int windDegree;
    int clouds;
    Temperature temperature;
    Weather[] weathers;

    public Forecast() {
    }

    public Forecast(JSONObject json) throws JSONException {
        this.time = json.getLong("dt");
        this.pressure = json.getDouble("pressure");
        this.humidity = json.getInt("humidity");
        this.windSpeed = json.getDouble("speed");
        this.windDegree = json.getInt("deg");
        this.clouds = json.getInt("clouds");
        this.temperature = new Temperature(json.getJSONObject("temp"));
        JSONArray weatherArray = json.getJSONArray("weather");
        int len = weatherArray.length();
        Weather[] ws = new Weather[len];
        for(int i=0; i<len; ++i) {
            ws[i] = new Weather(weatherArray.getJSONObject(i));
        }
        this.weathers = ws;

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Weather[] getWeathers() {
        return weathers;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(int windDegree) {
        this.windDegree = windDegree;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }
}
