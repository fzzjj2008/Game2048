package com.example.game2048.utils;

import com.example.game2048.MainApplication;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScaleUtils {

    public static int screenWidth;
    public static int screenHeight;
    public static float density;
    public static float scaledDensity;

    public static void init() {
        Context context = MainApplication.getContext();
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        density = dm.density;
        scaledDensity = dm.scaledDensity;
    }

    public static int scale(int size) {
        return (int) (size * density);
    }
    
    public static int scaleSp(int size) {
        return (int) (size * scaledDensity);
    }

}
