package com.example.rvkdt.rvkapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rvkdt.rvkapp.Adapters.EventListAdapter;
import com.example.rvkdt.rvkapp.DataManagers.BarStorage;
import com.example.rvkdt.rvkapp.DataManagers.ImageManager;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.DataObjects.Event;
import com.example.rvkdt.rvkapp.R;
import com.example.rvkdt.rvkapp.Utils.FacebookHandler;
import com.example.rvkdt.rvkapp.Utils.MapSetup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ms.square.android.expandabletextview.ExpandableTextView;



public class ProfileActivity extends AppCompatActivity {

    EventListAdapter eventAdapter;
    MapFragment mapFragment;
    GoogleMap googleMap;
    Activity activity;
    Intent intent;
    private BarStorage barStorage;
    private Bar barData;
    private int id;
    private boolean liked;
    private FacebookHandler facebookHandler;
    private ImageManager imageManager;

    private static final String TAG = ProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        barStorage = ((BarStorage) this.getApplicationContext());
        intent = getIntent();
        super.onCreate(savedInstanceState);

        this.activity = this;
        facebookHandler = new FacebookHandler(activity);
        imageManager = new ImageManager(activity);

        setContentView(R.layout.activity_profile);

        id = intent.getIntExtra("bar_id", -1);
        liked = intent.getBooleanExtra("liked", false);
        Log.d(TAG, "get intent ID-ið" + id);
        if (id < 0) {
            Toast.makeText(this, "Error, invalid id", Toast.LENGTH_LONG).show();
            return;
        }

        //Bar barData = new Bar(1, "KoKo Bar", null, "https://scontent-arn2-1.xx.fbcdn.net/v/t1.0-9/10407812_637843283004053_8987368172014209754_n.jpg?oh=c1f1a5170f469e5b6d0f9bd3dbce8326&oe=5973A783",
        // 1.0, 2.0, "https://www.facebook.com/hurra.is/", "sSpooOOKY TEXT IsA DescRipTIonTeXteRinoO", 2.0, null, null);
        barData = barStorage.getBar(id, liked);


        TextView name = (TextView) findViewById(R.id.barTitle);

        ExpandableTextView desc = (ExpandableTextView) findViewById(R.id.expand_text_view);

        final ListView list = (ListView) findViewById(R.id.eventListi);

        ImageView imageView = (ImageView) findViewById(R.id.profileImage);

        String img_url = barData.getImage();
        int  img_id = barData.getId(); // +.jpeg?

        imageManager.loadImage(img_id, img_url, imageView);

        ImageButton fbButton = (ImageButton) findViewById(R.id.fbButton);


        Event[] events = barData.getEvents();
        eventAdapter = new EventListAdapter(this, events);

        String barName = barData.getName();
        name.setText(barName);
        desc.setText(barData.getAbout());


        list.setAdapter(eventAdapter);


        fbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String theLink = barData.getLink();
                Log.d(TAG, theLink);
                Intent fbSite = facebookHandler.newFacebookIntent(theLink, "page");
                startActivity(fbSite);
            }
        });


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


        final LatLng dest = new LatLng(barData.getLat(), barData.getLng());

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            simpleMapSetup(map,dest);
            return;
        }
        Location orgLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (orgLocation == null) {
            simpleMapSetup(map,dest);
            return;
        }

        final LatLng origin = new LatLng( orgLocation.getLatitude(),orgLocation.getLongitude());

        Location destLocation = new Location("");
        destLocation.setLatitude(dest.latitude);
        destLocation.setLongitude(dest.longitude);
        float distance = orgLocation.distanceTo(destLocation);

        // ef að staðsettning er í lengri en 300 km fjarlægð sínum við bara staðsettningu ekki leiðina þangað
        if (distance > 20000) {
            simpleMapSetup(map,dest);
            return;
        }

        MapSetup mapSetup = new MapSetup(this, map);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(activity, MapActivity.class);
                i.putExtra("marker", false);
                i.putExtra("origin-lat", origin.latitude);
                i.putExtra("origin-long", origin.longitude);
                i.putExtra("dest-lat", dest.latitude);
                i.putExtra("dest-long", dest.longitude);
                startActivityForResult(i, 0);
            }
        });
        LatLng middle = new LatLng((origin.latitude + dest.latitude)/2,(origin.longitude + dest.longitude)/2);

        this.googleMap = mapSetup.setupMap(origin, dest);

        //þarf örugglega að breyta og skoða betur
        float zoom = 10;
        if (distance < 10000) zoom = 10;
        if (distance < 5000) zoom = 12;
        if (distance < 1000) zoom = 17;
        if (distance < 500) zoom = 20;


        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(middle,zoom));
    }

    private void simpleMapSetup(GoogleMap map, LatLng destination){

        final LatLng dest = destination;
        this.googleMap = map;
        this.googleMap.addMarker(new MarkerOptions().position(dest));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dest, 15));

        //bæta við maplistener
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(activity, MapActivity.class);
                i.putExtra("marker", true);
                i.putExtra("dest-lat", dest.latitude);
                i.putExtra("dest-long", dest.longitude);
                startActivityForResult(i, 0);
            }
        });
    }

    ;
}

