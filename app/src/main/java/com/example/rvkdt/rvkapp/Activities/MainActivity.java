package com.example.rvkdt.rvkapp.Activities;

import android.animation.TimeInterpolator;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.daprlabs.cardstack.SwipeFrameLayout;
import com.example.rvkdt.rvkapp.Adapters.SwipeDeckAdapter;
import com.example.rvkdt.rvkapp.DataManagers.BarStorage;
import com.example.rvkdt.rvkapp.DataManagers.BarManager;
import com.example.rvkdt.rvkapp.Callback;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.DataManagers.DBHandler;
import com.example.rvkdt.rvkapp.Fragments.LikedBarsFragment;
import com.example.rvkdt.rvkapp.R;
import com.example.rvkdt.rvkapp.updateListCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Callback {

    private SwipeDeck cardStack;
    private BarManager barManager;
    private Bar currentBar;
    private final int INITIAL_DECK = 5;

    private ImageView navbutton_cards;
    private ImageView navbutton_heart;
    private SwipeFrameLayout swipe_frame;
    private LinearLayout liked_bars_container;
    private LikedBarsFragment liked_bars_fragment;

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

    @Override
    public  void onFailure(){

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
            liked_bars_container.animate().translationX(0);
        } else if(view_type == "heart") {
            navbutton_heart.animate().scaleX(1.1f).scaleY(1.1f).alpha(1);
            navbutton_cards.animate().scaleX(1f).scaleY(1f).alpha(0.5f);
            swipe_frame.animate().translationX(-width);
            liked_bars_container.animate().translationX(-width);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1 );


        Log.d("snug", "onCreate");

        /*for(int i = 1; i < 89; i++){
            db.addLikedBarId(i);
        }*/
        /*db.addLikedBarId(1);
        db.addLikedBarId(88);
        db.addLikedBarId(2);
        db.addLikedBarId(3);
        db.addLikedBarId(4);
        db.addLikedBarId(5);
        db.addLikedBarId(6);
        db.addLikedBarId(7);
        db.addLikedBarId(8);
        db.addLikedBarId(9);
        db.addLikedBarId(10);
        db.addLikedBarId(11);
        db.addLikedBarId(12);
        db.addLikedBarId(13);
        db.addLikedBarId(14);
        db.addLikedBarId(15);
        db.addLikedBarId(16);
        db.addLikedBarId(17);*/
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
        swipe_frame.setLayoutParams(new LinearLayout.LayoutParams(Math.round(width)-1, LinearLayout.LayoutParams.MATCH_PARENT));
        swipe_frame.animate().setInterpolator(new DecelerateInterpolator()).setDuration(300);

        liked_bars_container = (LinearLayout) findViewById(R.id.liked_bars_container);
        liked_bars_container.setLayoutParams(new LinearLayout.LayoutParams(Math.round(width), LinearLayout.LayoutParams.MATCH_PARENT));
        liked_bars_container.animate().setInterpolator(new DecelerateInterpolator()).setDuration(300);

        // Liked bars fragmentið
        liked_bars_fragment = (LikedBarsFragment) getSupportFragmentManager().findFragmentById(R.id.liked_bars_fragment);

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
            public  void onFailure(){

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

        barManager = new BarManager(getApplicationContext(), new updateListCallback() {

            @Override
            public void update() {
                liked_bars_fragment.update();
            }

            @Override
            public void onClick() {

            }
            @Override
            public void onResponse() {
                final ArrayList<Bar> bars = new ArrayList<Bar>();
                ProgressBar p = (ProgressBar)findViewById(R.id.progressBar2);
                if(p.getVisibility() != View.INVISIBLE){ // check if it is visible
                    p.setVisibility(View.INVISIBLE); // if not set it to visible
                }

                Bar bar = barManager.getBar();
                barManager.pushToDeck(bar);
                bars.add(bar);

                for(int i = 0; i < INITIAL_DECK; i++){
                    bar = barManager.getBar();
                    barManager.pushToDeck(bar);
                    bars.add(bar);
                }
                currentBar = barManager.popDeck();

                final SwipeDeckAdapter adapter = new SwipeDeckAdapter(bars, androidCTX, onClickCallback);
                cardStack.setAdapter(adapter);

                cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                    @Override
                    public void cardSwipedLeft(int position) {
                        Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                        Bar bar = barManager.getBar();
                        if (bar != null){
                            currentBar = barManager.popDeck();
                            Log.d("**MAINACTIVITY**", "CURRENT BAR: " + currentBar.getName());
                            barManager.pushToDeck(bar);
                            bars.add(bar);
                        }
                    }

                    @Override
                    public void cardSwipedRight(int position) {
                        Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                        //liked_bars_fragment.update();
                        Bar bar = barManager.getBar();

                        if (bar != null){
                            barManager.pushLiked(currentBar);
                            currentBar = barManager.popDeck();
                            update();
                            barManager.pushToDeck(bar);
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
            @Override
            public  void onFailure(){
                Toast.makeText(androidCTX, "Something went wrong, server did not respond",
                        Toast.LENGTH_LONG).show();
                ProgressBar p = (ProgressBar)findViewById(R.id.progressBar2);
                if(p.getVisibility() != View.INVISIBLE){ // check if it is visible
                    p.setVisibility(View.INVISIBLE); // if not set it to visible
                }
            }
        }, db);
    }
}
