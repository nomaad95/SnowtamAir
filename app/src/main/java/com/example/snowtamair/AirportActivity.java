package com.example.snowtamair;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * The most basic example of adding a map to an activity.
 */
public class AirportActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener ,RunwayFragment.OnFragmentInteractionListener, PermissionsListener, OnMapReadyCallback {

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
    private static final String MARKER_SOURCE = "markers-source";
    private static final String MARKER_STYLE_LAYER = "markers-style-layer";
    private static final String MARKER_IMAGE = "custom-marker";
    private Airport airportObject;
    private Snowtam snowtamObject;
    private String snowtamCode = new String();
    private TextView textViewTime;
    private DrawerLayout drawer;

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
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(airportObject.getLatitude(), airportObject.getLongitude())) // Sets the new camera position
                        .zoom(15) // Sets the zoom
                        .bearing(180) // Rotate the camera
                        .tilt(70) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);
                mapboxMap.setStyle(Style.SATELLITE, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        style.addImage(MARKER_IMAGE, BitmapFactory.decodeResource(
                                AirportActivity.this.getResources(), R.drawable.placeholder3));
                        addMarkers(style);
                    }
                });
            }
        });

        // GET ARGS
        intent = getIntent();
        bundle = intent.getExtras();
        oaci = bundle.getString("search");
        window = getWindow();
        // create Airport
        airportObject = Airport.getAirport(oaci,this);
        // save Airport
        savedAirports.addAirportToList(airportObject);
        updateMenuTitle();
        //Set Toolbar title
        setTitle(airportObject.getName());

        // GET SNOWTAM
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
                    Log.d(snowtamCode, "onSuccess: snowtamCode ");
                    // go to MainActivity ?
                    //finish();
                    // set textview saying No Snowtam
                    TextView tv = findViewById(R.id.textView_noSnowtam);
                    tv.setVisibility(View.VISIBLE);
                    ProgressBar loadingimage= (ProgressBar) findViewById(R.id.progessbar_snowtam_waiting);
                    loadingimage.setVisibility(View.INVISIBLE);
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
                style.addImage(MARKER_IMAGE, BitmapFactory.decodeResource(
                        AirportActivity.this.getResources(), R.drawable.placeholder3));
                addMarkers(style);
            }
        });
    }


    private void addMarkers(@NonNull Style loadedMapStyle) { //ajout d'icônes sur la carte aux positions des boîtes de nuit

        if(airportObject!= null){
            this.latLng = new LatLng(airportObject.getLatitude(), airportObject.getLongitude());
            Log.d("latLng", "latlng: "+airportObject.getLatitude()+","+airportObject.getLongitude());
            //Bitmap icon= BitmapFactory.decodeResource(BoxActivity.this.getResources(),R.drawable.place);
            MarkerOptions markerOptions = new MarkerOptions().setPosition( new LatLng(airportObject.getLatitude(), airportObject.getLongitude()));
            IconFactory iconFactory = IconFactory.getInstance(AirportActivity.this);
            Icon icon = iconFactory.fromResource(R.drawable.placeholder3);
            markerOptions.icon(icon);
        }
        Log.d("bCheck", "appel de addMarker");
        this.features = new ArrayList<>();
        this.features.add(Feature.fromGeometry(Point.fromLngLat(airportObject.getLongitude(),airportObject.getLatitude())));
        /* Source: A data source specifies the geographic coordinate where the image marker gets placed. */
        loadedMapStyle.addSource(new GeoJsonSource(MARKER_SOURCE, FeatureCollection.fromFeatures(features), //création du fichier GeoJson permettant le placemment des markers
                new GeoJsonOptions().withCluster(true).withClusterMaxZoom(14).withClusterRadius(50))); //si les points sont trop nombreux au même endroit, ils sont regroupés en un icône
        /* Style layer: A style layer ties together the source and image and specifies how they are displayed on the map. */
        loadedMapStyle.addLayer(new SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
                .withProperties(
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconIgnorePlacement(true),
                        PropertyFactory.iconImage(MARKER_IMAGE),
                        // Adjust the second number of the Float array based on the height of your marker image.
                        // This is because the bottom of the marker should be anchored to the coordinate point, rather
                        // than the middle of the marker being the anchor point on the map.
                        PropertyFactory.iconOffset(new Float[]{0f, -52f})
                )
        );
    }
    private void updateMenuTitle(){
        SavedAirports savedAirports = SavedAirports.getInstance();
        for(int i =0 ; i < savedAirports.getListAirport().size() ; i++){
            MainActivity.navigationView.getMenu().getItem(i).setTitle(savedAirports.getListAirport().get(i).getName());
        }


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

    /*@Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }*/

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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
            Log.d(runway.getId(), "getItem RUNWAY : ");
            return new RunwayFragment(runway, snowtamCode);
        }

        @Override
        public int getCount() {
            return nbRunways;
        }
    }
}