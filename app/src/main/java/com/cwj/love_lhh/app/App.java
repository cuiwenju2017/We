package com.cwj.love_lhh.app;

import android.content.Context;

import androidx.multidex.MultiDex;

public class App extends BaseApplication {

    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
