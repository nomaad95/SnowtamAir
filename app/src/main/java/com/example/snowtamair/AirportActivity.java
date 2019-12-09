package com.example.snowtamair;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;


import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * The most basic example of adding a map to an activity.
 */
public class AirportActivity extends AppCompatActivity  implements RunwayFragment.OnFragmentInteractionListener, PermissionsListener, OnMapReadyCallback {

    private MapView mapView;
    private int nbRunways = 5;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private String oaci;
    private Intent intent;
    private Bundle bundle;
    private Window window;
    private com.mapbox.mapboxsdk.geometry.LatLng latLng;
    private MapboxMap mapboxMap;
    private List<Feature> features;

    private Airport airportObject;
    private Snowtam snowtamObject;
    private String snowtamCode = new String();
    private TextView textViewTime;

    private SavedAirports savedAirports = SavedAirports.getInstance();
    private SnowtamRequest snowtamRequest;

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

        intent = getIntent();
        bundle = intent.getExtras();
        oaci = (String) bundle.get("search");
        window = getWindow();
        // create Airport
        airportObject = Airport.getAirport(oaci,this);
        // save Airport
        savedAirports.addAirportToList(airportObject);

        Log.d(String.valueOf(airportObject), "onCreate AIRPORT OBJECT: ");

        setTitle(airportObject.getName());
        //Log.d("airportCheck", String.valueOf(airportObject.getAirport_ID()));


        snowtamRequest = new SnowtamRequest(airportObject.getICAO(), AirportActivity.this, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")){
                    snowtamObject = new Snowtam(result, AirportActivity.this);
                    snowtamCode = result;
                    nbRunways = snowtamObject.getRunwaysSize();
                    Log.d(String.valueOf(nbRunways), "onSuccess nbRunways: ");
                    Log.d(snowtamCode, "onSuccess: snowtamCode ");

                    // Instantiate a ViewPager and a PagerAdapter.
                    mPager = (ViewPager) findViewById(R.id.pager_pistes);
                    pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), snowtamObject);
                    mPager.setAdapter(pagerAdapter);

                } else {
                    // go to MainActivity
                    Log.d(snowtamCode, "onSuccess: snowtamCode ");
                    Intent intent = new Intent();
                    intent.setClass( AirportActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private Snowtam snowtamObject;
        public ScreenSlidePagerAdapter(FragmentManager fm, Snowtam snowtam) {
            super(fm);
            snowtamObject = snowtam;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(String.valueOf(snowtamObject), "getItem OBJECT: ");
            Log.d(String.valueOf(position), "getItem position: ");
            Runway runway = snowtamObject.getRunwayFromPosition(position);
            //Runway runway = new Runway();
            return new RunwayFragment(runway);
        }

        @Override
        public int getCount() {
            return nbRunways;
        }
    }
}