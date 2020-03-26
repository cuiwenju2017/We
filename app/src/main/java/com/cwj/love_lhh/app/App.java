package com.cwj.love_lhh.app;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDex;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class App extends BaseApplication {

    private static Context context;
    private static App mInstance;
    private int speedGrade = 1;
    private List<Activity> activityList = new LinkedList<Activity>();

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


    public static App getInstance() {
        if (mInstance == null) {
            mInstance = new App();
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
