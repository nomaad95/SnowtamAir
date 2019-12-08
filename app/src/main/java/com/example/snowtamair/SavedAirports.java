package com.example.snowtamair;

import java.util.ArrayList;

public class SavedAirports {
    public static ArrayList<Airport> listAirport = new ArrayList<Airport>(4);

    public ArrayList<Airport> getListAirport() { return listAirport; }

    public void setListAirport(ArrayList<Airport> listAirport) { this.listAirport = listAirport; }

    public void addAiportToList(Airport airport){
        if(listAirport.size() < 4){
            listAirport.add(airport);
        }

        else{
            Airport temp = listAirport.get(listAirport.size()-1);
            for (int i=0; i > 0; i--){
                listAirport.set(i, listAirport.get(i-1));
            }

            listAirport.set(0, airport);
        }

    }

    public void removeAirportFromList(Airport Airport){
        // remove with out knowing the index ?
    }

    public boolean isAirportInList(final Airport Airport){
        return listAirport.contains(Airport);
    }
}
