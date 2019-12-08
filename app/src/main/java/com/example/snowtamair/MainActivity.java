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
import androidx.fragment.app.FragmentTransaction;

import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, AirportResultCardFragment.OnFragmentInteractionListener {
    private Button buttonAer1;
    private Button buttonAer2;
    private Button buttonAer3;
    private ImageButton buttonSearch;
    private EditText inputSearch;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    public static  JSONArray oaciList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // configuration des évènements qui ont lieu dans le MenuActivity accessible depuis cette activité

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

       // Init Btn Search
        inputSearch = findViewById(R.id.input_search);

        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = new InputFilter.LengthFilter(4);
        filterArray[1] = new InputFilter.AllCaps();
        inputSearch.setFilters(filterArray);

        buttonSearch = findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oaciCheck(inputSearch.getText().toString(), MainActivity.this)){
                    Intent intent = new Intent();
                    intent.setClass( MainActivity.this, AirportActivity.class);
                    intent.putExtra("search", inputSearch.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, inputSearch.getText().toString()+" is not a valid ICAO", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Add airport_result_card Fragment
        // /!\ CODE DE TEST
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String name = new String("Pertominsk AirportRequest");
        String code = new String("ULAT");
        Bundle bundle = new Bundle();
        bundle.putString("nameAirport", name);
        bundle.putString("codeOACI", code);
        AirportResultCardFragment cardResultFragment1 = new AirportResultCardFragment();
        cardResultFragment1.setArguments(bundle);
        AirportResultCardFragment cardResultFragment2 = new AirportResultCardFragment();
        cardResultFragment2.setArguments(bundle);
        ft.add(R.id.framelayout_pistes, cardResultFragment1, "frag1");
        ft.add(R.id.framelayout_pistes, cardResultFragment2, "frag2");
        ft.addToBackStack(null);
        ft.commit();

        /*
        // get ListAirport
        SavedAirports savedAirports = new SavedAirports();
        if(savedAirports.getListAirportRequest()!= null){
            FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
            for(Airport airport : savedAirports.getListAirport()){
                Bundle bundleFrag = new Bundle();
                bundleFrag.putString("nameAirport", airportRequest.getNameAirport());
                bundleFrag.putString("codeOACI", airportRequest.getCodeOACI());
                AirportResultCardFragment airportResultCardFragment = new AirportResultCardFragment();
                airportResultCardFragment.setArguments(bundleFrag);
                String tag = "tag_fragcardresult_" + airport.getCodeOACI();
                fragtrans.add(R.id.framelayout_pistes, airportResultCardFragment, tag);
            }
            fragtrans.addToBackStack(null);
            fragtrans.commit();
        }

         */

    }

    public boolean oaciCheck(String oaci, Context context) {
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d("menuActivity", "click");
        int id = menuItem.getItemId();
        Context context = this;

        if (id == R.id.nav_aer1) {
            Log.d("menuActivity", "itemSelected");
            Intent intent = new Intent();
            intent.setClass(context, AirportActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_aer2) {
        } else if (id == R.id.nav_aer3) {
        } else if (id == R.id.nav_aer4) {
        } else if (id == R.id.nav_share) {
            Log.d("menu", "ok");
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
