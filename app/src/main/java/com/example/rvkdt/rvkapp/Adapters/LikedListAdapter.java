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
import com.example.rvkdt.rvkapp.deleteLikedCallback;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.Picasso;

import java.io.File;


/**
 * Created by Danni on 09/03/2017.
 */

public class LikedListAdapter extends ArrayAdapter<Bar> implements deleteLikedCallback{

    private final Activity ctx;
    private Bar[] bars;
    private final Bar testBar;
    private final BarStorage barStorage;
    private deleteLikedCallback callback = null;

    // The async image loader
    ImageLoader imageLoader;

    // Temporary shit
    private int img_num = 0;

    @Override
    public  void onDelete(){

    }



    public LikedListAdapter(Activity context, Bar[] bars, final deleteLikedCallback cb){
        super(context, R.layout.liked_bar, bars);
        callback=cb;
        Log.d("snug","CALLED CONSTRUCTOR WITH THIS BARS LENGTH: " + bars.length);
        this.ctx = context;
        this.bars = bars;
        this.barStorage = ((BarStorage) context.getApplicationContext());
        this.testBar = barStorage.pop();
        /*this.imageLoader = ImageLoader.getInstance();

        ImageLoaderConfiguration config = getImageLoaderConfiguration();
        this.imageLoader.init(config);*/
    }

    public void setBars(Bar[] bars){
        this.bars = bars;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        //Log.d("snug", "NUMBER: " + position);
        LayoutInflater inflater = ctx.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.liked_bar2, null, true);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, ProfileActivity.class);
                int id = bars[position].getId();
                i.putExtra("bar_id",id);
                i.putExtra("liked",true);
                Log.d("id",String.valueOf(id));
                ctx.startActivityForResult(i, 0);
                ctx.overridePendingTransition(0, 0);
            }
        });

        ImageView delete_button = (ImageView) rowView.findViewById(R.id.delete_button);
        delete_button.setTag(position);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("string","ping");
                int barId = (int) v.getTag();
                int id = bars[position].getId();
                Log.d("string", String.valueOf(barId));
                Log.d("string", String.valueOf(id));
                barStorage.removeLiked(id);
                callback.onDelete();

            }
        });

        final ImageView image_view = (ImageView) rowView.findViewById(R.id.image);
        final RelativeLayout list_item_container = (RelativeLayout) rowView.findViewById(R.id.list_item_container);
        TextView bar_name_view = (TextView) rowView.findViewById(R.id.name);

        String name = bars[position].getName();
        bar_name_view.setText(name);



        return rowView;
    }

    @Override
    public int getCount() {
        return bars.length;
    }

    // temporary shit
    private int getImageID() {

        img_num++;
        if(img_num > 4) img_num = 0;

        switch(img_num){
            case 0:
                return R.drawable.hurrapic;
            case 1:
                return R.drawable.hurrapic;
            case 2:
                return R.drawable.hurrapic;
            case 3:
                return R.drawable.hurrapic;
            case 4:
                return R.drawable.hurrapic;
            default:
                return R.drawable.hurrapic;
        }
    }

    private DisplayImageOptions getImageLoaderOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .build();

        return options;
    }

    private ImageLoaderConfiguration getImageLoaderConfiguration() {
        File cacheDir = StorageUtils.getCacheDirectory(this.ctx);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.ctx)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this.ctx)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        return config;
    }
}
