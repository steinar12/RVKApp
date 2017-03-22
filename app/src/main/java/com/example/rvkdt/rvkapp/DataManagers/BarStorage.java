package com.example.rvkdt.rvkapp.DataManagers;

import android.app.Application;

import com.example.rvkdt.rvkapp.DataObjects.Bar;

import java.util.ArrayList;

/**
 * Created by Danni on 09/03/2017.
 */

public class BarStorage extends Application {
    private ArrayList<Bar> listed_bars;
    private ArrayList<Bar> liked_bars;
    private ArrayList<Integer> bar_ids;
    private String test_data;

    public void setListedBars(ArrayList<Bar> bars){ listed_bars = bars; }
    public void setLikedBars(ArrayList<Bar> bars){ liked_bars = bars; }
    public void setBarIds(ArrayList<Integer> ids) {bar_ids = ids;}
    public void setTestData(String data) {test_data = data;}

    public ArrayList<Bar> getListedBars() { return listed_bars; }
    public ArrayList<Bar> getLikedBars() { return liked_bars; }
    public ArrayList<Integer> getBarIds() { return bar_ids; }
    public String getTestData() { return test_data; }

    public int size() {return listed_bars.size();}
    public int likedSize() {return liked_bars.size();}

    public void addAll(ArrayList<Bar> bars) {listed_bars.addAll(bars);}
    public void addAllLiked(ArrayList<Bar> bars) {liked_bars.addAll(bars);}
    public void pushLiked(Bar bar) {liked_bars.add(bar);}
    public void removeLiked(Bar bar)
    {
        for(int i = 0; i<likedSize(); i++)
        {
            Bar current_bar = liked_bars.get(i);
            if(equal(bar,current_bar))
            {
                listed_bars.remove(i);
            }
        }
    }

    public Bar getLikedBar(int id) { return getBar(id,true); }

    public Bar getListedBar(int id){ return getBar(id,false); }

    public boolean equal(Bar bar1, Bar bar2) {

        return bar1.getId() == bar2.getId();
    }
    public void push(Bar bar) {listed_bars.add(bar);}
    public Bar pop() {
        if(size() > 0)
        {
            Bar res = listed_bars.get(0);
            listed_bars.remove(0);
            return res;
        }
        return null;
    }

    public Bar getBar(int id, boolean liked) {
        if(liked)
        {
            for(int i = 0; i<likedSize(); i++)
            {
                Bar current_bar = liked_bars.get(i);
                if(current_bar.getId() == id)
                {
                    return current_bar;
                }
            }

            return null;
        }

        for(int i = 0; i<size(); i++)
        {
            Bar current_bar = listed_bars.get(i);
            if(current_bar.getId() == id)
            {
                return current_bar;
            }
        }

        return null;
    }

}