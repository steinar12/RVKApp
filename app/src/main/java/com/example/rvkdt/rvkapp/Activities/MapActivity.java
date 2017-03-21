package com.example.rvkdt.rvkapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rvkdt.rvkapp.R;
import com.example.rvkdt.rvkapp.Utils.MapSetup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends AppCompatActivity {

    MapFragment mapFragment;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setupMap(googleMap);
            }
        });
    }

    private void setupMap(GoogleMap map) {
        MapSetup mapSetup = new MapSetup(this, map);
        this.googleMap = mapSetup.setupMap(64.148661854835, -21.94014787674);
    }

    public void onClick(View v) {
        finish();
    }
}
