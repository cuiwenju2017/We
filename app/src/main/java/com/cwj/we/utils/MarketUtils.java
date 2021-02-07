package com.cwj.we.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 跳转到应用市场/应用，判断是否安装某应用并进入某应用工具类
 */
public class MarketUtils {

    private static MarketUtils tools;
    private static final String schemaUrl = "market://details?id=";

    public static MarketUtils getTools() {
        if (null == tools) {
            tools = new MarketUtils();
        }
        return tools;
    }

    /***
     * 不指定包名，通过此应用包名跳转到手机自带应用市场搜索此应用
     * @param mContext
     */
    public void openMarket(Context mContext) {
        String packageName = mContext.getPackageName();//得到包名
        openMarket(mContext, packageName);
    }

    /**
     * 指定包名，跳转到手机自带应用市场搜索应用（需要传入应用包名）
     *
     * @param mContext
     * @param packageName
     */
    public void openMarket(Context mContext, String packageName) {
        try {
            String deviceBrand = getDeviceBrand();//获得手机厂商
            //根据厂商获取对应市场的包名
            String brandName = deviceBrand.toUpperCase();//大写
            if (TextUtils.isEmpty(brandName)) {
                ToastUtil.showTextToast(mContext, "没有读取到手机厂商~~");
            }
            String marketPackageName = getBrandName(brandName);
            //本机应用市场不在列表里,去尝试寻找百度应用市场和应用宝
            if (null == marketPackageName || "".equals(marketPackageName)) {
                //检测百度和应用宝是否在手机上安装,如果安装，则跳转到这两个市场的其中一个
                boolean isExit1 = isAppInstalled(mContext, PACKAGE_NAME.BAIDU_PACKAGE_NAME);
                if (isExit1) {
                    openMarket(mContext, packageName, PACKAGE_NAME.BAIDU_PACKAGE_NAME);
                }
                boolean isExit2 = isAppInstalled(mContext, PACKAGE_NAME.TENCENT_PACKAGE_NAME);
                if (isExit2) {
                    openMarket(mContext, packageName, PACKAGE_NAME.TENCENT_PACKAGE_NAME);
                }
            }
            openMarket(mContext, packageName, marketPackageName);
        } catch (ActivityNotFoundException anf) {
            ToastUtil.showTextToast(mContext, "要跳转的应用市场不存在,请先安装!");
        } catch (Exception e) {
            ToastUtil.showTextToast(mContext, "" + e.getMessage());
        }
    }

    /***
     * 打开指定应用市场搜索（需要传入应用包名，应用市场包名）
     * @param mContext
     * @param packageName
     * @param marketPackageName
     */
    public void openMarket(Context mContext, String packageName, String marketPackageName) {
        try {
            Uri uri = Uri.parse(schemaUrl + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage(marketPackageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException anf) {
            ToastUtil.showTextToast(mContext, "要跳转的应用市场不存在,请先安装!");
        } catch (Exception e) {
            ToastUtil.showTextToast(mContext, "" + e.getMessage());
        }
    }

    /**
     * 打开指定应用并打开相应页面（需要传入应用包名，类名）
     * 注意：打开相应应用的页面配置文件中一定要加如下配置，要不然会报Permission Denial: starting Intent 错误
     * <intent-filter>
     * <action android:name="android.intent.action.MAIN" />
     * </intent-filter>
     *
     * @param mContext
     * @param packagename
     * @param className
     */
    public void openInstalledApp(Context mContext, String packagename, String className) {
        try {
            // 一般我们知道了另一个应用的包名和MainActivity的名字之后便可以直接通过如下代码来启动：
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packagename, className);
            intent.setComponent(cn);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException anf) {
            ToastUtil.showTextToast(mContext, "要跳转的应用不存在,请先安装!");
        } catch (Exception e) {
            ToastUtil.showTextToast(mContext, "" + e.getMessage());
            Log.i("aaa", "openInstalledApp: " + e.getMessage());
        }
    }

    /**
     * 打开指定浏览器并传入相应连接进行搜索（需要传入浏览器包名，浏览器启动类名，打开连接）
     *
     * @param mContext
     * @param packagename
     * @param className
     * @param urlStr
     */
    public void openInstalledAppInURL(Context mContext, String packagename, String className, String urlStr) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(urlStr));
            intent.setClassName(packagename, className);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException anf) {
            ToastUtil.showTextToast(mContext, "要跳转的应用不存在,请先安装!");
        } catch (Exception e) {
            ToastUtil.showTextToast(mContext, "" + e.getMessage());
        }
    }

    /***
     * 检测是否安装某应用（需要传入应用包名）
     * @param mContext
     * @param packageName
     * @return
     */
    public boolean isAppInstalled(Context mContext, String packageName) {
        boolean installed = isInstalled(packageName, mContext);
        return installed;
    }

    /****
     * 检查APP是否安装成功
     * @param packageName
     * @param context
     * @return
     */
    private boolean isInstalled(@NonNull String packageName, Context context) {
        if ("".equals(packageName) || packageName.length() <= 0) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    private String getBrandName(String brandName) {
        if (BRAND.HUAWEI_BRAND.equals(brandName)) {
            //华为
            return PACKAGE_NAME.HUAWEI_PACKAGE_NAME;
        } else if (BRAND.OPPO_BRAND.equals(brandName)) {
            //oppo
            return PACKAGE_NAME.OPPO_PACKAGE_NAME;
        } else if (BRAND.VIVO_BRAND.equals(brandName)) {
            //vivo
            return PACKAGE_NAME.VIVO_PACKAGE_NAME;
        } else if (BRAND.XIAOMI_BRAND.equals(brandName)) {
            //小米
            return PACKAGE_NAME.XIAOMI_PACKAGE_NAME;
        } else if (BRAND.LENOVO_BRAND.equals(brandName)) {
            //联想
            return PACKAGE_NAME.LIANXIANG_PACKAGE_NAME;
        } else if (BRAND.QH360_BRAND.equals(brandName)) {
            //360
            return PACKAGE_NAME.QH360_PACKAGE_NAME;
        } else if (BRAND.MEIZU_BRAND.equals(brandName)) {
            //魅族
            return PACKAGE_NAME.MEIZU_PACKAGE_NAME;
        } else if (BRAND.HONOR_BRAND.equals(brandName)) {
            //华为
            return PACKAGE_NAME.HUAWEI_PACKAGE_NAME;
        } else if (BRAND.XIAOLAJIAO_BRAND.equals(brandName)) {
            //小辣椒
            return PACKAGE_NAME.ZHUOYI_PACKAGE_NAME;
        } else if (BRAND.ZTE_BRAND.equals(brandName)) {
            //zte
            return PACKAGE_NAME.ZTE_PACKAGE_NAME;
        } else if (BRAND.NIUBIA_BRAND.equals(brandName)) {
            //努比亚
            return PACKAGE_NAME.NIUBIA_PACKAGE_NAME;
        } else if (BRAND.ONE_PLUS_BRAND.equals(brandName)) {
            //OnePlus
            return PACKAGE_NAME.OPPO_PACKAGE_NAME;
        } else if (BRAND.MEITU_BRAND.equals(brandName)) {
            //美图
            return PACKAGE_NAME.MEITU_PACKAGE_NAME;
        } else if (BRAND.SONY_BRAND.equals(brandName)) {
            //索尼
            return PACKAGE_NAME.GOOGLE_PACKAGE_NAME;
        } else if (BRAND.GOOGLE_BRAND.equals(brandName)) {
            //google
            return PACKAGE_NAME.GOOGLE_PACKAGE_NAME;
        }
        return "";
    }

    /**
     * 获取手机厂商
     */
    private String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    private static class BRAND {
        public static final String HUAWEI_BRAND = "HUAWEI";//HUAWEI_PACKAGE_NAME
        public static final String HONOR_BRAND = "HONOR";//HUAWEI_PACKAGE_NAME
        public static final String OPPO_BRAND = "OPPO";//OPPO_PACKAGE_NAME
        public static final String MEIZU_BRAND = "MEIZU";//MEIZU_PACKAGE_NAME
        public static final String VIVO_BRAND = "VIVO";//VIVO_PACKAGE_NAME
        public static final String XIAOMI_BRAND = "XIAOMI";//XIAOMI_PACKAGE_NAME
        public static final String LENOVO_BRAND = "LENOVO";//LIANXIANG_PACKAGE_NAME //Lenovo
        public static final String ZTE_BRAND = "ZTE";//ZTE_PACKAGE_NAME
        public static final String XIAOLAJIAO_BRAND = "XIAOLAJIAO";//ZHUOYI_PACKAGE_NAME
        public static final String QH360_BRAND = "360";//QH360_PACKAGE_NAME
        public static final String NIUBIA_BRAND = "NUBIA";//NIUBIA_PACKAGE_NAME
        public static final String ONE_PLUS_BRAND = "ONEPLUS";//OPPO_PACKAGE_NAME
        public static final String MEITU_BRAND = "MEITU";//MEITU_PACKAGE_NAME
        public static final String SONY_BRAND = "SONY";//GOOGLE_PACKAGE_NAME
        public static final String GOOGLE_BRAND = "GOOGLE";//GOOGLE_PACKAGE_NAME

        public static final String HTC_BRAND = "HTC";//未知应用商店包名
        public static final String ZUK_BRAND = "ZUK";//未知应用商店包名
    }

    /** Redmi*/
    /**
     * 华为，oppo,vivo,小米，360，联想，魅族，安智，百度，阿里，应用宝，goog，豌豆荚，pp助手
     **/
    public static class PACKAGE_NAME {
        public static final String OPPO_PACKAGE_NAME = "com.oppo.market";//oppo
        public static final String VIVO_PACKAGE_NAME = "com.bbk.appstore";//vivo
        public static final String HUAWEI_PACKAGE_NAME = "com.huawei.appmarket";//华为
        public static final String QH360_PACKAGE_NAME = "com.qihoo.appstore";//360
        public static final String XIAOMI_PACKAGE_NAME = "com.xiaomi.market";//小米
        public static final String MEIZU_PACKAGE_NAME = "com.meizu.mstore";//，魅族
        public static final String LIANXIANG_PACKAGE_NAME = "com.lenovo.leos.appstore";//联想
        public static final String ZTE_PACKAGE_NAME = "zte.com.market";//zte
        public static final String ZHUOYI_PACKAGE_NAME = "com.zhuoyi.market";//卓易
        public static final String GOOGLE_PACKAGE_NAME = "com.android.vending";//google
        public static final String NIUBIA_PACKAGE_NAME = "com.nubia.neostore";//努比亚
        public static final String MEITU_PACKAGE_NAME = "com.android.mobile.appstore";//美图
        public static final String BAIDU_PACKAGE_NAME = "com.baidu.appsearch";//baidu
        public static final String TENCENT_PACKAGE_NAME = "com.tencent.android.qqdownloader";//应用宝
        public static final String PPZHUSHOU_PACKAGE_NAME = "com.pp.assistant";//pp助手
        public static final String ANZHI_PACKAGE_NAME = "com.goapk.market";//安智市场
        public static final String WANDOUJIA_PACKAGE_NAME = "com.wandoujia.phonenix2";//豌豆荚
        public static final String SUONI_PACKAGE_NAME = "com.android.vending";//索尼
    }
}
