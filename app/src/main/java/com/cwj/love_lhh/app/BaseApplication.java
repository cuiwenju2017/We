package com.cwj.love_lhh.app;

import androidx.multidex.MultiDexApplication;

import com.taobao.sophix.SophixManager;

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
         /*
            queryAndLoadNewPatch不可放在attachBaseContext 中，
            否则无网络权限，建议放在后面任意时刻，如onCreate中
          */
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
