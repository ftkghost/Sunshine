package com.example.android.sunshine.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pqin on 19/11/14.
 */
public class Temperature {
    float day;
    float night;
    float morning;
    float evening;
    float min;
    float max;

    public Temperature() {
    }

    public Temperature(JSONObject json) throws JSONException {
        this.day = (float)json.getDouble("day");
        this.night = (float)json.getDouble("night");
        this.morning = (float)json.getDouble("morn");
        this.evening = (float)json.getDouble("eve");
        this.min = (float)json.getDouble("min");
        this.max = (float)json.getDouble("max");
    }

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getMorning() {
        return morning;
    }

    public void setMorning(float morning) {
        this.morning = morning;
    }

    public float getEvening() {
        return evening;
    }

    public void setEvening(float evening) {
        this.evening = evening;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
