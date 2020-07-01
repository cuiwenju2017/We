package com.cwj.love_lhh.app;

import androidx.multidex.MultiDexApplication;

import cn.bmob.v3.Bmob;

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
