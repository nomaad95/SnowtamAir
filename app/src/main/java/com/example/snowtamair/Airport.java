package com.example.snowtamair;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Airport {
    private float Airport_ID;
    private String Name;
    private String City;
    private String Country;
    private String IATA;
    private String ICAO;
    private float Latitude;
    private float Longitude;
    private float Altitude;
    private float Timezone;
    private String DST;
    private String Tz_database_time_zone;
    private String Type;
    private String Source;


    // Getter Methods

    public float getAirport_ID() {
        return Airport_ID;
    }

    public String getName() {
        return Name;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getIATA() {
        return IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public float getLatitude() {
        return Latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public float getAltitude() {
        return Altitude;
    }

    public float getTimezone() {
        return Timezone;
    }

    public String getDST() {
        return DST;
    }

    public String getTz_database_time_zone() {
        return Tz_database_time_zone;
    }

    public String getType() {
        return Type;
    }

    public String getSource() {
        return Source;
    }

    // Setter Methods

    public void setAirport_ID(float Airport_ID) {
        this.Airport_ID = Airport_ID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public void setLatitude(float Latitude) {
        this.Latitude = Latitude;
    }

    public void setLongitude(float Longitude) {
        this.Longitude = Longitude;
    }

    public void setAltitude(float Altitude) {
        this.Altitude = Altitude;
    }

    public void setTimezone(float Timezone) {
        this.Timezone = Timezone;
    }

    public void setDST(String DST) {
        this.DST = DST;
    }

    public void setTz_database_time_zone(String Tz_database_time_zone) {
        this.Tz_database_time_zone = Tz_database_time_zone;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public Airport(String oaci, Context context) {


        try {
            Log.d("airportCheck", "ok1");
            InputStream is = context.getAssets().open("airport.json");
            int size = 0;
            try {
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");

                try {
                    JSONArray oaciList = new JSONArray(json);

                    for(int i = 0; i < oaciList.length(); i++){
                        JSONObject oaciJson = oaciList.getJSONObject((i));
                        Log.d("airportCheck", String.valueOf(oaciJson.getString("Airport ID")));
                        if(oaci.equals(oaciJson.getString("ICAO"))){
                            this.setAirport_ID(Float.parseFloat(oaciJson.getString("Airport ID")));
                            this.setName(String.valueOf(oaciJson.getString("Name")));
                            this.setCity(String.valueOf(oaciJson.getString("City")));
                            this.setCountry(String.valueOf(oaciJson.getString("Country")));
                            this.setIATA(String.valueOf(oaciJson.getString("IATA")));
                            this.setICAO(String.valueOf(oaciJson.getString("ICAO")));
                            this.setLatitude(Float.valueOf(oaciJson.getString("Latitude")));
                            this.setLongitude(Float.valueOf(oaciJson.getString("Longitude")));
                            this.setAltitude(Float.valueOf(oaciJson.getString("Altitude")));
                            this.setTimezone(Float.valueOf(oaciJson.getString("Timezone")));
                            this.setDST(String.valueOf(oaciJson.getString("DST")));
                            this.setTz_database_time_zone(String.valueOf(oaciJson.getString("Tz database time zone")));
                            this.setType(String.valueOf(oaciJson.getString("Type")));
                            this.setSource(String.valueOf(oaciJson.getString("Source")));

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}