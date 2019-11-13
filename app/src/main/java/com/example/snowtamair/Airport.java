package com.example.snowtamair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.snowtamair.ui.airport.AirportFragment;

public class Airport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airport_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AirportFragment.newInstance())
                    .commitNow();
        }
    }
}
