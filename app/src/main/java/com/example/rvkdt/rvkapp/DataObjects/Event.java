package com.example.rvkdt.rvkapp.DataObjects;

import java.util.Date;

/**
 * Created by Oskar on 28.2.2017.
 */

public class Event {
    private String name;
    private Date startTime;
    private Date endTime;
    private int guests;
    private String venue;
    private String link;
    public Event (String nme, Date stm, Date etm, int gst, String ven, String lnk) {
        name = nme;
        startTime = stm;
        endTime = etm;
        guests = gst;
        venue = ven;
        link = lnk;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getGuests() {
        return guests;
    }

    public String getVenue() {
        return venue;
    }

    public String getLink() {
        return link;
    }
}
