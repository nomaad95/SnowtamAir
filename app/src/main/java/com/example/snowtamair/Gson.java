package com.example.snowtamair;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Gson {
    private String path = "~/Bureau/5A/appInterfaces/SnowtamAir/app/src/main/assets/airports.json";


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fullfil(){
        try {
            Reader reader = Files.newBufferedReader(Paths.get("airports.json"));
            JsonObject parser = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
