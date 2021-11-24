package com.cwj.we.module.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手电筒
 */
public class ShoudiantongActivity extends BaseActivity {

    @BindView(R.id.bjiv)
    ImageView bjiv;
    @BindView(R.id.iv)
    ImageView iv;

    private boolean mBackKeyPressed = false;//记录是否有首次按键
    private CameraManager mCameraManager;
    private String mCameraId;
    private boolean islight = true;
    private NotificationChannel channel;
    private NotificationManager manager;
    private MyBroadCast receiver;
    private int SUCCESS = 201;
    private BasePopupView basePopupView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shoudiantong;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
        // 注册广播，并设置过滤条件
        receiver = new MyBroadCast();
        IntentFilter filter = new IntentFilter("a");
        registerReceiver(receiver, filter);

        showNotify();

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        //开启闪光灯
        turnOnFlashLight();
    }

    @OnClick({R.id.iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv:
                if (islight) {
                    bjiv.setImageResource(R.drawable.off);//关闭的图片
                    turnOffFlashLight();
                    islight = false;
                    if(manager != null){
                        manager.cancel(1);
                    }
                } else {
                    bjiv.setImageResource(R.drawable.on);//点亮的图片
                    turnOnFlashLight();
                    showNotify();
                    islight = true;
                }
                break;
            default:
                break;
        }
    }

    class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            bjiv.setImageResource(R.drawable.off);//关闭的图片
            turnOffFlashLight();
            if(manager != null){
                manager.cancel(1);
            }
            islight = false;
        }
    }

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
            } else {
                //自定义通知
                RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
                notificationLayout.setTextViewText(R.id.notification_title, "手电筒");
                Intent intent = new Intent("a");
                Intent intent2 = new Intent(this, ShoudiantongActivity.class);
                notificationLayout.setOnClickPendingIntent(R.id.notification_btn, PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification customNotification = new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                        .setCustomContentView(notificationLayout)//设置自定义View
                        .setDeleteIntent(pendingIntent)//设置侧滑删除通知
                        .setContentIntent(pendingIntent2)//设置可点击跳转
                        .build();
                manager.notify(1, customNotification);
            }
        }
    }

    private void openNotification() {
        //未打开通知
        basePopupView = new XPopup.Builder(this).asConfirm("提示", "打开通知更方便操作手电筒哦",
                () -> {
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            intent.putExtra(Settings.EXTRA_CHANNEL_ID, 1);
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package", getPackageName());
                        intent.putExtra("app_uid", getApplicationInfo().uid);
                        startActivity(intent);
                    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                    } else if (Build.VERSION.SDK_INT >= 15) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                    }
                    startActivityForResult(intent, SUCCESS);
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUCCESS) {
            showNotify();
        }
    }

    /**
     * 开启闪光灯的方法
     */
    private void turnOnFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭闪光的的方法
     */
    private void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 双击返回键退出程序
     */
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtil.showTextToast(this, "再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则清除第一次记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {//退出程序
            //关灯
            turnOffFlashLight();
            if(manager != null){
                manager.cancel(1);
            }
            islight = false;
            finish();
        }
    }
}