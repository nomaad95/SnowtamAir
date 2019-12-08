package com.example.snowtamair;

import java.util.ArrayList;

public class SavedAirports {
    private ArrayList<Airport> listAirport = new ArrayList<Airport>();

    public ArrayList<Airport> getListAirport() { return listAirport; }

    public void setListAirport(ArrayList<Airport> listAirport) { this.listAirport = listAirport; }

    public void addAiportToList(Airport airport){
        listAirport.add(airport);
    }

    public void removeAirportFromList(Airport airport){
        // remove with out knowing the index ?
    }

    public boolean isAirportInList(final Airport airport){
        return listAirport.contains(airport);
    }
}
