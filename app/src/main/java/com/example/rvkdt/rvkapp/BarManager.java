package com.example.rvkdt.rvkapp;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oskar on 28.2.2017.
 */


public class BarManager {/*
    private ArrayList<Integer> barids;
    private ArrayList<Bar> bars;

    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String idurl ="http://localhost:3000/api/ids";
    private String barurl ="http://localhost:3000/api/bars";

    // constructor býr til nýtt requestqueue og nær í öll barids, sækir lýka 5 bari
    public BarManager(Context ctx){
        queue = Volley.newRequestQueue(ctx);
        barids = new ArrayList<Integer>();
        bars = new ArrayList<Bar>();
        barids = fetchIds();
        //sækja bari útfrá random ids
        int[] barsToFetch = randomIds(barids, 5);
        fetchBars(barsToFetch);
    }

    //skilar random tölu frá 0 til max
    private int random (int max){
        int range = max + 1;
        int randomNumber = (int) (Math.random()* range);
        return randomNumber;
    }

    // skilar fylki af id af stærð size úr fylki input
    private int[] randomIds (ArrayList<Integer>  input, int size){
        int[] output = new int[size];
        for (int i = 0; i < size; i++){
            int inputSize= input.size();
            int index = random(inputSize);
            output[i] = input.get(index);
            input.remove(index);
        }
        return output;
    }

    // fall sem að sækir þá bari sem samsvara idum sem eru send
    private void fetchBars(int[] ids) {
        // bæta við aðferð sem að postar
    }

    // fall sem að sækir öll id fyrir bari frá api
    private ArrayList<Integer> fetchIds() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, idurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("response", response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // fall sem að skilar bar úr bar fylki og bætir í bar fylki ef það er að verða tómt
    public Bar getBar() {
        if (bars.size() < 3){
            int[] barsToFetch = randomIds(barids, 5);
            fetchBars(barsToFetch);
        }
        Bar output  = bars.get(0);
        bars.remove(0);
        return output;

    }*/
}
