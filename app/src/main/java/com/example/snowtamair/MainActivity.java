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

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, AirportResultCardFragment.OnFragmentInteractionListener, SearchInputFragment.OnFragmentInteractionListener {
    private Button buttonAer1;
    private Button buttonAer2;
    private Button buttonAer3;
    private ImageButton buttonSearch;
    private EditText inputSearch;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    public static JSONArray oaciList;
    private int searchInputNb = 1;
    public static  NavigationView navigationView;
    private SavedAirports savedAirports = SavedAirports.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // configuration des évènements qui ont lieu dans le MenuActivity accessible depuis cette activité

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Adding search input fragment
       FloatingActionButton btnFab = findViewById(R.id.floatingbtn_add_searchinput);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchInputNb<4){
                    // Add fragment
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    SearchInputFragment searchInputFragment = new SearchInputFragment();
                    searchInputNb ++;
                    String tag = "searchInputNum" + searchInputNb;
                    ft.add(R.id.linearLayout_searchinputs, searchInputFragment, tag);
                    ft.addToBackStack(null);
                    ft.commit();

                } else {
                    // Do nothing
                }
            }
        });

        // /!\ CODE DE STATIC
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



        // get ListSnowtams
        if(savedAirports.getListAirport()!= null){
            FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
            for(Airport airport : savedAirports.getListAirport()){
                Bundle bundleFrag = new Bundle();
                bundleFrag.putString("nameAirport", airport.getName());
                bundleFrag.putString("codeOACI", airport.getICAO());
                AirportResultCardFragment airportResultCardFragment = new AirportResultCardFragment(airport.getICAO());
                airportResultCardFragment.setArguments(bundleFrag);
                String tag = "tag_fragcardresult_" + airport.getAirport_ID();
                fragtrans.add(R.id.linearlayout_airportCardsResults, airportResultCardFragment, tag);
            }
            fragtrans.addToBackStack(null);
            fragtrans.commit();
        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d("menuActivity", "click");
        int id = menuItem.getItemId();
        Context context = this;

        if (id == R.id.nav_aer1 && (menuItem.getTitle()!="Nom aéroport")) {
            Log.d("menuActivity", "itemSelected");
            Intent intent = new Intent();
            intent.setClass( MainActivity.this, AirportActivity.class);
            intent.putExtra("search", savedAirports.getListAirport().get(0).getICAO());
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
