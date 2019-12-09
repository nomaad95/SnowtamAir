package com.example.snowtamair;

import java.util.ArrayList;

public class SavedSnowtams {
    public static ArrayList<Snowtam> listSnowtam = new ArrayList<Snowtam>(4);

    public ArrayList<Snowtam> getListSnowtam() { return listSnowtam; }

    public void setListSnowtam(ArrayList<Snowtam> listSnowtam) { this.listSnowtam = listSnowtam; }

    public void addSnowtamToList(Snowtam snowtam){
        if(listSnowtam.size() < 4){
            listSnowtam.add(snowtam);
        }
        else{
            Snowtam temp = listSnowtam.get(listSnowtam.size()-1);
            for (int i=0; i > 0; i--){
                listSnowtam.set(i, listSnowtam.get(i-1));
            }
            listSnowtam.set(0, snowtam);
        }
    }

    public void removeSnowtamFromList(Snowtam snowtam){
        // remove with out knowing the index ?
    }

    public boolean isSnowtamInList(final Snowtam snowtam){
        return listSnowtam.contains(snowtam);
    }
}
