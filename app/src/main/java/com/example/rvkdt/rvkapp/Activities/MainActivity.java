package com.example.rvkdt.rvkapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.rvkdt.rvkapp.Adapters.SwipeDeckAdapter;
import com.example.rvkdt.rvkapp.Managers.BarManager;
import com.example.rvkdt.rvkapp.Callback;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.DataManagers.DBHandler;
import com.example.rvkdt.rvkapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Callback {

    private SwipeDeck cardStack;
    private BarManager barManager;
    private Bar currentBar;

    DBHandler db = new DBHandler(this,"Likedbars",null ,1);

    @Override
    public  void onResponse(){

    }

    @Override
    public  void onClick(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*db.addLikedBarId(1);
        db.addLikedBarId(88);
        db.addLikedBarId(2);
        db.removeBarId(1);
        db.getLikedBarIds();*/

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        final Context androidCTX = this;

        // Interface that is passed into the SwipeDeckAdapter so
        // the adapter can use the onClick function in the MainActivity context.
        final Callback onClickCallback = new Callback() {
            @Override
            public void onResponse() {

            }

            @Override
            public void onClick() {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                int id = currentBar.getId();
                i.putExtra("bar_id",id);
                startActivityForResult(i, 0);
            }
        };

        barManager = new BarManager(getApplicationContext(), new Callback() {
            @Override
            public void onClick() {

            }
            @Override
            public void onResponse() {
                final ArrayList<Bar> bars = new ArrayList<Bar>();

                for(int i = 0; i < 10; i++){
                    Bar bar = barManager.getBar();
                    currentBar = bar;
                    bars.add(bar);
                }

                final SwipeDeckAdapter adapter = new SwipeDeckAdapter(bars, androidCTX, onClickCallback);
                cardStack.setAdapter(adapter);

                cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                    @Override
                    public void cardSwipedLeft(int position) {
                        Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                        Bar bar = barManager.getBar();
                        if (bar != null){
                            currentBar = bar;
                            bars.add(bar);
                        }
                    }

                    @Override
                    public void cardSwipedRight(int position) {
                        Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                        Bar bar = barManager.getBar();
                        if (bar != null){
                            currentBar = bar;
                            bars.add(bar);
                        }
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
        }, db);
    }

    public interface onClickInterface {

    }

}
