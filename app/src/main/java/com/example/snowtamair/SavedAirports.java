package com.example.snowtamair;

import java.util.ArrayList;

public class SavedAirports {
    private static ArrayList<Airport> listAirport = new ArrayList<Airport>(4);

    public ArrayList<Airport> getListAirport() { return listAirport; }

    public void setListAirport(ArrayList<Airport> listAirport) { this.listAirport = listAirport; }

    public void addAiportToList(Airport Airport){
        if(listAirport.size() < 4){
            listAirport.add(Airport);
        }

        else{

        }

    }

    public void removeAirportFromList(Airport Airport){
        // remove with out knowing the index ?
    }

    public boolean isAirportInList(final Airport Airport){
        return listAirport.contains(Airport);
    }
}
