package com.example.snowtamair;

import java.util.ArrayList;

public class SavedAirports {
    private static SavedAirports instance;
    private static ArrayList<Airport> listAirports = new ArrayList<Airport>(4);
    private SavedAirports() {}

    public static SavedAirports getInstance() {
        if (instance == null) {
            instance = new SavedAirports();
        }
        return instance;
    }

    public ArrayList<Airport> getListAirport() { return listAirports; }

    public void setListAirport(ArrayList<Airport> listAirport) { this.listAirports = listAirport; }

    public boolean checkAirport(Airport airport){
        for(int i = 0; i < listAirports.size(); i++){
            if(listAirports.get(i).getICAO() == airport.getICAO()){
                return true;
            }
        }

        return false;
    }

    public void addAirportToList(Airport airport){
        if(!checkAirport(airport)){
            if(listAirports.size() < 4){
                listAirports.add(airport);
            }
            else{
                Airport temp = listAirports.get(listAirports.size()-1);
                for (int i=0; i > 0; i--){
                    listAirports.set(i, listAirports.get(i-1));
                }
                listAirports.set(0, airport);
            }
        }


    }

    public void removeAirportFromList(Airport airport){
        // remove with out knowing the index ?
    }

    public boolean isAirportInList(final Airport airport){
        return listAirports.contains(airport);
    }
}
