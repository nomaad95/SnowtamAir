package com.example.snowtamair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * The most basic example of adding a map to an activity.
 */
public class AirportActivity extends FragmentActivity implements PisteFragment.OnFragmentInteractionListener{

    private MapView mapView;
    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    private Button btnDialogCodeSnowTam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));

// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.airport_activity);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setCameraPosition(new CameraPosition.Builder().zoom(15).build());
                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
// Map is set up and the style has loaded. Now you can add data or make other map adjustments.
                    }
                });
            }
        });

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager_pistes);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        // Init dialog btn
        btnDialogCodeSnowTam = findViewById(R.id.btn_dialog_snowtam);
        btnDialogCodeSnowTam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AirportActivity.this)
                        .setTitle("Code SnowTam")
                        .setMessage("the complet and original snowtam")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })
                        //.setNegativeButton(android.R.string.no, null)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(String.valueOf(uri), "onFragmentInteraction: ");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.d(String.valueOf(hasCapture), "onFragmentInteraction: ");
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new PisteFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}