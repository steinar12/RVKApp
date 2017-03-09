package com.example.rvkdt.rvkapp;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.rvkdt.rvkapp.Adapters.SwipeDeckAdapter;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.DataObjects.Event;
import com.example.rvkdt.rvkapp.Database.DBHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Callback {

    private SwipeDeck cardStack;
    private BarManager barManager;

    DBHandler db = new DBHandler(this,"Likedbars",null ,1);

    GoogleMap googleMap;
    @Override
    public  void onResponse(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db.addLikedBarId(1);
        //db.addLikedBarId(88);
        //db.addLikedBarId(2);
        //db.removeBarId(1);
        //db.getLikedBarIds();

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        final Context androidCTX = this;

        barManager = new BarManager(getApplicationContext(), new Callback() {
            @Override
            public void onResponse() { final ArrayList<String> testData = new ArrayList<>();
                testData.add("0");
                testData.add("1");
                testData.add("2");
                testData.add("3");
                testData.add("4");
                testData.add("0");
                testData.add("1");
                testData.add("2");
                testData.add("3");
                testData.add("4");
                testData.add("0");
                testData.add("1");
                testData.add("2");
                testData.add("3");
                testData.add("4");
                testData.add("0");
                testData.add("1");
                testData.add("2");
                testData.add("3");
                testData.add("4");
                testData.add("0");
                testData.add("1");
                testData.add("2");
                testData.add("3");
                testData.add("4");
                testData.add("0");
                testData.add("1");
                testData.add("2");
                testData.add("3");
                testData.add("4");

                final SwipeDeckAdapter adapter = new SwipeDeckAdapter(testData, androidCTX);
                cardStack.setAdapter(adapter);

                cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                    @Override
                    public void cardSwipedLeft(int position) {
                        Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                    }

                    @Override
                    public void cardSwipedRight(int position) {
                        Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                    }

                    @Override
                    public void cardsDepleted() {
                        Log.i("MainActivity", "no more cards");
                    }

                    @Override
                    public void cardActionDown() {
                        Log.i("MainActivity", "up");
                    }

                    @Override
                    public void cardActionUp() {
                        Log.i("MainActivity", "down");
                    }
                });}
        },db);
    }

    public Bar createBar() {
        return barManager.getBar();
    }
}
