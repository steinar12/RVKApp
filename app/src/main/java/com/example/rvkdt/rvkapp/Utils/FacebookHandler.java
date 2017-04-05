package com.example.rvkdt.rvkapp.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by Gunnthor on 4/5/2017.
 */

public class FacebookHandler {

    Activity ctx;

    public FacebookHandler(Activity ctx){
        this.ctx = ctx;
    }

    public Intent newFacebookIntent(String url, String type) {

        PackageManager pm = ctx.getPackageManager();
        Uri uri = Uri.parse(url);

        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://" + type + "/" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            Log.d(TAG, "" + ignored);
        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
