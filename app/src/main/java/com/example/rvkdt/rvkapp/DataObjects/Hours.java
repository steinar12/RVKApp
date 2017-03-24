package com.example.rvkdt.rvkapp.DataObjects;

import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;

import com.example.rvkdt.rvkapp.DataObjects.Pair;


/**
 * Created by Danni on 06/03/2017.
 */


/*
  "hours": {
    "mon_1_open": "18:00",
    "mon_1_close": "01:00",
    "tue_1_open": "18:00",
    "tue_1_close": "01:00",
    "wed_1_open": "18:00",
    "wed_1_close": "01:00",
    "thu_1_open": "18:00",
    "thu_1_close": "01:00",
    "fri_1_open": "18:00",
    "fri_1_close": "04:30",
    "sat_1_open": "18:00",
    "sat_1_close": "04:30",
    "sun_1_open": "18:00",
    "sun_1_close": "01:00"
  },

 */


public class Hours {
    private final String[] weekdays;
    private final ArrayList<Pair> opening_hours;
    private final ArrayList<Pair> closing_hours;


    public Hours(ArrayList<Pair> opening,ArrayList<Pair> closing){

        weekdays = new String[]{"mon","tue","wed","thu","fri","sat","sun"};
        opening_hours = opening;
        closing_hours = closing;
        //opening_hours =
    }

    public String getHours(){
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(new Date());
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String timeZone = DateFormat.getDateTimeInstance().getCalendar().getTimeZone().toString();
        String currentDay = currentDateString.substring(0,3).toLowerCase();
        int minute_mark = currentDateTimeString.length()-3; //The index which contains the first minute
        int hour_mark = minute_mark-5; //The index which contains the first hour
        int current_minute = toMins(currentDateTimeString.substring(hour_mark,minute_mark));
        String open_hours = "";


        ListIterator<Pair> iter_opening = opening_hours.listIterator();
        ListIterator<Pair> iter_closing = closing_hours.listIterator();

        for(int i = 0; i<opening_hours.size(); i++)
        {
            Pair current_opening_pair = opening_hours.get(i);
            Pair current_closing_pair = closing_hours.get(i);
            String day = current_opening_pair.getDay().toLowerCase();//get the first three letters of the day
            String day_name = day.substring(0,3);
            String day_number = day.substring(4,5);
            int opening_minute = toMins(current_opening_pair.getHour());//number of minutes the opening hour is from midnight
            int closing_minute = toMins(current_closing_pair.getHour());//number of minutes the closing hour is from


            if(day_name.equals(currentDay))
            {

                if(current_minute <= opening_minute)
                {
                    boolean isMonday = i <= 0;
                    Pair previous_closing_pair;
                    if(!isMonday) {
                        previous_closing_pair = closing_hours.get(i-1);
                    } else{
                        previous_closing_pair = closing_hours.get(closing_hours.size()-1);
                    }
                    int previous_closing_minute = toMins(previous_closing_pair.getHour());
                    if(current_minute <= previous_closing_minute)
                    {
                        Pair previous_opening_pair;
                        if(isMonday) previous_opening_pair = opening_hours.get(opening_hours.size()-1);
                        else previous_opening_pair = opening_hours.get(i-1);
                        open_hours = parseOpeningHours(previous_opening_pair.getHour(),previous_closing_pair.getHour());
                    }
                    else
                    {
                        open_hours = parseOpeningHours(current_opening_pair.getHour(),current_closing_pair.getHour());

                    }
                }

                else{
                    if(current_minute > closing_minute){
                        Pair next_opening_pair;
                        if(i<opening_hours.size()-1){
                            next_opening_pair = opening_hours.get(i+1);
                            String nextDay = next_opening_pair.getDay().substring(0,3);
                            if(nextDay.equals(currentDay)) {
                                Pair next_closing_pair = closing_hours.get(i+1);
                                open_hours = parseOpeningHours(next_opening_pair.getHour(),next_closing_pair.getHour());

                            }
                            else{
                                open_hours = parseOpeningHours(current_opening_pair.getHour(),current_closing_pair.getHour());

                            }
                        }

                        //Pair next_opening_air = opening_hours.get(i)
                    }

                    else{
                        open_hours = parseOpeningHours(current_opening_pair.getHour(),current_closing_pair.getHour());

                    }
                }


            }

        }

        return open_hours;

    }

    public String parseOpeningHours(String opens, String closes){
        return opens + "-" + closes;
    }


    /**
     * @param s H:m timestamp, i.e. [Hour in day (0-23)]:[Minute in hour (0-59)]
     * @return total minutes after 00:00
     */
    private static int toMins(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }
}
