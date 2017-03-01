package com.example.rvkdt.rvkapp;

/**
 * Created by Oskar on 28.2.2017.
 */

public class Bar {
    private Event[] events;
    private String[] menu;
    private String[] image;
    private String[] coords;
    private String[] link;
    private String[] descr;
    private String[] name;
    public Bar(Event[] evt, String[] men, String[] img, String[] cords, String[] lnk, String[] dscr, String[] nam){
        events = evt;
        menu = men;
        image = img;
        coords = cords;
        link = lnk;
        descr = dscr;
        name = nam;
    }

    public Event[] getEvents() {
        return events;
    }

    public String[] getDescr() {
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

    public String[] getName() {
        return name;
    }

    public String[] getCoords() {
        return coords;
    }
}
