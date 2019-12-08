package com.example.snowtamair;

import java.util.ArrayList;

public class SavedAirports {
    private static ArrayList<AirportRequest> listAirportRequest = new ArrayList<AirportRequest>();

    public ArrayList<AirportRequest> getListAirportRequest() { return listAirportRequest; }

    public void setListAirportRequest(ArrayList<AirportRequest> listAirportRequest) { this.listAirportRequest = listAirportRequest; }

    public void addAiportToList(AirportRequest airportRequest){
        listAirportRequest.add(airportRequest);
    }

    public void removeAirportFromList(AirportRequest airportRequest){
        // remove with out knowing the index ?
    }

    public boolean isAirportInList(final AirportRequest airportRequest){
        return listAirportRequest.contains(airportRequest);
    }
}
