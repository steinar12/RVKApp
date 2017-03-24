package com.example.rvkdt.rvkapp.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    private final Bar[] bars;
    private final Bar testBar;
    private final BarStorage barStorage;

    public LikedListAdapter(Activity context, Bar[] bars){
        super(context, R.layout.liked_bar, bars);
        Log.d("LikedListAdapter","CALLED CONSTRUCTOR WITH THIS BARS LENGTH: " + bars.length);
        this.ctx = context;
        this.bars = bars;
        this.barStorage = ((BarStorage) context.getApplicationContext());
        this.testBar = barStorage.pop();

    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        Log.d("**POSITION** ", "NUMBER: " + position);
        LayoutInflater inflater = ctx.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.liked_bar, null, true);
        TextView bar_name = (TextView) rowView.findViewById(R.id.name);
        TextView bar_description = (TextView) rowView.findViewById(R.id.description);
        ImageView bar_image = (ImageView) rowView.findViewById(R.id.image);
        bar_image.setImageResource(R.drawable.hurrapic);
        bar_name.setText(bars[position].getName());
        bar_description.setText(bars[position].getAbout());
        return rowView;
    }
}
