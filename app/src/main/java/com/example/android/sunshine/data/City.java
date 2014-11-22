package com.example.android.sunshine.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pqin on 19/11/14.
 */
public class City {
    String id;
    String name;
    String country;
    double longitude;
    double latitude;
    int population;

    public City() {
    }

    public City(JSONObject json) throws JSONException {
        this.id = json.getString("id");
        this.name = json.getString("name");
        this.country = json.getString("country");
        this.population = json.getInt("population");
        JSONObject coord = json.getJSONObject("coord");
        longitude = coord.getDouble("lon");
        latitude = coord.getDouble("lat");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
