package com.example.rvkdt.rvkapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rvkdt.rvkapp.DataManagers.BarStorage;
import com.example.rvkdt.rvkapp.DataObjects.Bar;
import com.example.rvkdt.rvkapp.R;
import com.example.rvkdt.rvkapp.Utils.ImageSaver;
import com.example.rvkdt.rvkapp.Utils.MapSetup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.ms.square.android.expandabletextview.ExpandableTextView;


public class ProfileActivity extends AppCompatActivity {

    ArrayAdapter <String> eventAdapter;
    MapFragment mapFragment;
    GoogleMap googleMap;
    Activity activity;
    Intent intent;
    private ImageSaver imageSaver;
    private BarStorage barStorage;
    private int id;

    private static final String TAG = ProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageSaver = new ImageSaver(this);
        barStorage = ((BarStorage) this.getApplicationContext());
        intent = getIntent();
        super.onCreate(savedInstanceState);

        this.activity = this;

        setContentView(R.layout.activity_profile);
        //(int idvalue, String nme,String men,
        // String img, double lt, double lg,
        // String lnk, String desc, double rat,
        // Hours hrs, Event[] evt){
        id  = intent.getIntExtra("bar_id", -1);
        Log.d(TAG, "get intent ID-ið" + id);
        if(id < 0) {
            Toast.makeText(this, "Error, invalid id", Toast.LENGTH_LONG).show();
            return;
        }

       //Bar barData = new Bar(1, "KoKo Bar", null, "https://scontent-arn2-1.xx.fbcdn.net/v/t1.0-9/10407812_637843283004053_8987368172014209754_n.jpg?oh=c1f1a5170f469e5b6d0f9bd3dbce8326&oe=5973A783",
        // 1.0, 2.0, "https://www.facebook.com/hurra.is/", "sSpooOOKY TEXT IsA DescRipTIonTeXteRinoO", 2.0, null, null);
        final Bar barData = barStorage.getBar(id, false);
        Log.d(TAG, "le barData??!!" + barData);
        Log.d(TAG, "barData.getLink" + barData.getLink());
        Log.d(TAG, "barData.getImage" + barData.getImage());


        TextView name = (TextView) findViewById(R.id.barTitle);
        //TextView desc = (TextView) findViewById(R.id.descriptionTexti);
        TextView abt = (TextView) findViewById(R.id.aboutTexti);
        TextView fbLink = (TextView) findViewById(R.id.facebookLink);

        ExpandableTextView desc = (ExpandableTextView) findViewById(R.id.expand_text_view);

        RatingBar rating = (RatingBar) findViewById(R.id.rating);

        ListView list = (ListView) findViewById(R.id.eventListi);

        ImageView cover = (ImageView) findViewById(R.id.profileImage);

        ImageButton fbButton = (ImageButton) findViewById(R.id.fbButton);

        Button loadButton = (Button) findViewById(R.id.buttonLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //action
                //Bitmap bm = imageSaver.loadCoverPhoto();
                //ImageView coverDD = (ImageView) findViewById(R.id.profileImage);
                //coverDD.setImageBitmap(bm);
                //Intent test = newFacebookIntent(activity.getPackageManager(), "https://www.facebook.com/hurra.is/");
                //startActivity(test);

            }
        });



        String[] test = new String[] {"danni", "Kaffibarinn", "Austur", "Lebowskibar", "hurra"};
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, test);

        //cover.setImageResource(R.drawable.hurrapic);
        setCoverImage(barData.getId());
        String barName = barData.getName();
        name.setText(barName);
        //desc.setText(barData.getAbout());
        desc.setText("Kindness to he horrible reserved ye. Effect twenty indeed beyond for not had county. The use him without greatly can private. Increasing it unpleasant no of contrasted no continuing. Nothing colonel my no removed in weather. It dissimilar in up devonshire inhabiting. \n" +
                "\n" +
                "Started earnest brother believe an exposed so. Me he believing daughters if forfeited at furniture. Age again and stuff downs spoke. Late hour new nay able fat each sell. Nor themselves age introduced frequently use unsatiable devonshire get. They why quit gay cold rose deal park. One same they four did ask busy. Reserved opinions fat him nay position. Breakfast as zealously incommode do agreeable furniture. One too nay led fanny allow plate. \n" +
                "\n" +
                "Material confined likewise it humanity raillery an unpacked as he. Three chief merit no if. Now how her edward engage not horses. Oh resolution he dissimilar precaution to comparison an. Matters engaged between he of pursuit manners we moments. Merit gay end sight front. Manor equal it on again ye folly by match. In so melancholy as an sentiments simplicity connection. Far supply depart branch agreed old get our. \n" +
                "\n" +
                "Dwelling and speedily ignorant any steepest. Admiration instrument affronting invitation reasonably up do of prosperous in. Shy saw declared age debating ecstatic man. Call in so want pure rank am dear were. Remarkably to continuing in surrounded diminution on. In unfeeling existence objection immediate repulsive on he in. Imprudence comparison uncommonly me he difficulty diminution resolution. Likewise proposal differed scarcely dwelling as on raillery. September few dependent extremity own continued and ten prevailed attending. Early to weeks we could. \n" +
                "\n" +
                "Full age sex set feel her told. Tastes giving in passed direct me valley as supply. End great stood boy noisy often way taken short. Rent the size our more door. Years no place abode in \uFEFFno child my. Man pianoforte too solicitude friendship devonshire ten ask. Course sooner its silent but formal she led. Extensive he assurance extremity at breakfast. Dear sure ye sold fine sell on. Projection at up connection literature insensible motionless projecting. \n" +
                "\n" +
                "Exquisite cordially mr happiness of neglected distrusts. Boisterous impossible unaffected he me everything. Is fine loud deal an rent open give. Find upon and sent spot song son eyes. Do endeavor he differed carriage is learning my graceful. Feel plan know is he like on pure. See burst found sir met think hopes are marry among. Delightful remarkably new assistance saw literature mrs favourable. \n" +
                "\n" +
                "He share of first to worse. Weddings and any opinions suitable smallest nay. My he houses or months settle remove ladies appear. Engrossed suffering supposing he recommend do eagerness. Commanded no of depending extremity recommend attention tolerably. Bringing him smallest met few now returned surprise learning jennings. Objection delivered eagerness he exquisite at do in. Warmly up he nearer mr merely me. \n" +
                "\n" +
                "Shewing met parties gravity husband sex pleased. On to no kind do next feel held walk. Last own loud and knew give gay four. Sentiments motionless or principles preference excellence am. Literature surrounded insensible at indulgence or to admiration remarkably. Matter future lovers desire marked boy use. Chamber reached do he nothing be. \n" +
                "\n" +
                "Cause dried no solid no an small so still widen. Ten weather evident smiling bed against she examine its. Rendered far opinions two yet moderate sex striking. Sufficient motionless compliment by stimulated assistance at. Convinced resolving extensive agreeable in it on as remainder. Cordially say affection met who propriety him. Are man she towards private weather pleased. In more part he lose need so want rank no. At bringing or he sensible pleasure. Prevent he parlors do waiting be females an message society. \n" +
                "\n" +
                "Dashwood contempt on mr unlocked resolved provided of of. Stanhill wondered it it welcomed oh. Hundred no prudent he however smiling at an offence. If earnestly extremity he he propriety something admitting convinced ye. Pleasant in to although as if differed horrible. Mirth his quick its set front enjoy hoped had there. Who connection imprudence middletons too but increasing celebrated principles joy. Herself too improve gay winding ask expense are compact. New all paid few hard pure she. \n" +
                "\n");
        fbLink.setClickable(true);
        fbLink.setMovementMethod(LinkMovementMethod.getInstance());
        String fbHref = "<a href=" + barData.getLink() + ">" + barData.getName() + "Facebook Site </a>";
        fbLink.setText(Html.fromHtml(fbHref));
        rating.setStepSize((float) 0.1);
        rating.setRating((float) 2.0);

        list.setAdapter(eventAdapter);

        fbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
          String theLink = barData.getLink();
           Log.d(TAG, theLink);
           Intent fbSite = newFacebookIntent(activity.getPackageManager(), "https://www.facebook.com/hurra.is");
           startActivity(fbSite);
            }
        });



        ////////////////////////

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setupMap(googleMap);
            }
        });

        ///////////////////////
    }

    private void setCoverImage(int id) {
        Log.d(TAG, "ID a cover image sem á að loada " + id);
        Bitmap bm = imageSaver.loadCoverPhoto(id);
        ImageView cover = (ImageView) findViewById(R.id.profileImage);
        cover.setImageBitmap(bm);
    }

    private void setupMap(GoogleMap map) {

        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);

        final LatLng origin = new LatLng(64.148661854835, -21.94014787674);
        final LatLng dest = new LatLng(64.148661854835, -21.94014787674+0.01);

        MapSetup mapSetup = new MapSetup(this, map);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(activity, MapActivity.class);

                i.putExtra("origin-lat", origin.latitude);
                i.putExtra("origin-long", origin.longitude);
                i.putExtra("dest-lat", dest.latitude);
                i.putExtra("dest-long", dest.longitude);

                startActivityForResult(i, 0);
            }
        });

        this.googleMap = mapSetup.setupMap(origin, dest);
    }

    /**
     * <p>Intent to open the official Facebook app. If the Facebook app is not installed then the
     * default web browser will be used.</p>
     *
     * <p>Example usage:</p>
     *
     * {@code newFacebookIntent(ctx.getPackageManager(), "https://www.facebook.com/JRummyApps");}
     *
     * @param pm
     *     The {@link PackageManager}. You can find this class through {@link
     *     Context#getPackageManager()}.
     * @param url
     *     The full URL to the Facebook page or profile.
     * @return An intent that will open the Facebook page/profile.
     */
    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            Log.d(TAG, "" + ignored);
        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
