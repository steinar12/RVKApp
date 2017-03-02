package com.example.rvkdt.rvkapp.DataObjects;

/**
 * Created by Oskar on 28.2.2017.
 */

public class Bar {
    private Event[] events;
    private String[] menu;
    private String[] image;
    private String[] coords;
    private String[] link;
    private String descr;
    private String name;
    private double rating;
    public Bar(Event[] evt, String[] men, String[] img, String[] cords, String[] lnk, String dscr, String nam, double rating){
        this.events = evt;
        this.menu = men;
        this.image = img;
        this.coords = cords;
        this.link = lnk;
        this.descr = dscr;
        this.name = nam;
        this.rating = rating;
    }

    public Event[] getEvents() {
        return events;
    }

    public String getDescr() {
        return descr;
    }

    public String[] getImage() {
        return image;
    }

    public String[] getLink() {
        return link;
    }

    public String[] getMenu() {
        return menu;
    }

    public String getName() {
        return name;
    }

    public String[] getCoords() {
        return coords;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
