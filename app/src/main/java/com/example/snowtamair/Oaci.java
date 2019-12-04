package com.example.snowtamair;

public class Oaci {
    private String icao;
    private String iata;
    private String name;
    private String city;
    private String state;
    private String country;
    private float elevation;
    private float lat;
    private float lon;
    private String tz;


    // Getter Methods

    public String getIcao() {
        return icao;
    }

    public String getIata() {
        return iata;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public float getElevation() {
        return elevation;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getTz() {
        return tz;
    }

    // Setter Methods

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }
}
