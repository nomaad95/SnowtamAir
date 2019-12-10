package com.example.snowtamair.model;

import android.content.Context;
import android.util.Log;

import com.example.snowtamair.model.Airport;
import com.example.snowtamair.model.Runway;

import java.util.ArrayList;
import java.util.HashMap;

public class Snowtam {
    private Airport airport;
    private ArrayList<Runway> runways = new ArrayList<>();

    public void setRunways(ArrayList<Runway> runways) { this.runways = runways; }
    public int getRunwaysSize(){
        return runways.size();
    }


    public Snowtam(Airport airport, ArrayList<Runway> runway) {
        this.airport = airport;
        this.runways = runways;
    }

    public Airport getAirport() {
        return airport;
    }



    public ArrayList<Runway> getRunway() {
        return runways;
    }
    public Runway getRunwayFromPosition(int position) {
        return runways.get(position);
    }

    public Snowtam(String codedSnowtam, Context context) {
        String[] parsedSnowtam = codedSnowtam.split("\n");
        String ICAO = "";
        HashMap<String,String> bloc = new HashMap<String,String>();
        ArrayList<HashMap<String,String>> infosRunways = new ArrayList<HashMap<String, String>>();
        int nbRunways=0;
        for (int i = 4; i < parsedSnowtam.length; i++){
            String line = parsedSnowtam[i];
            if (line.charAt(1) == ')'){
                String words[] = line.split(" ");
                for (int j = 0; j < words.length; j = j+2) {
                    if (!words[j].equals("N)") && !words[j].equals("R)") && !words[j].equals("T)")) {
                        if (words[j].equals("A)")){
                            ICAO = words[j+1];
                        }
                        else {
                            if (words[j].equals("B)")) {
                                if (nbRunways > 0){
                                    Log.d("bloc_content1",bloc.toString());
                                    infosRunways.add(bloc);
                                }
                                bloc = new HashMap<>();
                                nbRunways++;
                            }
                            bloc.put(words[j],words[j+1]);
                        }
                    } else {
                        break;
                    }
                }

            }
        }
        Log.d("bloc_content2",bloc.toString());
        infosRunways.add(bloc);
        this.airport = Airport.getAirport(ICAO,context);
        for (HashMap<String,String> infoRunway : infosRunways){
            Log.d("infoRunway_content",infoRunway.toString());
            Runway runway = new Runway(infoRunway);
            this.runways.add(runway);
        }
    }

}