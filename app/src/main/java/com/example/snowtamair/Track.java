package com.example.snowtamair;

public class Track {

    public int getNumTrack() {
        return numTrack;
    }

    public void setNumTrack(int numTrack) {
        this.numTrack = numTrack;
    }

    private int numTrack;

    public String getCondTrack() {
        return condTrack;
    }

    public void setCondTrack(String condTrack) {
        this.condTrack = condTrack;
    }

    private String condTrack;

    public Track(int numTrack, String condTrack){
        this.numTrack = numTrack;
        this.condTrack = condTrack;
    }
}
