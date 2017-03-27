package com.example.rvkdt.rvkapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rvkdt.rvkapp.Activities.ProfileActivity;
import com.example.rvkdt.rvkapp.DataManagers.BarStorage;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.DataObjects.Event;
import com.example.rvkdt.rvkapp.DataObjects.Hours;
import com.example.rvkdt.rvkapp.DataObjects.Pair;
import com.example.rvkdt.rvkapp.R;


/**
 * Created by Danni on 09/03/2017.
 */

public class LikedListAdapter extends ArrayAdapter<Bar> {

    private final Activity ctx;
    private Bar[] bars;
    private final Bar testBar;
    private final BarStorage barStorage;

    public LikedListAdapter(Activity context, Bar[] bars){
        super(context, R.layout.liked_bar, bars);
        Log.d("snug","CALLED CONSTRUCTOR WITH THIS BARS LENGTH: " + bars.length);
        this.ctx = context;
        this.bars = bars;
        this.barStorage = ((BarStorage) context.getApplicationContext());
        this.testBar = barStorage.pop();

    }

    public void setBars(Bar[] bars){
        this.bars = bars;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        Log.d("snug", "NUMBER: " + position);
        LayoutInflater inflater = ctx.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.liked_bar2, null, true);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, ProfileActivity.class);
                ctx.startActivityForResult(i, 0);
                //ctx.overridePendingTransition(0, 0);
            }
        });

        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        TextView bar_name = (TextView) rowView.findViewById(R.id.name);
        image.setImageResource(R.drawable.hurrapic);
        bar_name.setText(bars[position].getName());
        return rowView;
    }

    @Override
    public int getCount() {
        return bars.length;
    }
}
