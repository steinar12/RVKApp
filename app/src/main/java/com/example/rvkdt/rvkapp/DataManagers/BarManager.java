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
import com.example.rvkdt.rvkapp.Utils.Parser;
import com.example.rvkdt.rvkapp.updateListCallback;

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
    private BarStorage barStorage;
    private updateListCallback localCallback;
    private Parser parser;

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
    public BarManager(Context ctx, final updateListCallback mainCallback, DBHandler dataBase){
        localCallback = mainCallback;
        queue = Volley.newRequestQueue(ctx);
        barStorage = ((BarStorage) ctx);
        barStorage.init();
        barStorage.setDbHandler(dataBase);
        parser = new Parser();
        fetchIds(new ResponseCallback() {
            @Override
            public void onResponse(JSONArray response) {
                barStorage.setBarIds(responseToIntList(response));
                int[] idsToRemove = barStorage.getSavedLikedBars();
                if (!(idsToRemove == null)){
                    loadLikedBars(idsToRemove);
                    barStorage.setBarIds(removeIds(barStorage.getBarIds(),idsToRemove));
                }
                int[] barsToFetch = randomIds(barStorage.getBarIds(), 15);
                fetchBars(barsToFetch, new ResponseCallback() {
                    @Override
                    public void onResponse(JSONArray response) {

                        barStorage.addAll(parser.parseBars(response));
                        localCallback.onResponse();
                    }
                    @Override
                    public void onFailure(){
                        localCallback.onFailure();
                    }
                });

            }
            @Override
            public void onFailure(){
                localCallback.onFailure();
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

        if(ids == null)
        {
            Log.d("*BARMANAGER*","ids array is empty");
            return;
        }
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

    // fall sem að skilar bar úr bars arraylist og bætir í bars arraylistann ef það er að verða tómt
    public Bar getBar() {

        int size = barStorage.size();

        Log.d("**BARMANAGER**", "CALLED GETBAR AND SIZE IS: " + size);

        if (size < 10){
            Log.d("**BARMANAGER FETCHING**", "ABOUT TO FETCH MORE BARS");
            int[] barsToFetch = randomIds(barStorage.getBarIds(), 10);
            if(barsToFetch == null) return null;
            fetchBars(barsToFetch, new ResponseCallback() {
                @Override
                public void onResponse(JSONArray response) {
                    barStorage.addAll(parser.parseBars(response));
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

        Log.d("liked bars length: ", likedIds.length + "");
        if(likedIds.length < 1) return;
        fetchBars(likedIds, new ResponseCallback() {
            @Override
            public void onResponse(JSONArray response) {
                barStorage.setLikedBars(parser.parseBars(response));
                localCallback.update();
            }
            @Override
            public void onFailure() {
            }
        });
    }

    public void pushToDeck (Bar bar) { barStorage.pushToDeck(bar); }

    public Bar popDeck () {return barStorage.popDeck();}

    public Bar getCurrentBar () {return barStorage.getCurrentBar();}

    public void pushLiked(Bar bar) {barStorage.pushLiked(bar);}

    public void removeLiked(int bar_id) {barStorage.removeLiked(bar_id);}

    public int getBarCount() {return barStorage.getBarIds().size();}

}