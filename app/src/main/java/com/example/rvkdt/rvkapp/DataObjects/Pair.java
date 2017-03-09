package com.example.rvkdt.rvkapp.DataObjects;

/**
 * Created by Danni on 06/03/2017.
 */

public class Pair {
    private final String day;
    private final String hour;

    public Pair(String day_val, String hour_val)
    {
        day = day_val;
        hour = hour_val;
    }

    public String getDay(){return day;}
    public String getHour() {return hour;}
}
