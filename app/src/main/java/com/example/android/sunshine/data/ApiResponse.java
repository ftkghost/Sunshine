package com.example.android.sunshine.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pqin on 21/11/14.
 */
public class ApiResponse {
    String code;
    String message;

    public ApiResponse() {
    }

    public ApiResponse(JSONObject json) throws JSONException {
        this.code = json.getString("cod");
        this.message = ((Double)json.getDouble("message")).toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
