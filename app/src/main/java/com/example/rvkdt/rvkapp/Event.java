package com.example.rvkdt.rvkapp;

/**
 * Created by Oskar on 28.2.2017.
 */

public class Event {
    private String time;
    private String name;
    private String description;
    private String venue;
    private String link;
    public Event (String tme, String nme, String descr, String ven, String lnk) {
        time = tme;
        name =nme;
        description = descr;
        venue = ven;
        description = descr;
        link = lnk;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }
}
