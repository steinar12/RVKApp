package com.example.rvkdt.rvkapp.DataObjects;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Oskar on 28.2.2017.
 */


public class Bar {

    private int id;
    private String name;
    private String menu;
    private String image;
    private double lat;
    private double lng;
    private String link;
    private String _tag;
    private double rating;
    private Hours hours;
    private Event[] events;
    private String _description;
    public Bar(int idvalue, String nme,String men, String img, double lt, double lg, String lnk, String tag, double rat, Hours hrs, Event[] evt, String description){
        id = idvalue;
        name = nme;
        menu = men;
        image = img;
        lat = lt;
        lng = lg;
        link = lnk;
        _tag = tag;
        rating = rat;
        hours = hrs;
        events = evt;
        _description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMenu() {
        return menu;
    }

    public String getImage() {
        return image;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() { return lng; }

    public String getLink() {
        return link;
    }

    public String getTag() {return _tag; }

    public String getDescription() {return _description;}

    public double getRating() {
        return rating;
    }

    public Hours getHours() {return hours;}

    public Event[] getEvents() {
        return events;
    }

    public float getDistance(Context ctx) {
        LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return -1;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            return -1;
        }

        double lat = this.getLat();
        double lng = this.getLng();
        Location location2 = new Location("");
        location2.setLatitude(lat);
        location2.setLongitude(lng);

        return location2.distanceTo(location);
    }
}
