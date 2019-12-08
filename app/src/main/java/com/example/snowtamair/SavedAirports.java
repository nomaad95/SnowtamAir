package com.example.snowtamair;

import java.util.ArrayList;

public class SavedAirports {
    private static ArrayList<Airport> listAirport = new ArrayList<Airport>();

    public ArrayList<Airport> getListAirport() { return listAirport; }

    public void setListAirport(ArrayList<Airport> listAirport) { this.listAirport = listAirport; }

    public void addAiportToList(Airport Airport){
        listAirport.add(Airport);
    }

    public void removeAirportFromList(Airport Airport){
        // remove with out knowing the index ?
    }

    public boolean isAirportInList(final Airport Airport){
        return listAirport.contains(Airport);
    }
}
