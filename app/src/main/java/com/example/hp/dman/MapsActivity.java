package com.example.hp.dman;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    String resType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Bundle b=getIntent().getExtras();
        resType=b.getString("ResourceType");
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap mMap = googleMap;
        DatabaseReference mdb= FirebaseDatabase.getInstance().getReference();
        Query q=mdb.child(resType);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<String> al=new ArrayList<String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag=false;
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    Geocoder gc = new Geocoder(getApplicationContext());
                    String pincode = d.getKey();
                    String desc="";
                    if(flag==false)
                    {
                        try
                        {
                            Address locad=gc.getFromLocationName(pincode,1).get(0);
                            LatLng al=new LatLng(locad.getLatitude(),locad.getLongitude());
                            CameraPosition cp=new CameraPosition(al,15,0,0);
                            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
                            flag=true;
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    for (DataSnapshot cd : d.getChildren())
                    {
                        Integer i=cd.getValue(Integer.class);
                        String k=cd.getKey();
                        desc=desc+k+"-"+i.toString()+" ";
                    }

                    try {
                        Address pa = gc.getFromLocationName(pincode, 1).get(0);
                        LatLng al = new LatLng(pa.getLatitude(), pa.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(al).title(pincode).snippet(desc));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
