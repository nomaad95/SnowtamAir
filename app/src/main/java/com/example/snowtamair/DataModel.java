package com.example.snowtamair;

public class DataModel {

    String name;
    String code;
    int id_;

    public DataModel(String name, String code, int id_) {
        this.name = name;
        this.code = code;
        this.id_ = id_;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id_;
    }
}