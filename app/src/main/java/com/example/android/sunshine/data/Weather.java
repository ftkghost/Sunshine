package com.example.android.sunshine.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pqin on 19/11/14.
 */
public class Weather {
    long id;
    String main;
    String description;
    String icon;

    public Weather() {
    }

    public Weather(JSONObject json) throws JSONException {
        this.id = json.getLong("id");
        this.main = json.getString("main");
        this.description = json.getString("description");
        this.icon = String.format("http://openweathermap.org/img/w/%s.png", json.getString("icon"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
