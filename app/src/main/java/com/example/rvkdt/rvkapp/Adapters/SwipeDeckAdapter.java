package com.example.rvkdt.rvkapp.Adapters;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rvkdt.rvkapp.Callback;
import com.example.rvkdt.rvkapp.DataManagers.ImageManager;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.R;
import com.example.rvkdt.rvkapp.onClickCallback;


import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Steinar on 3/1/2017.
 */

public class SwipeDeckAdapter extends BaseAdapter implements onClickCallback {

    private ArrayList<Bar> data;
    private Context context;
    private onClickCallback cb;
    private ImageManager imageManager;

    @Override
    public void onClick(View view) {

    }

    public SwipeDeckAdapter(ArrayList<Bar> data, Context context, onClickCallback cb) {
        this.data = data;
        this.context = context;
        this.cb = cb;
        this.imageManager = new ImageManager(context);

        /*for(int i = 1; i < 67; i++){
            imageManager.deleteImage("bar_image_" + i);
        }*/
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
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Log.d("hi there", "getView: ");
            // normally use a viewholder
            v = inflater.inflate(R.layout.card_view, parent, false);
        }
        Log.d("swipedeckadapter", "next log..");
        Log.d("SwipeDeckAdapater", data.get(position).getName());
        ((TextView) v.findViewById(R.id.barTitle)).setText(data.get(position).getName());
        //((TextView) v.findViewById(R.id.textView)).setText(data.get(position).getAbout());

        Bar bar = data.get(position);


        /////////////////////////////////

        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        String img_url = bar.getImage();
        int img_id = bar.getId(); // +.jpeg?

        imageManager.loadImage(img_id, img_url, imageView);


        /////////////////////////////////


        String hours = bar.getHours().getHours();
        if (hours != "") {
            ((TextView) v.findViewById(R.id.OpeningHours)).setText(hours);
        }
        else ((TextView) v.findViewById(R.id.OpeningHours)).setText("Unknown");

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                cb.onClick(v);
            }
        });

        float distance = bar.getDistance(this.context);

        if (distance < 0.0) {
            ((TextView) v.findViewById(R.id.Distance)).setText("unable to calculate distance");
            return v;
        }
        distance = distance / 1000;
        DecimalFormat kiloMeters = new DecimalFormat("#.#");
        DecimalFormat meters = new DecimalFormat("#");

        if (distance < 1) {
            ((TextView) v.findViewById(R.id.Distance)).setText(meters.format(distance*1000) + " m");
        }
        else ((TextView) v.findViewById(R.id.Distance)).setText(kiloMeters.format(distance) + " km");

        return v;
    }
}