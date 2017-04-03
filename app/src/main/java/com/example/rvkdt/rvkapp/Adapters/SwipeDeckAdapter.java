package com.example.rvkdt.rvkapp.Adapters;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rvkdt.rvkapp.Activities.ProfileActivity;
import com.example.rvkdt.rvkapp.Callback;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steinar on 3/1/2017.
 */

public class SwipeDeckAdapter extends BaseAdapter implements Callback {

    private ArrayList<Bar> data;
    private Context context;
    private Callback onClickCallback;

    @Override
    public void onResponse() {

    }

    @Override
    public void onClick() {

    }

    @Override
    public void onFailure() {

    }

    public SwipeDeckAdapter(ArrayList<Bar> data, Context context, Callback onClickCallback) {
        this.data = data;
        this.context = context;
        this.onClickCallback = onClickCallback;
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
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Log.d("hi there", "getView: ");
            // normally use a viewholder
            v = inflater.inflate(R.layout.card_view, parent, false);
        }
        ((TextView) v.findViewById(R.id.barTitle)).setText(data.get(position).getName());
        ((TextView) v.findViewById(R.id.textView)).setText(data.get(position).getAbout());
        Picasso.with(context).load(data.get(position).getImage()).into(((ImageView) v.findViewById(R.id.imageView)));




        String hours = data.get(position).getHours().getHours();
        if (hours != "") {
            ((TextView) v.findViewById(R.id.OpeningHours)).setText(hours);
        }
        else ((TextView) v.findViewById(R.id.OpeningHours)).setText("unkown");



        Log.d("***look***", data.get(position).getHours().getHours());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("tag", "tag");
                v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        onClickCallback.onClick();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

            }
        });


        LocationManager locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ((TextView) v.findViewById(R.id.Distance)).setText("Need gps permission to show distance to bar");
            return v;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            ((TextView) v.findViewById(R.id.Distance)).setText("unkown");
            return v;
        }



        double lat = data.get(position).getLat();
        double lng = data.get(position).getLng();
        Location location2 = new Location("");
        location2.setLatitude(lat);
        location2.setLongitude(lng);

        float distance = location2.distanceTo(location);
        distance = distance / 1000;
        DecimalFormat kiloMeters = new DecimalFormat("#.#");
        DecimalFormat meters = new DecimalFormat("#");

        if (distance < 1) {
            ((TextView) v.findViewById(R.id.Distance)).setText(meters.format(distance*1000) + " m");
        }
        else ((TextView) v.findViewById(R.id.Distance)).setText(kiloMeters.format(distance) + " km");

        return v;
    }
}