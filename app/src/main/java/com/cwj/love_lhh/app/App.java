package com.cwj.love_lhh.app;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDex;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;

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
        Bmob.initialize(this, "6db49e4b74caba7f1782c11002dfcfd7");
    }

    public static Context getContext() {
        return context;
    }
}
