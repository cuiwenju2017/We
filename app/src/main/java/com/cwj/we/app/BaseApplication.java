package com.cwj.we.app;

import androidx.multidex.MultiDexApplication;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
