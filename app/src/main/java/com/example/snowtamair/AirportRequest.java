package com.example.snowtamair;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class AirportRequest {
    private String data;
    private ArrayList <Track> tracks;
    private String codeOACI;
    private String nameAirport;

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<Track> getTracks() { return tracks; }
    public void setTracks(ArrayList<Track> tracks) { this.tracks = tracks; }

    public String getCodeOACI() { return codeOACI; }
    public void setCodeOACI(String codeOACI) { this.codeOACI = codeOACI; }

    public String getNameAirport() { return nameAirport; }
    public void setNameAirport(String nameAirport) { this.nameAirport = nameAirport; }

    public AirportRequest(String OACI, Context context) {
        this.codeOACI = OACI;
        String url = "https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/states/notams/notams-realtime-list?" +
                "api_key=f614b0f0-1674-11ea-ab2e-bf2aa2669a71&format=json&criticality=&locations=" + OACI;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        data= response.toString();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        RequestSingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }

    public AirportRequest(ArrayList <Track> tracks){
        this.tracks=tracks;
    }
}
