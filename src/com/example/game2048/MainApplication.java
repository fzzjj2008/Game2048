package com.example.game2048;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

public class MainApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        Global.font = Typeface.createFromAsset(context.getAssets(), "ClearSans-Bold.ttf");
    }

    public static Context getContext() {
        return context;
    }
}
