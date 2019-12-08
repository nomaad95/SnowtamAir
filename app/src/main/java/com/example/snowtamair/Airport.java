package com.example.snowtamair;

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
}