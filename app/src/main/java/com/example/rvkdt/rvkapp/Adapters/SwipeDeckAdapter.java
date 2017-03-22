package com.example.rvkdt.rvkapp.Adapters;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rvkdt.rvkapp.Activities.ProfileActivity;
import com.example.rvkdt.rvkapp.Callback;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steinar on 3/1/2017.
 */

public class SwipeDeckAdapter extends BaseAdapter implements Callback {

    private ArrayList<Bar> data;
    private Context context;
    private Callback onClickCallback;

    @Override
    public void onResponse(){

    }

    @Override
    public void onClick(){

    }

    public SwipeDeckAdapter(ArrayList<Bar> data, Context context, Callback onClickCallback) {
        this.data = data;
        this.context = context;
        this.onClickCallback = onClickCallback;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            Log.d("hi there", "getView: ");
            // normally use a viewholder
            v = inflater.inflate(R.layout.card_view, parent, false);
        }
        ((TextView) v.findViewById(R.id.barTitle)).setText(data.get(position).getName());
        ((TextView) v.findViewById(R.id.textView)).setText(data.get(position).getAbout());
        ((RatingBar) v.findViewById(R.id.rating)).setRating((float)data.get(position).getRating());
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(((ImageView) v.findViewById(R.id.imageView)));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("tag", "tag");
                v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        onClickCallback.onClick();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

            }
        });

        return v;
    }
}