package com.example.rvkdt.rvkapp.Adapters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Steinar on 3/1/2017.
 */

public class SwipeDeckAdapter extends BaseAdapter {

    MapView mapView;
    GoogleMap googleMap;

    Bar bar;

    private List<String> data;
    private Context context;

    public SwipeDeckAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            // normally use a viewholder
            v = inflater.inflate(R.layout.card_view, parent, false);
        }
        //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String)getItem(position);
                Log.i("MainActivity", item);
            }
        });

        // Creates the map
        mapView = (MapView) v.findViewById(R.id.map);
        //mapView.onCreate(savedInstanceState);
        if (mapView != null) {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    System.out.println("123123123123123");
                    configureMap(googleMap);
                }
            });
        }

        return v;
    }

    private void configureMap(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone))
                .anchor(0.0f, 1.0f)
                .position(new LatLng(55.854049, 13.661331)));
        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(64.148661854835, -21.94014787674) , 15.0f) );
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        System.out.println("HEHE");
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        MapsInitializer.initialize(context);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(55.854049, 13.661331));
        LatLngBounds bounds = builder.build();
        int padding = 0;
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cameraUpdate);
        this.googleMap = googleMap;
    }
}