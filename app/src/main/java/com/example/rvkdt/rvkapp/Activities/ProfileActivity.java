package com.example.rvkdt.rvkapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.rvkdt.rvkapp.R;
import com.example.rvkdt.rvkapp.Utils.MapSetup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class ProfileActivity extends AppCompatActivity {

    ArrayAdapter <String> eventAdapter;
    MapFragment mapFragment;
    GoogleMap googleMap;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.activity = this;

        ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                1 );
        setContentView(R.layout.activity_profile);

        ListView list = (ListView) findViewById(R.id.eventListi);
        ImageView cover = (ImageView) findViewById(R.id.profileImage);

        String[] test = new String[] {"danni", "Kaffibarinn", "Austur", "Lebowskibar", "hurra"};
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, test);

        cover.setImageResource(R.drawable.hurrapic);

        list.setAdapter(eventAdapter);



        ////////////////////////

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setupMap(googleMap);
            }
        });

        ///////////////////////
    }

    private void setupMap(GoogleMap map) {

        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);

        final LatLng origin = new LatLng(64.148661854835, -21.94014787674);
        final LatLng dest = new LatLng(64.148661854835, -21.94014787674+0.01);

        MapSetup mapSetup = new MapSetup(this, map);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(activity, MapActivity.class);

                i.putExtra("origin-lat", origin.latitude);
                i.putExtra("origin-long", origin.longitude);
                i.putExtra("dest-lat", dest.latitude);
                i.putExtra("dest-long", dest.longitude);

                startActivityForResult(i, 0);
            }
        });

        this.googleMap = mapSetup.setupMap(origin, dest);
    }
}
