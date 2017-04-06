package com.example.rvkdt.rvkapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
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
import com.example.rvkdt.rvkapp.Utils.FacebookHandler;
import com.example.rvkdt.rvkapp.deleteLikedCallback;


import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.android.volley.VolleyLog.TAG;


/**
 * Created by Gunnthor on 04/04/2017.
 */

public class EventListAdapter extends ArrayAdapter<Event> {

    private final Activity ctx;
    private Event[] events;
    private FacebookHandler facebookHandler;


    public EventListAdapter(Activity context, Event[] events){
        super(context, R.layout.event_list_item, events);
        Log.d(TAG,"Loading events " + events.length);
        this.ctx = context;
        this.events = events;
        this.facebookHandler = new FacebookHandler(this.ctx);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        FacebookHandler fbh = this.facebookHandler;
        LayoutInflater inflater = ctx.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.event_list_item, null, true);

        TextView nameText = (TextView) rowView.findViewById(R.id.name);
        nameText.setText(events[position].getName());

        TextView venueText = (TextView) rowView.findViewById(R.id.attendingCount);
        venueText.setText(String.valueOf(events[position].getGuests()));

        TextView startTime = (TextView) rowView.findViewById(R.id.eventStartTime);
        //TextView endTime = (TextView) rowView.findViewById(R.id.eventEndTime);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = formatter.format(events[position].getStartTime());
        //String end = formatter.format(events[position].getEndTime());
        startTime.setText(start);
        //endTime.setText(end);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theLink = events[position].getLink();
                Log.d(TAG, theLink);
                Intent fbSite = facebookHandler.newFacebookIntent(theLink, "event");
                ctx.startActivity(fbSite);
            }
        });

        return rowView;
    }




    public void onClick(View v){
        final int id = v.getId();
        final int barId = (int) v.getTag();

        switch(id){
            case R.id.delete_button:

                break;
            default:
                break;
        }
    }
}
