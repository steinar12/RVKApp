package com.example.rvkdt.rvkapp.Activities;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.daprlabs.cardstack.SwipeDeck;
import com.daprlabs.cardstack.SwipeFrameLayout;
import com.example.rvkdt.rvkapp.Adapters.SwipeDeckAdapter;
import com.example.rvkdt.rvkapp.DataManagers.BarStorage;
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
    private BarStorage barStorage;

    private ImageView navbutton_cards;
    private ImageView navbutton_heart;
    private SwipeFrameLayout swipe_frame;

    // view 1 = cards, view 2 = liked bars
    private boolean cardview_enabled;

    // Width of the screen
    private float width;



    DBHandler db = new DBHandler(this,"Likedbars",null ,1);

    @Override
    public  void onResponse(){

    }

    @Override
    public  void onClick(){

    }

    public void onNavButtonClick(View v){

        switch(v.getId()){
            case R.id.navbutton_cards:
                if(!cardview_enabled) {
                    cardview_enabled = true;
                    set_view("cards");
                }
                break;
            case R.id.navbutton_heart:
                if(cardview_enabled) {
                    cardview_enabled = false;
                    set_view("heart");
                }
                break;
            default:
                break;
        }
    }

    private void set_view(String view_type){
        if(view_type == "cards"){
            navbutton_cards.animate().scaleX(1.1f).scaleY(1.1f).alpha(1);
            navbutton_heart.animate().scaleX(1f).scaleY(1f).alpha(0.5f);
            swipe_frame.animate().translationX(0);
        } else if(view_type == "heart") {
            navbutton_heart.animate().scaleX(1.1f).scaleY(1.1f).alpha(1);
            navbutton_cards.animate().scaleX(1f).scaleY(1f).alpha(0.5f);
            swipe_frame.animate().translationX(-width);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*db.addLikedBarId(1);
        db.addLikedBarId(88);
        db.addLikedBarId(2);*/
        /*int[] j = db.getLikedBarIds();
        for (int k = 0; k < j.length; k++){
            db.removeBarId(j[k]);
        }
        db.addLikedBarId(1);*/
        /*db.getLikedBarIds();*/

        width = getWindowManager().getDefaultDisplay().getWidth();
        cardview_enabled = true;

        navbutton_cards = (ImageView) findViewById(R.id.navbutton_cards);
        navbutton_heart = (ImageView) findViewById(R.id.navbutton_heart);
        navbutton_cards.animate().setInterpolator(new DecelerateInterpolator()).setDuration(150);
        navbutton_heart.animate().setInterpolator(new DecelerateInterpolator()).setDuration(150);


        swipe_frame = (SwipeFrameLayout) findViewById(R.id.swipe_frame);
        swipe_frame.animate().setInterpolator(new DecelerateInterpolator()).setDuration(300);

        set_view("cards");

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
                overridePendingTransition(0, 0);
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
                        //db.addLikedBarId(bars.get(position).getId()); setur liked bar í database
                        //barStorage.pushLiked(bars.get(position)); bætir liked bar í barstorage
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
}
