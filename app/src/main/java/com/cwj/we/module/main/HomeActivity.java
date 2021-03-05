package com.cwj.we.module.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.bean.LatestBean;
import com.cwj.we.module.fragment.GamesFragment;
import com.cwj.we.module.fragment.ToolFragment;
import com.cwj.we.module.fragment.UsFragment;
import com.cwj.we.utils.NotificationUtils;
import com.cwj.we.utils.PermissionUtils;
import com.cwj.we.utils.ToastUtil;
import com.cwj.we.view.FallObject;
import com.cwj.we.view.FallingView;
import com.cwj.we.view.TabView;
import com.maning.updatelibrary.InstallUtils;

import java.util.ArrayList;
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

    private static final int INDEX_US = 0;
    private static final int INDEX_GAMES = 1;
    private static final int INDEX_TOOL = 2;
    @BindView(R.id.fallingView)
    FallingView fallingView;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    private List<TabView> mTabViews = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private AlertDialog alertDialog;
    private String string;
    private int build;
    private int REQUEST_SD = 200;

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
        UsFragment usFragment = new UsFragment();
        GamesFragment gamesFragment = new GamesFragment();
        ToolFragment toolFragment = new ToolFragment();

        fragments.add(usFragment);
        fragments.add(gamesFragment);
        fragments.add(toolFragment);

        mTabViews.add(tabUs);
        mTabViews.add(tabGames);
        mTabViews.add(tabTool);

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

        //初始化一个雪花样式的fallObject
        FallObject.Builder builder = new FallObject.Builder(getResources().getDrawable(R.drawable.icon_huaban));
        FallObject fallObject = builder
                .setSpeed(3, true)
                .setSize(55, 55, true)
                .setWind(5, true, true)
                .build();
        fallingView.addFallObject(fallObject, 30);//添加下落物体对象

        //底部导航背景透明度设置0~255
        llBottom.getBackground().setAlpha(200);

        //通知用户开启通知
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        boolean isEnabled = notification.areNotificationsEnabled();
        if (!isEnabled) {
            openTongzhi();
        }

        if (alertDialog == null) {
            initCallBack();
            presenter.latest("5fc866b023389f0c69e23c24", "6570963ae9a308ca993393518f865887");
        }
    }

    private void openTongzhi() {
        //未打开通知
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("请在“通知”中打开通知权限以便观察应用更新进度")
                .setCancelable(false)
                .setNegativeButton("取消", (dialog, which) -> dialog.cancel())
                .setPositiveButton("去设置", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("android.provider.extra.APP_PACKAGE", HomeActivity.this.getPackageName());
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
                .create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    /*获取本地软件版本号​*/
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("TAG", "当前版本号：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    private InstallUtils.DownloadCallBack downloadCallBack;

    private void initCallBack() {
        downloadCallBack = new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(String path) {
                string = path;
                //去安装APK
                installApk(string);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onLoading(long total, long current) {
                //内部做了处理，onLoading 进度转回progress必须是+1，防止频率过快
                int progress = (int) (current * 100 / total);
                if (progress < 100) {
                    NotificationUtils.showNotification(HomeActivity.this, "下载中...", "下载进度：" + progress + "%", 0, "", progress, 100);

                    if (alertDialog == null) {
                        alertDialog = new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("提示")
                                .setMessage("应用下中，请稍等...")
                                .setCancelable(false)
                                .create();
                        alertDialog.show();
                        //放在show()之后，不然有些属性是没有效果的，比如height和width
                        Window dialogWindow = alertDialog.getWindow();
                        WindowManager m = getWindowManager();
                        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
                        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                        // 设置宽度
                        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
                        p.gravity = Gravity.CENTER;//设置位置
                        //p.alpha = 0.8f;//设置透明度
                        dialogWindow.setAttributes(p);
                    }
                } else {
                    NotificationUtils.cancleNotification(0);
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void cancle() {

            }
        };
    }

    private void installApk(String path) {
        InstallUtils.installAPK(this, path, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                //onSuccess：表示系统的安装界面被打开
                //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
//                ToastUtil.s(getString(R.string.installing_program));
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SD) {
            InstallUtils.with(this)
                    //必须-下载地址
                    .setApkUrl(string)
                    //非必须-下载回调
                    .setCallBack(downloadCallBack)
                    //开始下载
                    .startDownload();
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
    @OnClick({R.id.tab_tool, R.id.tab_games, R.id.tab_us})
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
        }
    }

    @Override
    public void latestData(LatestBean bean) {
        if (bean != null) {
            string = bean.getDirect_install_url();
            updata(bean);
        }
    }

    private void updata(LatestBean bean) {
        if (getLocalVersion(HomeActivity.this) < Integer.parseInt(bean.getBuild())) {
            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this)
                    .setTitle("检测到新版本V" + bean.getVersionShort())
                    .setMessage(bean.getChangelog())
                    .setCancelable(false)
                    .setNegativeButton("暂不升级", (dialog, which) -> dialog.cancel())
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.cancel();
                        //申请SD卡权限
                        if (!PermissionUtils.isGrantSDCardReadPermission(HomeActivity.this)) {
                            PermissionUtils.requestSDCardReadPermission(HomeActivity.this, REQUEST_SD);
                        } else {
                            InstallUtils.with(HomeActivity.this)
                                    //必须-下载地址
                                    .setApkUrl(string)
                                    //非必须-下载回调
                                    .setCallBack(downloadCallBack)
                                    //开始下载
                                    .startDownload();
                        }
                    })
                    .create();
            alertDialog.show();
            //放在show()之后，不然有些属性是没有效果的，比如height和width
            Window dialogWindow = alertDialog.getWindow();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            // 设置宽度
            p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
            p.gravity = Gravity.CENTER;//设置位置
            //p.alpha = 0.8f;//设置透明度
            dialogWindow.setAttributes(p);

            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
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
