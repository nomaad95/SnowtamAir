package com.example.snowtamair;

public class DataModel {

    String name;
    String codeOACI;
    String snowTam;
    int id_;

    public DataModel(String name, String codeOACI, int id_) {
        this.name = name;
        this.codeOACI = codeOACI;
        this.id_ = id_;
    }

    public String getName() {
        return name;
    }

    public String getCodeOACI() {
        return codeOACI;
    }

    public String getSnowTam() {
        return snowTam;
    }

    public int getId() {
        return id_;
    }
}