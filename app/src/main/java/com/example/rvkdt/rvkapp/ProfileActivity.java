package com.example.rvkdt.rvkapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ProfileActivity extends AppCompatActivity {

    ArrayAdapter <String> eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ListView list = (ListView) findViewById(R.id.eventListi);
        ImageView cover = (ImageView) findViewById(R.id.profileImage);

        String[] test = new String[] {"danni", "Kaffibarinn", "Austur", "Lebowskibar", "hurra"};
        eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);

        cover.setImageResource(R.drawable.hurrapic);

        list.setAdapter(eventAdapter);
    }
}
