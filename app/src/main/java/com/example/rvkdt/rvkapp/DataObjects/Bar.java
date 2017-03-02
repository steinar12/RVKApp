package com.example.rvkdt.rvkapp.DataObjects;

/**
 * Created by Oskar on 28.2.2017.
 */


public class Bar {

    private String name;
    private String menu;
    private String image;
    private double lat;
    private double lng;
    private String link;
    private String description;
    private double rating;
    private String opens;
    private String closes;
    private Event[] events;
    public Bar(String nme,String men, String img, double lt, double lg, String lnk, String desc, double rat, String opn, String cls, Event[] evt){
        name = nme;
        menu = men;
        image = img;
        lat = lt;
        lng = lg;
        link = lnk;
        description = desc;
        rating = rat;
        opens = opn;
        closes = cls;
        events = evt;
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

    public String getOpens() {
        return opens;
    }

    public String getCloses() {
        return closes;
    }

    public Event[] getEvents() {
        return events;
    }
}
