package com.example.hp.dman;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;

public class DManNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GetHelp.OnFragmentInteractionListener,OnMapReadyCallback,GiveHelp.OnFragmentInteractionListener,Welcome.OnFragmentInteractionListener ,ResourceSelector.OnFragmentInteractionListener{

    SupportMapFragment supportMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportMapFragment=SupportMapFragment.newInstance();

        setContentView(R.layout.activity_dman_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame_layout1,new Welcome()).commit();
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dman_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(supportMapFragment.isAdded())
        {
            FragmentManager fm=getSupportFragmentManager();
            fm.beginTransaction().hide(supportMapFragment).commit();
        }
        if (id == R.id.get_help) {
            FragmentManager fm=getSupportFragmentManager();
                GetHelp gh=new GetHelp();
                fm.beginTransaction().replace(R.id.frame_layout1,gh).addToBackStack(null).commit();
            }
        else if(id==R.id.resSelect)
        {
            FragmentManager fm=getSupportFragmentManager();
            ResourceSelector rs=new ResourceSelector();
            Log.d("Hello","Hi");
            fm.beginTransaction().replace(R.id.frame_layout1,rs).addToBackStack(null).commit();
            Log.d("After replace","Hi");
        }
         else if (id == R.id.give_help) {
            FragmentManager fm=getSupportFragmentManager();
            GiveHelp gh=new GiveHelp();
            fm.beginTransaction().replace(R.id.frame_layout1,gh).addToBackStack(null).commit();

        } else if (id == R.id.show_map) {
            FragmentManager fm=getSupportFragmentManager();
            if(!supportMapFragment.isAdded())
                fm.beginTransaction().add(R.id.map,supportMapFragment).commit();
            else
                fm.beginTransaction().show(supportMapFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onFragmentInteraction(Uri uri){}

    @Override
    public void onMapReady(GoogleMap googleMap) {

        final Geocoder gc=new Geocoder(getApplicationContext());
        final GoogleMap myMap=googleMap;
        DatabaseReference mdb= FirebaseDatabase.getInstance().getReference();
        Query q=mdb.child("Pins");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    boolean flag=true;
                    for(DataSnapshot d:dataSnapshot.getChildren())
                    {
                        try
                        {
                            String pin=d.getKey();
                            Log.d("The pin is ",pin);
                            Address locad=gc.getFromLocationName(d.getKey(),1).get(0);
                            LatLng al=new LatLng(locad.getLatitude(),locad.getLongitude());
                            myMap.addMarker(new MarkerOptions().position(al).title(pin));
                            if(flag==true)
                            {
                                CameraPosition cp = new CameraPosition(al, 15, 0, 0);
                                myMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
                                flag=false;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
