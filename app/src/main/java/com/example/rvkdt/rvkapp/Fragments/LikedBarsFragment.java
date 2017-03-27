package com.example.rvkdt.rvkapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rvkdt.rvkapp.Adapters.LikedListAdapter;
import com.example.rvkdt.rvkapp.DataManagers.BarStorage;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikedBarsFragment extends Fragment {

    LikedListAdapter likedListAdapter;
    private BarStorage barStorage;
    private Context ctx;

    public LikedBarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_liked_bars, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("**LikedBarsFragment**", "About to created LikedListAdapter");
        Log.d("snug", "onActivityCreated");
        ListView listView = (ListView) getView().findViewById(R.id.liked_bars);
        barStorage = ((BarStorage)getActivity().getApplicationContext());
        ArrayList<Bar> likedBars = barStorage.getLikedBars();
        Log.d("**LIKED BARS: ", " length: " + likedBars.size());
        Bar[] bars = new Bar[likedBars.size()];
        likedBars.toArray(bars);
        Log.d("snug", likedBars.size() + "");
        likedListAdapter = new LikedListAdapter(getActivity(),bars);
        listView.setAdapter(likedListAdapter);
        this.update();

    }

    public void update(){
        Log.d("snug", "fjöldi liked bars   " + barStorage.getLikedBars().size() + "");
        ArrayList<Bar> likedBars = barStorage.getLikedBars();
        Bar[] bars = new Bar[likedBars.size()];
        bars = likedBars.toArray(bars);
        likedListAdapter.setBars(bars);
        likedListAdapter.notifyDataSetChanged();
    }
}
