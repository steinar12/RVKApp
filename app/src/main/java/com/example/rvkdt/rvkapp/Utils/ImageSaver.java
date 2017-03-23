package com.example.rvkdt.rvkapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by Gunnthor on 3/22/2017.
 */

public class ImageSaver {

    private String directoryName = "images";
    private String fileName = "image.png";
    private Context context;
    private boolean external;
    private Target loadtarget;
    private int counter = 0;
    private static final String TAG = ImageSaver.class.getSimpleName();


    public ImageSaver(Context context) {
        this.context = context;
    }

    public ImageSaver setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ImageSaver setExternal(boolean external) {
        this.external = external;
        return this;
    }

    public ImageSaver setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
        return this;
    }

    public void save(Bitmap bitmapImage) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile());
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private File createFile() {
        File directory;
        if(external){
            directory = getAlbumStorageDir(directoryName);
        }
        else {
            directory = context.getDir(directoryName, Context.MODE_PRIVATE);
        }

        return new File(directory, fileName);
    }

    private File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("ImageSaver", "Directory not created");
        }
        return file;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public Bitmap load() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    ///PICASSO FIFF
    // make sure to set Target as strong reference
    public void loadBitmap(String url, final int id) {

        if(url.equals("null")) {
            url = "https://scontent-arn2-1.xx.fbcdn.net/v/t1.0-9/10264956_10152789682559358_372568786516885638_n.jpg?oh=9aad9dd840c3a9b1162da986ab3eecdf&oe=595DF1BD";
            Log.d("ImageSaver_>loadBitMap", "fake PIC");
        }
        Log.d("ImageSaver_>loadBitMap", "urlið:" + url);
        if (loadtarget == null) loadtarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // do something with the Bitmap
                Log.d("OnBitmapLoaded, ID: ", "id" + id);
                handleLoadedBitmap(bitmap, id);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(context).load(url).into(loadtarget);
    }

    public void handleLoadedBitmap(Bitmap image, int id) {
        // do something here
        Log.d(TAG, "Counter:" + id);
        new ImageSaver(context).
                setFileName(counter + ".png").
                setDirectoryName("images").
                save(image);
        counter++;
    }
    //Fetch image from internet with URL
    public void downloadImage(String image, int id) {
        loadBitmap(image, id);
    }

    public Bitmap loadTest() {
        Log.d(TAG, "LoadTest > counter:" + counter);

        counter++;
        Bitmap bitmap = new ImageSaver(context).
                setFileName(counter + ".png").
                setDirectoryName("images").
                load();
        return bitmap;
    }
}
