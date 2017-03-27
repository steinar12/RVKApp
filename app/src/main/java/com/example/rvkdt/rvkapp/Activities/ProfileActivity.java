package com.example.rvkdt.rvkapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rvkdt.rvkapp.DataManagers.BarStorage;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.R;
import com.example.rvkdt.rvkapp.Utils.ImageSaver;
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
    Intent intent;
    private ImageSaver imageSaver;
    private BarStorage barStorage;
    private int id;

    private static final String TAG = ProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageSaver = new ImageSaver(this);
        barStorage = ((BarStorage) this.getApplicationContext());
        intent = getIntent();
        super.onCreate(savedInstanceState);


        this.activity = this;

        ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1 );
        setContentView(R.layout.activity_profile);
        //(int idvalue, String nme,String men,
        // String img, double lt, double lg,
        // String lnk, String desc, double rat,
        // Hours hrs, Event[] evt){
        id  = intent.getIntExtra("bar_id", -1);
        Log.d(TAG, "get intent ID-ið" + id);
        if(id < 0) {
            Toast.makeText(this, "Error, invalid id", Toast.LENGTH_LONG).show();
            return;
        }

       //Bar barData = new Bar(1, "KoKo Bar", null, "https://scontent-arn2-1.xx.fbcdn.net/v/t1.0-9/10407812_637843283004053_8987368172014209754_n.jpg?oh=c1f1a5170f469e5b6d0f9bd3dbce8326&oe=5973A783",
        //1.0, 2.0, "https://www.facebook.com/hurra.is/", "sSpooOOKY TEXT IsA DescRipTIonTeXteRinoO", 2.0, null, null);
        Bar barData = barStorage.getBar(id, false);
        Log.d(TAG, "le barData??!!" + barData);

        TextView name = (TextView) findViewById(R.id.barTitle);
        TextView desc = (TextView) findViewById(R.id.descriptionTexti);
        TextView abt = (TextView) findViewById(R.id.aboutTexti);
        TextView fbLink = (TextView) findViewById(R.id.facebookLink);
        RatingBar rating = (RatingBar) findViewById(R.id.rating);
        ListView list = (ListView) findViewById(R.id.eventListi);
        ImageView cover = (ImageView) findViewById(R.id.profileImage);

        Button loadButton = (Button) findViewById(R.id.buttonLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //action
                //Bitmap bm = imageSaver.loadCoverPhoto();
                //ImageView coverDD = (ImageView) findViewById(R.id.profileImage);
                //coverDD.setImageBitmap(bm);
                Intent test = newFacebookIntent(activity.getPackageManager(), "https://www.facebook.com/hurra.is/");
                startActivity(test);

            }
        });

        String[] test = new String[] {"danni", "Kaffibarinn", "Austur", "Lebowskibar", "hurra"};
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, test);

        //cover.setImageResource(R.drawable.hurrapic);
        setCoverImage(barData.getId());
        String barName = barData.getName();
        name.setText(barName);
        desc.setText(barData.getAbout());
        fbLink.setClickable(true);
        fbLink.setMovementMethod(LinkMovementMethod.getInstance());
        String fbHref = "<a href=" + barData.getLink() + ">" + barData.getName() + "Facebook Site </a>";
        fbLink.setText(Html.fromHtml(fbHref));
        rating.setStepSize((float) 0.1);
        rating.setRating((float) 2.0);

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

    private void setCoverImage(int id) {
        Bitmap bm = imageSaver.loadCoverPhoto(id);
        ImageView cover = (ImageView) findViewById(R.id.profileImage);
        cover.setImageBitmap(bm);
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

    /**
     * <p>Intent to open the official Facebook app. If the Facebook app is not installed then the
     * default web browser will be used.</p>
     *
     * <p>Example usage:</p>
     *
     * {@code newFacebookIntent(ctx.getPackageManager(), "https://www.facebook.com/JRummyApps");}
     *
     * @param pm
     *     The {@link PackageManager}. You can find this class through {@link
     *     Context#getPackageManager()}.
     * @param url
     *     The full URL to the Facebook page or profile.
     * @return An intent that will open the Facebook page/profile.
     */
    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            Log.d(TAG, "" + ignored);
        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
