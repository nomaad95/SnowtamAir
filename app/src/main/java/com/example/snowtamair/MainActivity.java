package com.example.snowtamair;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Vibrator;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, AirportResultCardFragment.OnFragmentInteractionListener {
    private AutoCompleteTextView inputSearch;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    public static JSONArray oaciList;
    public static  NavigationView navigationView;
    private SavedAirports savedAirports = SavedAirports.getInstance();
    private InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // configuration des évènements qui ont lieu dans le MenuActivity accessible depuis cette activité
        hideItem();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Init OaciJson
        try {
            is = getAssets().open("airport.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Init Input Search
        inputSearch = findViewById(R.id.input_search);
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, getListOACI());
        inputSearch.setAdapter(searchAdapter);
        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = new InputFilter.LengthFilter(4);
        filterArray[1] = new InputFilter.AllCaps();
        inputSearch.setFilters(filterArray);

        // Init Search button
        FloatingActionButton btnFab = findViewById(R.id.floatingbtn_search);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                v.vibrate(20);
                if(oaciCheck(inputSearch.getText().toString(), MainActivity.this)){
                    Intent intent = new Intent();
                    intent.setClass( MainActivity.this, AirportActivity.class);
                    intent.putExtra("search", inputSearch.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, getString(R.string.toast_wrong_ICAO), Toast.LENGTH_LONG).show();
                }
            }
        });

        /* /!\ CODE DE STATIC Create Card Airport
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String name = new String("Pertominsk Airport");
        String code = new String("ULAT");
        Bundle bundle = new Bundle();
        bundle.putString("nameAirport", name);
        bundle.putString("codeOACI", code);
        AirportResultCardFragment cardResultFragment1 = new AirportResultCardFragment(code);
        cardResultFragment1.setArguments(bundle);
        ft.add(R.id.linearlayout_airportCardsResults, cardResultFragment1, "frag1");
        ft.addToBackStack(null);
        ft.commit();
         */
    }

    @Override
    protected void onResume() {
        super.onResume();
        // First remove all existing fragment
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        // get Cards Ariport from Saved Airport
        Log.d(String.valueOf(savedAirports.getListAirport()), "onCreate SAVED AIRPORTS : ");
        if(savedAirports.getListAirport()!= null){
            FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
            for(Airport airport : savedAirports.getListAirport()){
                Bundle bundleFrag = new Bundle();
                bundleFrag.putString("nameAirport", airport.getName());
                bundleFrag.putString("codeOACI", airport.getICAO());
                AirportResultCardFragment airportResultCardFragment = new AirportResultCardFragment(airport.getICAO());
                airportResultCardFragment.setArguments(bundleFrag);
                String tag = "tag_fragcardresult_" + airport.getAirport_ID();
                Log.d(tag, "onCreate TAG : ");
                fragtrans.add(R.id.linearlayout_airportCardsResults, airportResultCardFragment, tag);
            }
            fragtrans.addToBackStack(null);
            fragtrans.commit();
        }
    }

    private void hideItem() {
        for(int i = 0; i < 4; i++){
            navigationView.getMenu().getItem(i).setVisible(false);
        }
    }

    public boolean oaciCheck(String oaci, Context context) {
        try {
            //is = context.getAssets().open("airport.json");
            is.reset();
            int size = 0;
            try {
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                //is.close();
                String json = new String(buffer, "UTF-8");
                try {
                    oaciList = new JSONArray(json);
                    for(int i = 0; i < oaciList.length(); i++){
                        JSONObject oaciJson = oaciList.getJSONObject((i));
                        if(oaci.equals(oaciJson.getString("ICAO"))){
                            return true;
                        }
                    }
                    return false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getListOACI(){
        ArrayList<String> listOACI = new ArrayList<String>();
        try {
            is.reset();
            int size = 0;
            try {
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                String json = new String(buffer, "UTF-8");
                try {
                    oaciList = new JSONArray(json);
                    for(int i = 0; i < oaciList.length(); i++){
                        JSONObject oaciJson = oaciList.getJSONObject((i));
                        listOACI.add(oaciJson.getString("ICAO"));
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
        return listOACI;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d("menuActivity", navigationView.getMenu().getItem(0).getTitle().toString());
        int id = menuItem.getItemId();
        Context context = this;

        if (id == R.id.nav_aer1 && (menuItem.getTitle()!="Nom aéroport")) {
            Log.d("menuActivity", "itemSelected");
            Intent intent = new Intent();
            intent.setClass( MainActivity.this, AirportActivity.class);
            intent.putExtra("search", savedAirports.getListAirport().get(0).getICAO());
            startActivity(intent);
        } else if (id == R.id.nav_aer2 && savedAirports.getListAirport().size() >=2) {
            Intent intent = new Intent();
            intent.setClass( MainActivity.this, AirportActivity.class);
            intent.putExtra("search", savedAirports.getListAirport().get(1).getICAO());
            startActivity(intent);

        } else if (id == R.id.nav_aer3 && savedAirports.getListAirport().size() >=3) {
            Intent intent = new Intent();
            intent.setClass( MainActivity.this, AirportActivity.class);
            intent.putExtra("search", savedAirports.getListAirport().get(2).getICAO());
            startActivity(intent);
        } else if (id == R.id.nav_aer4 && savedAirports.getListAirport().size() ==4) {
            Intent intent = new Intent();
            intent.setClass( MainActivity.this, AirportActivity.class);
            intent.putExtra("search", savedAirports.getListAirport().get(3).getICAO());
            startActivity(intent);
        } else if (id == R.id.back) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override // necessary !
    public void onFragmentInteraction(Uri uri) {
        Log.d(String.valueOf(uri), "onFragmentInteraction: ");
    }

    @Override // usless ?
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.d(String.valueOf(hasCapture), "onFragmentInteraction: ");
    }
}
