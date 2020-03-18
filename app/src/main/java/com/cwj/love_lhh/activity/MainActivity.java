package com.cwj.love_lhh.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.baidu.autoupdatesdk.AppUpdateInfo;
import com.baidu.autoupdatesdk.AppUpdateInfoForInstall;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.CPCheckUpdateCallback;
import com.baidu.autoupdatesdk.CPUpdateDownloadCallback;
import com.cwj.love_lhh.R;
import com.cwj.love_lhh.utils.NotificationUtils;
import com.cwj.love_lhh.utils.TimeUtils;
import com.jaeger.library.StatusBarUtil;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.wv)
    WebView wv;
    @BindView(R.id.cl_view)
    CoordinatorLayout clView;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_in_harness_year)
    TextView tvInHarnessYear;
    @BindView(R.id.tv_get_married_year)
    TextView tvGetMarriedYear;
    @BindView(R.id.tv_more)
    TextView tvMore;
    SharedPreferences sprfMain;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_change_date)
    TextView tvChangeDate;
    private String togetherTime, getMarriedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        BDAutoUpdateSDK.cpUpdateCheck(this, new MyCPCheckUpdateCallback());
    }

    private void initView() {
        StatusBarUtil.setTranslucentForImageView(MainActivity.this, 0, clView);//沉浸状态栏
        // 设置WebView属性，能够执行Javascript脚本
        wv.getSettings().setJavaScriptEnabled(true);
        //语言设置防止加载乱码
        wv.getSettings().setDefaultTextEncodingName("GBK");
        // 即asserts文件夹下有一个color2.html
        wv.loadUrl("file:///android_asset/index.html");

        //取出上个页面保存的值（取数据）
        sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
        togetherTime = sprfMain.getString("togetherTime", "");
        getMarriedTime = sprfMain.getString("getMarriedTime", "");

        tvTime.setText(togetherTime + "我们在一起" + "\n" + getMarriedTime + "我们结婚");

        update();//显示数据

        //开始计时
        handler.postDelayed(runnable, 1000);
        //停止计时
//        handler.removeCallbacks(runnable);
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            update();//获取新数据
            handler.postDelayed(this, 1000); //n秒刷新一次
        }
    };

    private void update() {
        long nowTime, startTime, apartTime, remainderHour, remainderMinute, remainderSecond;
        int inHarnessYear, getMarriedYear, setTogetherTime, setGetMarriedTime;
        try {
            nowTime = TimeUtils.getTimeStame();//现在时间
            startTime = Long.parseLong(TimeUtils.dateToStamp(togetherTime));//起始时间
            apartTime = (nowTime - startTime) / 1000 / 60 / 60 / 24;//天数
            remainderHour = (nowTime - startTime) / 1000 / 60 / 60 % 24;//小时
            remainderMinute = (nowTime - startTime) / 1000 / 60 % 60;//分钟
            remainderSecond = (nowTime - startTime) / 1000 % 60;//秒
            tv.setText(apartTime + "天" + remainderHour + "小时" + remainderMinute + "分" + remainderSecond + "秒");

            setTogetherTime = Integer.parseInt(togetherTime.substring(0, 4));
            setGetMarriedTime = Integer.parseInt(getMarriedTime.substring(3, 7));

            inHarnessYear = Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) - setTogetherTime;//在一起年数
            getMarriedYear = Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) - setGetMarriedTime;//结婚年数
            tvInHarnessYear.setText("" + inHarnessYear);
            tvGetMarriedYear.setText("" + getMarriedYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static final int REQUEST_SEARCH = 100;

    @OnClick({R.id.tv_change_date, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_date://日期修改
                Intent intent = new Intent(this, SetTimeActivity.class);
                startActivityForResult(intent, REQUEST_SEARCH);
                break;
            case R.id.tv_more://小游戏
                startActivity(new Intent(this, MoreActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//判断是否返回成功
            if (requestCode == REQUEST_SEARCH) {//判断来自哪个Activity
                togetherTime = sprfMain.getString("togetherTime", "");
                getMarriedTime = sprfMain.getString("getMarriedTime", "");
                tvTime.setText(togetherTime + "我们在一起" + "\n" + getMarriedTime + "我们结婚");
            }
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
                BDAutoUpdateSDK.cpUpdateInstall(getApplicationContext(), infoForInstall.getInstallPath());
            } else if (info != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                                BDAutoUpdateSDK.cpUpdateDownloadByAs(MainActivity.this);
                                dialog.dismiss();
                            }
                        });
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BDAutoUpdateSDK.cpUpdateDownload(MainActivity.this, info, new UpdateDownloadCallback());
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
            BDAutoUpdateSDK.cpUpdateInstall(getApplicationContext(), apkPath);
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
