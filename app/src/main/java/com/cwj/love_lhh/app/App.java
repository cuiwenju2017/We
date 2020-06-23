package com.cwj.love_lhh.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.multidex.MultiDex;

import com.cwj.love_lhh.R;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import cn.bmob.v3.Bmob;

public class App extends BaseApplication {

    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        initSophix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "6db49e4b74caba7f1782c11002dfcfd7");
    }

    /**
     * 初始化Sophix
     * 需要在attachBaseContext方法里面调用
     * 并且要在super.attachBaseContext(base);和Multidex.install方法之后调用
     * 且在其他方法之前
     */
    private void initSophix() {
        String appVersion = "1.2.1";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SophixManager.getInstance().setContext(this)
                /*
                    设置版本号，版本号与控制台的版本号统一，才可以更新
                    这里我踩的坑，控制台上添加版本，是添加需要更新的版本，与版本升级没有关系
                 */
                .setAppVersion(appVersion)
                //<可选>用户自定义aes秘钥, 会对补丁包采用对称加密
                .setAesKey(null)
                /*
                    <可选> isEnabled默认为false, 是否调试模式, 调试模式下会输出日志以及不进行补丁签名校验.
                    线下调试此参数可以设置为true, 查看日志过滤TAG
                    正式发布必须改为false，否则存在安全风险
                 */
                .setEnableDebug(true)
                /*
                     <可选，推荐使用> 三个Secret分别对应AndroidManifest里面的三个，
                     可以不在AndroidManifest设置而是用此函数来设置Secret
                 */
                .setSecretMetaData(null, null, null)
                /*
                     <可选> 设置patch加载状态监听器,
                    该方法参数需要实现PatchLoadStatusListener接口
                 */
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        // 补丁加载回调通知
                        Log.e("sophix", "onLoad: 补丁加载回调通知  code = " + code);
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.e("sophix", "onLoad: 表明补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Log.e("sophix", "onLoad: 表明新补丁生效需要重启. 开发者可提示用户或者强制重启");
//                            SophixManager.getInstance().killProcessSafely();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.e("sophix", "onLoad: 其它错误信息, 查看PatchStatus类说明");
                        }
                    }
                }).initialize();
    }

    public static Context getContext() {
        return context;
    }
}
