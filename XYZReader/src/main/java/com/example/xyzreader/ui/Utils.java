package com.example.xyzreader.ui;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by Nicola on 2016-10-28.
 */

public class Utils {
    public static int getScreenDpHeight(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = activity.getBaseContext().getResources().getDisplayMetrics().density;
        return (int) (outMetrics.heightPixels / density);
    }

    public static int getScreenDpWidth(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = activity.getBaseContext().getResources().getDisplayMetrics().density;
        return (int) (outMetrics.widthPixels / density);
    }
}
