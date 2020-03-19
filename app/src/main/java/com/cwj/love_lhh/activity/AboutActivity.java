package com.cwj.love_lhh.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.autoupdatesdk.AppUpdateInfo;
import com.baidu.autoupdatesdk.AppUpdateInfoForInstall;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.CPCheckUpdateCallback;
import com.baidu.autoupdatesdk.CPUpdateDownloadCallback;
import com.cwj.love_lhh.R;
import com.cwj.love_lhh.utils.LoadingDialog;
import com.cwj.love_lhh.utils.NotificationUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于
 */
public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;
    @BindView(R.id.rl_check_updates)
    RelativeLayout rlCheckUpdates;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvVersionNumber.setText("V" + getLocalVersionName(this));
        loadingDialog = new LoadingDialog(AboutActivity.this, "加载中...");
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    @OnClick({R.id.rl_check_updates, R.id.rl_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_check_updates://检查更新、
                loadingDialog.show();
                BDAutoUpdateSDK.cpUpdateCheck(this, new CPCheckUpdateCallback() {
                    @Override
                    public void onCheckUpdateCallback(AppUpdateInfo appUpdateInfo, AppUpdateInfoForInstall appUpdateInfoForInstall) {
                        if (appUpdateInfo == null && appUpdateInfoForInstall == null) {
                            loadingDialog.dismiss();
                            Toast.makeText(AboutActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
                        } else {
                            BDAutoUpdateSDK.cpUpdateCheck(AboutActivity.this, new MyCPCheckUpdateCallback());
                        }
                    }
                });
                break;
            case R.id.rl_share://分享
                Toast.makeText(this, "敬请期待...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class MyCPCheckUpdateCallback implements CPCheckUpdateCallback {

        @Override
        public void onCheckUpdateCallback(final AppUpdateInfo info, AppUpdateInfoForInstall infoForInstall) {
            if (infoForInstall != null && !TextUtils.isEmpty(infoForInstall.getInstallPath())) {
               /* tv.setText(tv.getText() + "\n install info: " + infoForInstall.getAppSName() + ", \nverion="
                        + infoForInstall.getAppVersionName() + ", \nchange log=" + infoForInstall.getAppChangeLog());
                tv.setText(tv.getText() + "\n we can install the apk file in: "
                        + infoForInstall.getInstallPath());*/
                BDAutoUpdateSDK.cpUpdateInstall(AboutActivity.this, infoForInstall.getInstallPath());
            } else if (info != null) {
                loadingDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                long size = info.getAppPathSize() > 0 ? info.getAppPathSize() : info.getAppSize();
                builder.setTitle("新版大小：" + byteToMb(size))
                        .setMessage(Html.fromHtml(info.getAppChangeLog()))
                        .setNeutralButton("普通升级", null)
                        .setPositiveButton("智能升级", null)
                        .setCancelable(info.getForceUpdate() != 1)
                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    return true;
                                }
                                return false;
                            }
                        });
                if (info.getForceUpdate() != 1) {
                    builder.setNegativeButton("暂不升级", null);
                }
                AlertDialog dialog = builder.show();

                //放在show()之后，不然有些属性是没有效果的，比如height和width
                Window dialogWindow = dialog.getWindow();
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                // 设置宽度
                p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
                p.gravity = Gravity.CENTER;//设置位置
                //p.alpha = 0.8f;//设置透明度
                dialogWindow.setAttributes(p);

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                BDAutoUpdateSDK.cpUpdateDownloadByAs(AboutActivity.this);
                                dialog.dismiss();
                            }
                        });
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BDAutoUpdateSDK.cpUpdateDownload(AboutActivity.this, info, new UpdateDownloadCallback());
                        dialog.dismiss();
                    }
                });
            } else {
//                tv.setText(tv.getText() + "\n no update.");
            }
        }

        private String byteToMb(long fileSize) {
            float size = ((float) fileSize) / (1024f * 1024f);
            return String.format("%.2fMB", size);
        }

    }

    private class UpdateDownloadCallback implements CPUpdateDownloadCallback {

        @Override
        public void onDownloadComplete(String apkPath) {
//            tv.setText(tv.getText() + "\n onDownloadComplete: " + apkPath);
            BDAutoUpdateSDK.cpUpdateInstall(AboutActivity.this, apkPath);
        }

        @Override
        public void onStart() {
//            tv.setText(tv.getText() + "\n Download onStart");
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onPercent(int percent, long rcvLen, long fileSize) {
//            tv.setText(tv.getText() + "\n Download onPercent: " + percent + "%");
            if (percent < 100) {
                NotificationUtils.showNotification("下载中...", "下载进度：" + percent + "%", 0, "", percent, 100);
            } else {
                NotificationUtils.cancleNotification(0);
            }
        }

        @Override
        public void onFail(Throwable error, String content) {
//            tv.setText(tv.getText() + "\n Download onFail: " + content);
        }

        @Override
        public void onStop() {
//            tv.setText(tv.getText() + "\n Download onStop");
        }

    }
}
