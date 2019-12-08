package com.example.snowtamair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;


import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * The most basic example of adding a map to an activity.
 */
public class AirportActivity extends AppCompatActivity  implements PisteFragment.OnFragmentInteractionListener, PermissionsListener, OnMapReadyCallback {

    private MapView mapView;
    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private String oaci;
    private Button btnDialogCodeSnowTam;
    private Intent intent;
    private Bundle bundle;
    private Window window;
    private com.mapbox.mapboxsdk.geometry.LatLng latLng;
    private MapboxMap mapboxMap;
    private List<Feature> features;
    private Airport airportObject;

  //  private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;


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
                mapboxMap.setStyle(Style.SATELLITE, new Style.OnStyleLoaded() {
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
        intent = getIntent();
        bundle = intent.getExtras();
        oaci = (String) bundle.get("search");
        window = getWindow();
        airportObject = createAirport(oaci, this);
        setTitle(airportObject.getName());
        //Log.d("airportCheck", String.valueOf(airportObject.getAirport_ID()));

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

    public Airport createAirport(String oaci, Context context) {

        Airport airport = new Airport();

        try {
            Log.d("airportCheck", "ok1");
            InputStream is = context.getAssets().open("airport.json");
            int size = 0;
            try {
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");

                try {
                    JSONArray oaciList = new JSONArray(json);

                    for(int i = 0; i < oaciList.length(); i++){
                        JSONObject oaciJson = oaciList.getJSONObject((i));
                        Log.d("airportCheck", String.valueOf(oaciJson.getString("Airport ID")));
                        if(oaci.equals(oaciJson.getString("ICAO"))){
                            airport.setAirport_ID(Float.parseFloat(oaciJson.getString("Airport ID")));
                            airport.setName(String.valueOf(oaciJson.getString("Name")));
                            airport.setCity(String.valueOf(oaciJson.getString("City")));
                            airport.setCountry(String.valueOf(oaciJson.getString("Country")));
                            airport.setIATA(String.valueOf(oaciJson.getString("IATA")));
                            airport.setICAO(String.valueOf(oaciJson.getString("ICAO")));
                            airport.setLatitude(Float.valueOf(oaciJson.getString("Latitude")));
                            airport.setLongitude(Float.valueOf(oaciJson.getString("Longitude")));
                            airport.setAltitude(Float.valueOf(oaciJson.getString("Altitude")));
                            airport.setTimezone(Float.valueOf(oaciJson.getString("Timezone")));
                            airport.setDST(String.valueOf(oaciJson.getString("DST")));
                            airport.setTz_database_time_zone(String.valueOf(oaciJson.getString("Tz database time zone")));
                            airport.setType(String.valueOf(oaciJson.getString("Type")));
                            airport.setSource(String.valueOf(oaciJson.getString("Source")));
                            return airport;

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("airportCheck", "error when creating airport");

        return null;
    }

    public String getAirportName(String oaci, Context context) {

        try {
            InputStream is = context.getAssets().open("airport.json");
            int size = 0;
            try {
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");

                try {
                    JSONArray oaciList = new JSONArray(json);

                    for(int i = 0; i < oaciList.length(); i++){
                        JSONObject oaciJson = oaciList.getJSONObject((i));
                        if(oaci.equals(oaciJson.getString("ICAO"))){
                            latLng = new LatLng(Double.valueOf(oaciJson.getString("Latitude")), Double.valueOf(oaciJson.getString("Longitude")));
                            return (String) oaciJson.getString("Name");
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        AirportActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // Use a layer manager here


            }
        });
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        /*TextView text = findViewById(R.id.aiportTrackName);
        text.setText(airport.getData());*/

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

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

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