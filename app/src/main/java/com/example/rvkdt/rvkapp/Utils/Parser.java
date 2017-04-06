package com.example.rvkdt.rvkapp.Utils;

import android.content.Context;
import android.util.Log;

import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.DataObjects.Event;
import com.example.rvkdt.rvkapp.DataObjects.Hours;
import com.example.rvkdt.rvkapp.DataObjects.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by Danni on 05/04/2017.
 */

public class Parser {


    public ArrayList<Bar> parseBars (JSONArray data) {
        Log.d("snug","called parseBars with the following data: " + data.toString());

        ArrayList<Bar> output = new ArrayList<>();

        int length = data.length();
        Log.d("snug","length of data: " + length);
        for (int i = 0; i < length; i++){
            try {
                JSONObject obj = data.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                String menu = obj.getString("menu");
                String image = obj.getString("image");
                image = image.replaceAll("\\\\","");
                JSONObject coords = obj.getJSONObject("coords");
                double lat = coords.getDouble("lat");
                double lng = coords.getDouble("lng");
                String link = obj.getString("link");
                link = link.replaceAll("\\\\","");
                String description = obj.getString("description");
                if (description.equals("null") || description.trim().length() == 0) description = "no description";
                String tag = obj.getString("about");
                if (tag.equals("null") || tag.trim().length() == 0) tag = "";
                double rating = obj.getDouble("rating");
                JSONObject opens_obj = obj.getJSONObject("opens");
                JSONObject closes_obj = obj.getJSONObject("closes");
                ArrayList<Pair> parsed_opens = parseHours(opens_obj);
                ArrayList<Pair> parsed_closes = parseHours(closes_obj);
                Hours hours = new Hours(parsed_opens,parsed_closes);
                String hour_format = hours.getHours();
                //Log.d("About to print hours","-----Message-----");
                //Log.d("**HOURS**: ",hour_format);
                JSONArray jsonEvent = obj.getJSONArray("events");
                int eventLength = jsonEvent.length();
                Event[] events = new Event[eventLength];
                for (int k = 0; k < eventLength; k++){
                    JSONObject eventObject = jsonEvent.getJSONObject(k);
                    String eventName = eventObject.getString("name");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
                    String startTime = eventObject.getString("startTime");
                    Date startTimeDate = dateFormat.parse(startTime);
                    String endTime = eventObject.getString("endTime");
                    Date endTimeDate = dateFormat.parse(endTime);
                    int guests = eventObject.getInt("guests");
                    String venue = eventObject.getString("venue");
                    String eventLink = eventObject.getString("link");
                    eventLink = eventLink.replaceAll("\\\\","");
                    Event event = new Event(eventName, startTimeDate, endTimeDate, guests, venue, eventLink);
                    events[k] = event;
                }
                if (events.length > 1){
                    Log.d("events", events[0].getStartTime().toString());
                }
                Bar bar = new Bar(id, name, menu, image, lat, lng, link, tag, rating, hours, events, description);

                //Send the image to ImageSaver to store the image.
                String cleanImage = image.replaceAll("\\\\","");
                Log.d("BarManager", "imagesaver cleanImage:: " + cleanImage);
                Log.d("snug", "about to add this bar:  " + name);

                output.add(bar);
            } catch (JSONException e) {
                Log.d("snug","Got JSON exception: " + e.getMessage());
                e.printStackTrace();
            } catch (ParseException e) {
                Log.d("snug","Got Parse exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return output;
    }


    /**
     * Parses hours into a integer array containing hours in correct weekday order
     *
     * @param {JSONObject} hours - hours that are to be parsed
     * @returns {String[]} - hours in correct order
     */
    private ArrayList<Pair> parseHours(JSONObject hours){
        Iterator<String> iter = hours.keys();
        ArrayList<Pair> res = new ArrayList<Pair>();
        while (iter.hasNext()) {
            String key = iter.next();
            try {

                String hour = hours.getString(key);
                Pair pair = new Pair(key,hour);
                res.add(pair);

            } catch (JSONException e) {
                e.printStackTrace();
                // Something went wrong!
            }
        }
        return res;
    }
}
