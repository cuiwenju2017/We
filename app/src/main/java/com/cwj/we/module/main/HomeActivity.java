package com.cwj.we.module.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.manager.DownloadManager;
import com.bumptech.glide.Glide;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.bean.EventBG;
import com.cwj.we.bean.LatestBean;
import com.cwj.we.module.activity.WebViewActivity;
import com.cwj.we.module.fragment.GamesFragment;
import com.cwj.we.module.fragment.ToolFragment;
import com.cwj.we.module.fragment.UsFragment;
import com.cwj.we.module.fragment.YingshiFragment;
import com.cwj.we.utils.ReaderUtils;
import com.cwj.we.utils.ToastUtil;
import com.cwj.we.view.TabView;
import com.gyf.immersionbar.ImmersionBar;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomePrensenter> implements HomeView {

    @BindView(R.id.tab_tool)
    TabView tabTool;
    @BindView(R.id.tab_games)
    TabView tabGames;
    @BindView(R.id.tab_us)
    TabView tabUs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tab_yingshi)
    TabView tabYingshi;

    private List<TabView> mTabViews = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private String string;
    private int REQUEST_SD = 200;
    private LatestBean updataBean;
    private static final int INDEX_US = 0;
    private static final int INDEX_GAMES = 1;
    private static final int INDEX_TOOL = 2;
    private static final int INDEX_YINGSHI = 3;
    private BasePopupView basePopupView;
    private SharedPreferences sprfMain;
    private NotificationManager manager;
    private NotificationChannel channel;

    @Override
    protected HomePrensenter createPresenter() {
        return new HomePrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        ImmersionBar.with(this)
                .statusBarDarkFont(false) //状态栏字体是深色，不写默认为亮色
                .titleBar(viewpager)//解决状态栏和布局重叠问题，任选其一
                .init();

        sprfMain = this.getSharedPreferences("counter", Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(sprfMain.getString("path", ""))) {
            Glide.with(HomeActivity.this).load(R.drawable.we_bg).into(ivBg);
        } else {
            if (sprfMain.getString("path", "").startsWith("content://")) {
                Uri uri = Uri.parse(sprfMain.getString("path", ""));
                File mImageFile = new File(ReaderUtils.getPath(this, uri));
                Uri mImageUri = FileProvider.getUriForFile(HomeActivity.this,
                        "com.cwj.we.fileprovider", mImageFile);
                Glide.with(HomeActivity.this).load(mImageUri).into(ivBg);

                setBgImageByResource(mImageUri);
            } else {
                Glide.with(this).load(Uri.fromFile(new File(sprfMain.getString("path", "")))).into(ivBg);

                setBgImageByResource(Uri.fromFile(new File(sprfMain.getString("path", ""))));
            }
        }

        UsFragment usFragment = new UsFragment();
        GamesFragment gamesFragment = new GamesFragment();
        ToolFragment toolFragment = new ToolFragment();
        YingshiFragment yingshiFragment = new YingshiFragment();

        fragments.add(usFragment);
        fragments.add(gamesFragment);
        fragments.add(toolFragment);
        fragments.add(yingshiFragment);

        mTabViews.add(tabUs);
        mTabViews.add(tabGames);
        mTabViews.add(tabTool);
        mTabViews.add(tabYingshi);

        viewpager.setOffscreenPageLimit(fragments.size() - 1);
        viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 左边View进行动画
                mTabViews.get(position).setXPercentage(1 - positionOffset);
                // 如果positionOffset非0，那么就代表右边的View可见，也就说明需要对右边的View进行动画
                if (positionOffset > 0) {
                    mTabViews.get(position + 1).setXPercentage(positionOffset);
                }
            }
        });

        //底部导航背景透明度设置0~255
        llBottom.getBackground().setAlpha(100);

        showNotify();

        if (basePopupView == null) {
            presenter.latest("5fc866b023389f0c69e23c24", "6570963ae9a308ca993393518f865887");
        }
    }

    //----------状态栏字体动态变色start----------//
    private void setBgImageByResource(int imageResource) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResource);
        ivBg.setImageBitmap(bitmap);
        detectBitmapColor(bitmap);
    }

    private void setBgImageByResource(Uri mImageUri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(mImageUri));
            ivBg.setImageBitmap(bitmap);
            detectBitmapColor(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final void detectBitmapColor(Bitmap bitmap) {
        int colorCount = 5;
        int left = 0;
        int top = 0;
        int right = this.getScreenWidth();
        int bottom = this.getStatusBarHeight();
        Palette.from(bitmap)
                .maximumColorCount(colorCount)
                .setRegion(left, top, right, bottom)
                .generate(palette -> {
                    if (palette != null) {
                        Palette.Swatch mostPopularSwatch = null;
                        Iterator iterator = palette.getSwatches().iterator();
                        while (true) {
                            Palette.Swatch swatch;
                            do {
                                if (!iterator.hasNext()) {
                                    if (mostPopularSwatch != null) {
                                        double luminance = ColorUtils.calculateLuminance(mostPopularSwatch.getRgb());
                                        if (luminance < 0.5D) {
                                            setDarkStatusBar();
                                        } else {
                                            setLightStatusBar();
                                        }
                                        return;
                                    } else {
                                        return;
                                    }
                                }
                                swatch = (Palette.Swatch) iterator.next();
                                if (mostPopularSwatch == null) {
                                    break;
                                }
                            } while (swatch.getPopulation() <= mostPopularSwatch.getPopulation());
                            mostPopularSwatch = swatch;
                        }
                    }
                });
    }

    private final void setLightStatusBar() {
        int flags = getWindow().getDecorView().getSystemUiVisibility();
        getWindow().getDecorView().setSystemUiVisibility(flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private final void setDarkStatusBar() {
        int flags = getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        getWindow().getDecorView().setSystemUiVisibility(flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private final int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private final int getStatusBarHeight() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    //----------状态栏字体动态变色end----------//

    private void showNotify() {
        //通知用户开启通知
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        boolean isEnabled = notification.areNotificationsEnabled();
        if (!isEnabled) {
            openNotification();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // 通知渠道的id。
            String id = "1";
            // 用户可以看到的通知渠道的名字。
            CharSequence name = getResources().getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 最后在notificationmanager中创建该通知渠道。
            manager.createNotificationChannel(mChannel);
            channel = manager.getNotificationChannel(id);
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                openNotification();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBG eventBG) {
        switch (eventBG.getType()) {
            case "EVENT_CZ_BG":
                Glide.with(HomeActivity.this).load(R.drawable.we_bg).into(ivBg);

                setBgImageByResource(R.drawable.we_bg);
                break;
            case "EVENT_SZ_BG":
                if (eventBG.getUserIconPath().startsWith("content://")) {
                    Uri uri = Uri.parse(eventBG.getUserIconPath());
                    File mImageFile = new File(ReaderUtils.getPath(this, uri));
                    Uri mImageUri = FileProvider.getUriForFile(HomeActivity.this,
                            "com.cwj.we.fileprovider", mImageFile);
                    Glide.with(this).load(mImageUri).into(ivBg);

                    setBgImageByResource(mImageUri);
                } else {
                    Glide.with(this).load(Uri.fromFile(new File(eventBG.getUserIconPath()))).into(ivBg);

                    setBgImageByResource(Uri.fromFile(new File(eventBG.getUserIconPath())));
                }
                break;
        }
    }

    private void openNotification() {
        //未打开通知
        basePopupView = new XPopup.Builder(this).asConfirm("提示", "请在“通知”中打开通知权限以便观察应用更新进度",
                () -> {
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("android.provider.extra.APP_PACKAGE", HomeActivity.this.getPackageName());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            intent.putExtra(Settings.EXTRA_CHANNEL_ID, 1);
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package", HomeActivity.this.getPackageName());
                        intent.putExtra("app_uid", HomeActivity.this.getApplicationInfo().uid);
                        startActivity(intent);
                    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + HomeActivity.this.getPackageName()));
                    } else if (Build.VERSION.SDK_INT >= 15) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", HomeActivity.this.getPackageName(), null));
                    }
                    startActivity(intent);
                })
                .show();
    }

    /*获取本地软件版本号​*/
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SD) {
            updata();
        }

        //判断“requestCode”是否为申请权限时设置请求码CAMERA_REQ_CODE，然后校验权限开启状态
        if (requestCode == CAMERA_REQ_CODE && grantResults.length == PERMISSIONS_LENGTH && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            doCode();
        }
    }

    private void doCode() {
        //调用扫码接口，构建扫码能力
        HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().create();
        ScanUtil.startScan(this, REQUEST_CODE_SCAN_ONE, options);
    }

    private int REQUEST_CODE_SCAN_ONE = 202;
    //实现“onRequestPermissionsResult”函数接收校验权限结果
    final int PERMISSIONS_LENGTH = 2;
    private int CAMERA_REQ_CODE = 201;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            //导入图片扫描返回结果
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj != null) {
                //展示解码结果
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("name", "扫码结果");
                intent.putExtra("url", obj.getOriginalValue());
                startActivity(intent);
            }
        }
    }

    @Override
    public void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateCurrentTab(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (index == i) {
                mTabViews.get(i).setXPercentage(1);
            } else {
                mTabViews.get(i).setXPercentage(0);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.tab_tool, R.id.tab_games, R.id.tab_us, R.id.tab_yingshi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_tool:
                viewpager.setCurrentItem(INDEX_TOOL, false);
                updateCurrentTab(INDEX_TOOL);
                break;
            case R.id.tab_games:
                viewpager.setCurrentItem(INDEX_GAMES, false);
                updateCurrentTab(INDEX_GAMES);
                break;
            case R.id.tab_us:
                viewpager.setCurrentItem(INDEX_US, false);
                updateCurrentTab(INDEX_US);
                break;
            case R.id.tab_yingshi:
                viewpager.setCurrentItem(INDEX_YINGSHI, false);
                updateCurrentTab(INDEX_YINGSHI);
                break;
            default:
                break;
        }
    }

    @Override
    public void latestData(LatestBean bean) {
        if (bean != null) {
            string = bean.getDirect_install_url();
            updataBean = bean;
            updata();
        }
    }

    private void updata() {
        if (getLocalVersion(HomeActivity.this) < Integer.parseInt(updataBean.getBuild())) {
            UpdateConfiguration configuration = new UpdateConfiguration();
            configuration.setForcedUpgrade(false);//强制更新
            DownloadManager manager = DownloadManager.getInstance(this);
            manager.setApkName("we.apk")
                    .setApkSize(updataBean.getBinary().getFsize() / 1024 / 1024 + "." + updataBean.getBinary().getFsize() / 1024 % 1024)
                    .setApkUrl(string)
                    .setSmallIcon(R.drawable.logo)
                    //非必须参数
                    .setConfiguration(configuration)
                    //设置了此参数，那么会自动判断是否需要更新弹出提示框
                    .setApkVersionCode(Integer.parseInt(updataBean.getBuild()))
                    .setApkVersionName(updataBean.getVersionShort())
                    .setApkDescription(updataBean.getChangelog())
                    .download();
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showTextToast(this, msg);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> frags;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> frags) {
            super(fm);
            this.frags = frags;
        }

        @Override
        public Fragment getItem(int i) {
            return frags.get(i);
        }

        @Override
        public int getCount() {
            return frags.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 再按一次退出程序
     */
    private long currentBackPressedTime = 0;
    private static int BACK_PRESSED_INTERVAL = 2000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtil.showTextToast(this, "再按一次退出程序");
                return true;
            } else {//退出程序
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return false;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
