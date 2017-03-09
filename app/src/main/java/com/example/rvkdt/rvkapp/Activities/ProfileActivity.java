package com.example.rvkdt.rvkapp.Activities;

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

public class ProfileActivity extends AppCompatActivity {

    ArrayAdapter <String> eventAdapter;
    MapFragment mapFragment;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        MapSetup mapSetup = new MapSetup(this);
        this.googleMap = mapSetup.setupMap(map, 64.148661854835, -21.94014787674);
    }
}
