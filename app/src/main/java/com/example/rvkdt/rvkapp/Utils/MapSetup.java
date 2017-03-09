package com.example.rvkdt.rvkapp.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Steinar on 3/9/2017.
 */

public class MapSetup {

    Context context;

    public MapSetup(Context ctx) {
        this.context = ctx;
    }

    public GoogleMap setupMap(GoogleMap map, double latitude, double longitude) {

        LatLng coords = new LatLng(latitude, longitude);

        map.addMarker(new MarkerOptions()
                .anchor(0.0f, 1.0f)
                .position(coords));

        map.moveCamera( CameraUpdateFactory.newLatLngZoom( coords, 16.0f) );

        map.getUiSettings().setMyLocationButtonEnabled(false);

        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("hobo", "ruturr");
            return map;
        }

        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(false);

        MapsInitializer.initialize(this.context);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(coords);
        LatLngBounds bounds = builder.build();

        int padding = 0;
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.moveCamera(cameraUpdate);

        return map;
    }
}
