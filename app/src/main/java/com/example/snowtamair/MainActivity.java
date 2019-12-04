package com.example.snowtamair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
=======
>>>>>>> master
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button buttonAer1;
    private Button buttonAer2;
    private Button buttonAer3;
    private Button buttonAer4;
    private ImageButton buttonSearch;
    private EditText inputSearch;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);             // configuration des évènements qui ont lieu dans le menu accessible depuis cette activité
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

        inputSearch = findViewById(R.id.input_search);
        buttonSearch = findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass( MainActivity.this, AirportActivity.class);
                intent.putExtra("search", inputSearch.getText().toString());
                startActivity(intent);
            }
        });


       buttonAer1 = findViewById(R.id.button1);
       buttonAer1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent();
               intent.setClass( MainActivity.this, AirportActivity.class);
               startActivity(intent);
           }
       });
        buttonAer2 = findViewById(R.id.button2);
        buttonAer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass( MainActivity.this, AirportActivity.class);
                startActivity(intent);
            }
        });
        buttonAer3 = findViewById(R.id.button3);
        buttonAer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass( MainActivity.this, AirportActivity.class);
                startActivity(intent);
            }
        });
        buttonAer4 = findViewById(R.id.button4);
        buttonAer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass( MainActivity.this, AirportActivity.class);
                startActivity(intent);
            }
        });



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




}
