package com.example.rvkdt.rvkapp.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    public LikedListAdapter(Activity context, Bar[] bars){
        super(context, R.layout.liked_bar, bars);
        this.ctx = context;
        this.bars = bars;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        Log.d("called getview", "lalala");
        LayoutInflater inflater = ctx.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.liked_bar, null, true);
        TextView bar_name = (TextView) rowView.findViewById(R.id.name);
        TextView bar_description = (TextView) rowView.findViewById(R.id.description);
        ImageView bar_image = (ImageView) rowView.findViewById(R.id.image);
        bar_image.setImageResource(R.drawable.cat_1);
        //bar_name.setText(bars[position].getName());

        return rowView;
    }
}
