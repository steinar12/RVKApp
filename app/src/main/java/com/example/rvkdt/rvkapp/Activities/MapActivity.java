package com.example.rvkdt.rvkapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rvkdt.rvkapp.R;
import com.example.rvkdt.rvkapp.Utils.MapSetup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import static android.R.attr.defaultValue;

public class MapActivity extends AppCompatActivity {

    MapFragment mapFragment;
    GoogleMap googleMap;

    LatLng origin;
    LatLng dest;
    boolean marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (marker) setupMarkerMap(googleMap);
                else setupMap(googleMap);
            }
        });

        // Get location info from the intent
        Intent i = getIntent();

        this.marker = i.getBooleanExtra("marker", true);

        this.origin = new LatLng(i.getDoubleExtra("origin-lat", defaultValue),
                                 i.getDoubleExtra("origin-long", defaultValue));

        this.dest = new LatLng(i.getDoubleExtra("dest-lat", defaultValue),
                i.getDoubleExtra("dest-long", defaultValue));
    }

    private void setupMap(GoogleMap map) {
        MapSetup mapSetup = new MapSetup(this, map);
        this.googleMap = mapSetup.setupMap(origin, dest);
    }

    private void setupMarkerMap(GoogleMap map) {
        this.googleMap = map;
        this.googleMap.addMarker(new MarkerOptions().position(dest));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dest, 15));
    }

    public void onClick(View v) {
        finish();
    }
}
