package com.example.rvkdt.rvkapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.rvkdt.rvkapp.Adapters.LikedListAdapter;
import com.example.rvkdt.rvkapp.DataManagers.BarStorage;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.R;

public class LikedBarsActivity extends AppCompatActivity {
    LikedListAdapter likedListAdapter;
    private BarStorage barStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_bars);
        ListView listView = (ListView) findViewById(R.id.liked_bars);
        barStorage = ((BarStorage)getApplicationContext());
        Bar[] bars = new Bar[30];
        likedListAdapter = new LikedListAdapter(this,bars);
        listView.setAdapter(likedListAdapter);

    }

}
