package com.example.snowtamair.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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

    public static HashMap<String,Airport> registeredAirports = new HashMap<>();


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

    //Other Methods and Constructors

    public static Airport getAirport(String ICAO, Context context){
        if (registeredAirports.containsKey(ICAO)){
            return registeredAirports.get(ICAO);
        }else{
            Airport airport = new Airport(ICAO,context);
            registeredAirports.put(ICAO,airport);
            return airport;
        }

    }

    private Airport(String ICAO,Context context) {
        this.ICAO=ICAO;
        JSONObject airport = getAirportFromOACI(ICAO,context);
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
                    JSONArray ICAOList = new JSONArray(json);

                    for(int i = 0; i < ICAOList.length(); i++){
                        JSONObject ICAOJson = ICAOList.getJSONObject((i));
                        Log.d("airportCheck", String.valueOf(ICAOJson.getString("Airport ID")));
                        if(ICAO.equals(ICAOJson.getString("ICAO"))){
                            this.setAirport_ID(Float.parseFloat(ICAOJson.getString("Airport ID")));
                            this.setName(String.valueOf(ICAOJson.getString("Name")));
                            this.setCity(String.valueOf(ICAOJson.getString("City")));
                            this.setCountry(String.valueOf(ICAOJson.getString("Country")));
                            this.setIATA(String.valueOf(ICAOJson.getString("IATA")));
                            this.setICAO(String.valueOf(ICAOJson.getString("ICAO")));
                            this.setLatitude(Float.valueOf(ICAOJson.getString("Latitude")));
                            this.setLongitude(Float.valueOf(ICAOJson.getString("Longitude")));
                            this.setAltitude(Float.valueOf(ICAOJson.getString("Altitude")));
                            this.setTimezone(Float.valueOf(ICAOJson.getString("Timezone")));
                            this.setDST(String.valueOf(ICAOJson.getString("DST")));
                            this.setTz_database_time_zone(String.valueOf(ICAOJson.getString("Tz database time zone")));
                            this.setType(String.valueOf(ICAOJson.getString("Type")));
                            this.setSource(String.valueOf(ICAOJson.getString("Source")));

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
        Log.d("airportCheck", "error when creating airport");
    }

    private JSONObject getAirportFromOACI(String oaci,Context context) {
        try {
            InputStream is = context.getAssets().open("airport.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONArray aiportList = new JSONArray(json);
            for (int i = 0; i < aiportList.length(); i++) {
                JSONObject airport = aiportList.getJSONObject(i);
                if(airport.getString("ICAO").equals(oaci)){
                    return airport;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}