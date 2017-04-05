package com.example.rvkdt.rvkapp.DataManagers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.example.rvkdt.rvkapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Steinar on 4/5/2017.
 */

public class ImageManager {

    Context ctx;

    public ImageManager(Context ctx){
        this.ctx = ctx;
    }

    // Checks if the image is in local storage. If not, tries
    // to download the image from URL and puts it in local storage.
    // Then puts the image into the ImageView.
    public void loadImage(int id, String url, ImageView iw){
        String name = "bar_image_" + id;
        ContextWrapper cw = new ContextWrapper(ctx.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        Log.d("stored", directory.getAbsolutePath());
        File myImageFile = new File(directory, name);
        Log.d("stored", myImageFile.getAbsolutePath());

        if(myImageFile.exists()){
            Log.d("stored", "Exists");
            Picasso.with(ctx).load(myImageFile).into(iw);
        } else {
            Log.d("stored", url);
            Log.d("stored", "Doesn't Exist");
            downloadImage(name, url, iw);
        }
    }

    public void downloadImage(String name, String url, ImageView iw){
        // Downloading an image from a URL and saving it to a local file.
        Picasso.with(ctx).load(url).into(iw);
        Picasso.with(ctx).load(url).into(picassoImageTarget(ctx.getApplicationContext(), "imageDir", name, iw));
    }

    public void deleteImage(String name) {
        ContextWrapper cw = new ContextWrapper(ctx.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, name);
        if (myImageFile.delete()) Log.d("snug", "image on the disk deleted successfully!");
    }

    public Target picassoImageTarget(final Context context, final String imageDir, final String imageName, final ImageView iw) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("stored", "image saved to >>>" + myImageFile.getAbsolutePath());
                    }
                }).start();

                // Put the bitmap into the image view.
                // Set þetta aftur inn seinna þegar ég laga "double image download" quick fixið
                //iw.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("stored", "could not load bitmap");
                iw.setImageResource(R.drawable.hurrapic);
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }
}
