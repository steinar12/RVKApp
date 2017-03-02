package com.example.rvkdt.rvkapp;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.DataObjects.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Oskar on 28.2.2017.
 */


public class BarManager {
    private ArrayList<Integer> barids;
    private ArrayList<Bar> bars;

    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String idurl ="http://10.0.2.2:3000/api/ids";
    private String barurl ="http://10.0.2.2:3000/api/bars";

    // constructor býr til nýtt requestqueue og nær í öll barids, sækir líka 5 bari
    public BarManager(Context ctx){
        queue = Volley.newRequestQueue(ctx);
        barids = new ArrayList<Integer>();
        bars = new ArrayList<Bar>();
        fetchIds(new Callback() {
            @Override
            public void onResponse(JSONArray response) {
                barids = responseToIntList(response);
                int[] barsToFetch = randomIds(barids, 15);
                fetchBars(barsToFetch, new Callback() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseToBarList(response);
                    }
                });

            }
        });
    }

    // breytir JSONArray í ArrayList integer og skilar
    private ArrayList<Integer> responseToIntList ( JSONArray data) {
        int length = data.length();
        ArrayList<Integer> output = new ArrayList<Integer>();
        for( int i = 0; i < length; i++ ){
            try {
                int result = data.getInt(i);
                output.add(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    // Tekur gögn um bari í data og breytir í bar objcet og addar þeim í bars ArrayListann
    private void responseToBarList (JSONArray data) {
        int length = data.length();
        for (int i = 0; i < length; i++){
            try {
                JSONObject obj = data.getJSONObject(i);
                String name = obj.getString("name");
                String menu = obj.getString("menu");
                String image = obj.getString("image");
                JSONObject coords = obj.getJSONObject("coords");
                double lat = coords.getDouble("lat");
                double lng = coords.getDouble("lng");
                String link = obj.getString("link");
                String description = obj.getString("description");
                double rating = obj.getDouble("rating");
                // á eftir að útfæra opens og closes rétt
                String opens = obj.getString("opens");
                String closes = obj.getString("closes");

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
                    Event event = new Event(eventName, startTimeDate, endTimeDate, guests, venue, eventLink);
                    events[k] = event;
                }
                if (events.length > 1){
                    Log.d("events", events[0].getStartTime().toString());
                }
                Bar bar = new Bar( name, menu, image, lat, lng, link, description, rating, opens, closes, events);
                bars.add(bar);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Log.d("output", bars.toString());
    }

    // interface sem að er notað í callbökkum
    private interface Callback{
        void onResponse(JSONArray response);
    }

    //skilar random tölu frá 0 til max
    private int random (int max){
        int range = max + 1;
        int randomNumber = (int) (Math.random()* range);
        return randomNumber;
    }

    // skilar fylki af idum sem er jafn stórt size úr fylki input
    private int[] randomIds (ArrayList<Integer>  input, int size){
        int[] output = new int[size];
        for (int i = 0; i < size; i++){
            int inputSize= input.size();
            int index = random(inputSize - 1);
            output[i] = input.get(index);
            input.remove(index);
        }
        return output;
    }

    // fall sem að sækir öll id fyrir bari frá api
    private void fetchIds( final Callback callback) {
        // Request a string response from the provided URL.
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, idurl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Response: ", "error", error);

                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsArrRequest);
    }

    // fall sem að sækir frá api þá bari sem samsvara idum sem eru send
    private void fetchBars(int[] ids, final Callback callback) {

        JSONArray jsonarray = new JSONArray();
        for (int i = 0; i < ids.length; i++) {
            jsonarray.put(ids[i]);
        }

        // Define the POST request
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.POST, barurl, jsonarray, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response: ", response.toString());
                        callback.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Response: ", "error", error);

                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsArrRequest);
    }

   // fall sem að skilar bar úr bars fylki og bætir í bars fylki ef það er að verða tómt
    public Bar getBar() {
        Log.d("ststst",bars.toString());
        int size = bars.size();
        Log.d("hi",String.valueOf(size));


        if (bars.size() < 10){
            int[] barsToFetch = randomIds(barids, 10);
            fetchBars(barsToFetch, new Callback() {
                @Override
                public void onResponse(JSONArray response) {
                    responseToBarList(response);
                }
            });
        }
        Bar output  = bars.get(0);
        bars.remove(0);
        return output;
    }
}
