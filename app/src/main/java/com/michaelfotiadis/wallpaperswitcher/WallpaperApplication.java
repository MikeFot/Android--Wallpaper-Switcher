package com.michaelfotiadis.wallpaperswitcher;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

public class WallpaperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
