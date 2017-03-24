package com.example.rvkdt.rvkapp.DataManagers;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.rvkdt.rvkapp.Callback;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.DataObjects.Event;
import com.example.rvkdt.rvkapp.DataObjects.Hours;
import com.example.rvkdt.rvkapp.DataObjects.Pair;
import com.example.rvkdt.rvkapp.DataManagers.DBHandler;
import com.example.rvkdt.rvkapp.DataManagers.BarStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by Oskar on 28.2.2017.
 */


public class BarManager implements Callback {
    private DBHandler db;
    private BarStorage barStorage;

    @Override
    public void onResponse(){

    }

    @Override
    public void onClick(){

    }

    @Override
    public  void onFailure(){

    }

    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String idurl = "https://rvkapp.herokuapp.com/api/ids";
    private String barurl = "https://rvkapp.herokuapp.com/api/bars";


    // constructor býr til nýtt requestqueue og nær í öll barids, sækir líka 5 bari
    public BarManager(Context ctx, final Callback mainCallback, DBHandler dataBase){
        queue = Volley.newRequestQueue(ctx);
        barStorage = ((BarStorage) ctx);
        barStorage.setBarIds(new ArrayList<Integer>());
        barStorage.setListedBars(new ArrayList<Bar>());
        barStorage.setLikedBars(new ArrayList<Bar>());
        barStorage.setBarsInDeck(new ArrayList<Bar>());
        db = dataBase;
        fetchIds(new ResponseCallback() {
            @Override
            public void onResponse(JSONArray response) {
                barStorage.setBarIds(responseToIntList(response));
                int[] idsToRemove = db.getLikedBarIds();
                loadLikedBars(idsToRemove);
                barStorage.setBarIds(removeIds(barStorage.getBarIds(),idsToRemove));
                if (!(idsToRemove == null)){
                    barStorage.setBarIds(removeIds(barStorage.getBarIds(),idsToRemove));
                }
                int[] barsToFetch = randomIds(barStorage.getBarIds(), 15);
                fetchBars(barsToFetch, new ResponseCallback() {
                    @Override
                    public void onResponse(JSONArray response) {
                        barStorage.addAll(responseToBarList(response));
                        mainCallback.onResponse();
                    }
                    @Override
                    public void onFailure(){
                        mainCallback.onFailure();
                    }
                });

            }
            @Override
            public void onFailure(){
                mainCallback.onFailure();
            }
        });
    }

    private ArrayList<Integer> removeIds (ArrayList<Integer> id, int[] idsToRemove){
        for(int i = 0; i < idsToRemove.length; i++){
            for (int j = 0; j < id.size(); j++) {
                if (id.get(j) == idsToRemove[i]){
                    id.remove(j);
                    break;
                }
            }
        }
        return id;
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
    private ArrayList<Bar> responseToBarList (JSONArray data) {
        ArrayList<Bar> output = new ArrayList<Bar>();

        int length = data.length();
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
                String about = obj.getString("about");
                if (about.equals("null") || about.trim().length() == 0) about = description;
                if (about.equals("null") || about.trim().length() == 0) about = "no description";
                double rating = obj.getDouble("rating");
                // á eftir að útfæra opens og closes rétt
                JSONObject opens_obj = obj.getJSONObject("opens");
                JSONObject closes_obj = obj.getJSONObject("closes");
                ArrayList<Pair> parsed_opens = parseHours(opens_obj);
                ArrayList<Pair> parsed_closes = parseHours(closes_obj);
                //Hours hours = new Hours(parsed_opens,parsed_closes);
                //String hour_format = hours.getHours();
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
                Bar bar = new Bar(id, name, menu, image, lat, lng, link, about, rating, null, events);
                output.add(bar);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    // interface sem að er notað í callbökkum
    private interface ResponseCallback{
        void onResponse(JSONArray response);
        void onFailure();
    }

    //skilar random tölu frá 0 til max
    private int random (int max){
        int range = max + 1;
        int randomNumber = (int) (Math.random()* range);
        return randomNumber;
    }

    // skilar fylki af idum sem er jafn stórt size úr fylki input
    private int[] randomIds (ArrayList<Integer> input, int size){
        if(input.size() == 0) return null;
        if (size > input.size()) size = input.size();
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
    private void fetchIds( final ResponseCallback callback) {
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
                        callback.onFailure();

                    }
                });
        jsArrRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 10;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsArrRequest);
    }

    // fall sem að sækir frá api þá bari sem samsvara idum sem eru send
    private void fetchBars(int[] ids, final ResponseCallback callback) {

        JSONArray jsonarray = new JSONArray();
        for (int i = 0; i < ids.length; i++) {
            jsonarray.put(ids[i]);
        }

        // Define the POST request
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.POST, barurl, jsonarray, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       // Log.d("Response: ", response.toString());
                        callback.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Response: ", "error", error);
                        callback.onFailure();

                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsArrRequest);
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

   // fall sem að skilar bar úr bars fylki og bætir í bars fylki ef það er að verða tómt
    public Bar getBar() {

        int size = barStorage.size();

        if (size < 10){
            int[] barsToFetch = randomIds(barStorage.getBarIds(), 10);
            if(barsToFetch == null) return null;
            fetchBars(barsToFetch, new ResponseCallback() {
                @Override
                public void onResponse(JSONArray response) {
                    barStorage.addAll(responseToBarList(response));
                }
                @Override
                public void onFailure(){

                }
            });
        }
        Bar output  = barStorage.pop();
        return output;
    }


    public void loadLikedBars (int [] likedIds) {
        final ArrayList<Bar> likedBars = new ArrayList<Bar>();
        fetchBars(likedIds, new ResponseCallback() {
            @Override
            public void onResponse(JSONArray response) {
                barStorage.setLikedBars(responseToBarList(response));
            }
            @Override
            public void onFailure() {
            }
        });
    }

    public void pushToDeck (Bar bar) { barStorage.pushToDeck(bar); }

    public Bar popDeck () {return barStorage.popDeck();}

}
