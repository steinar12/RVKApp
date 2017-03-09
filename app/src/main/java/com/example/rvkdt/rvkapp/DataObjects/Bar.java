package com.example.rvkdt.rvkapp.DataObjects;

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
    private String description;
    private double rating;
    private Hours hours;
    private Event[] events;
    public Bar(int idvalue, String nme,String men, String img, double lt, double lg, String lnk, String desc, double rat, Hours hrs, Event[] evt){
        id = idvalue;
        name = nme;
        menu = men;
        image = img;
        lat = lt;
        lng = lg;
        link = lnk;
        description = desc;
        rating = rat;
        hours = hrs;
        events = evt;
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

    public double getLng() {
        return lng;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public Hours getHours() {return hours;}

    public Event[] getEvents() {
        return events;
    }
}
