package com.example.rifat.hellodoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Navi extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button book_appoint,health_tips,disease_symtoms,show_patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPatient11);
        setSupportActionBar(toolbar);

        book_appoint=(Button) findViewById(R.id.bookingPatient11);
        health_tips=(Button) findViewById(R.id.healthtipsPatient11);
        disease_symtoms=(Button) findViewById(R.id.symptomsPatient11);
        show_patient=(Button) findViewById(R.id.showBookingsPatient11);

        //iv=(ImageView)findViewById(R.id.navheadimage111);
        //tv1=(TextView) findViewById(R.id.navheadfirsttext111);
        //tv2=(TextView) findViewById(R.id.navsecondtextView111);

        //iv.setImageAlpha(R.drawable.yasin);
        //tv1.setText("YASIN ISLAM");
        //tv2.setText("yasin001");
        show_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti=new Intent(getApplicationContext(),Pat_Show_Bookings.class);

                inti.putExtra("PATIENT_NAME",getIntent().getStringExtra("PATIENT_NAME"));
                inti.putExtra("PATIENT_ID",getIntent().getStringExtra("PATIENT_ID"));

                startActivity(inti);
            }
        });



        book_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti=new Intent(getApplicationContext(),Pat_multiple_search.class);
                inti.putExtra("PATIENT_NAME",getIntent().getStringExtra("PATIENT_NAME"));
                inti.putExtra("PATIENT_ID",getIntent().getStringExtra("PATIENT_ID"));
                startActivity(inti);

                //startActivity(new Intent(getApplicationContext(),Pat_multiple_search.class));
            }
        });

        health_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Show_tips_title.class));
            }
        });
        disease_symtoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Show_disease.class));
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(getApplicationContext(),Navi.class));
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.lgoutId)
        {
            startActivity(new Intent(getApplicationContext(),DocPatSelection.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
        } else if (id == R.id.nav_gallery) {
            //startActivity(new Intent(getApplicationContext(),));

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(getApplicationContext(),Show_tips_title.class));

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(getApplicationContext(),Show_disease.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
