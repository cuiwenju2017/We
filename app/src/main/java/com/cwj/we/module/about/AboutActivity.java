package com.cwj.we.module.about;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.bean.LatestBean;
import com.cwj.we.bean.User;
import com.cwj.we.module.activity.ChangePasswordActivity;
import com.cwj.we.module.activity.LoginActivity;
import com.cwj.we.module.activity.WebViewActivity;
import com.cwj.we.utils.ActivityCollector;
import com.cwj.we.utils.LoadingDialog;
import com.cwj.we.utils.NotificationUtils;
import com.cwj.we.utils.PermissionUtils;
import com.cwj.we.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.maning.updatelibrary.InstallUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity<AboutPrensenter> implements AboutView {

    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;
    @BindView(R.id.rl_check_updates)
    RelativeLayout rlCheckUpdates;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.rl_change_password)
    RelativeLayout rlChangePassword;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_username)
    RelativeLayout rlUsername;

    private LoadingDialog loadingDialog;
    SharedPreferences sprfMain;
    private String username;
    private String string;
    private int REQUEST_SD = 200;
    private int REQUEST_SHARE = 202;
    private int INSTALL_PERMISS_CODE = 203;

    @Override
    protected AboutPrensenter createPresenter() {
        return new AboutPrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
        tvVersionNumber.setText("V" + getLocalVersionName(this));
        loadingDialog = new LoadingDialog(AboutActivity.this, "");
        sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
        username = sprfMain.getString("username", "");
        tvUsername.setText(username);
    }

    @Override
    public void initData() {

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

    private void share() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_SHARE);
        } else {
            shareImg(BitmapFactory.decodeResource(getResources(), R.drawable.icon_qr_code));
        }
    }

    /**
     * 分享图片(直接将bitamp转换为Uri)
     *
     * @param bitmap
     */
    private void shareImg(Bitmap bitmap) {
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent = Intent.createChooser(intent, getResources().getString(R.string.app_name));
        startActivity(intent);
    }

    private InstallUtils.DownloadCallBack downloadCallBack;
    private AlertDialog alertDialog;

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
                    NotificationUtils.showNotification(AboutActivity.this, "下载中...", "下载进度：" + progress + "%", 0, "", progress, 100);

                    if (alertDialog == null) {
                        alertDialog = new AlertDialog.Builder(AboutActivity.this)
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
        } else if (requestCode == REQUEST_SHARE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                shareImg(BitmapFactory.decodeResource(getResources(), R.drawable.icon_qr_code));
            } else {
                Toast.makeText(this, "你拒绝了权限申请，可能无法进行下面的操作哦", Toast.LENGTH_SHORT).show();
            }
        }
    }

    SharedPreferences.Editor editorMain;

    /**
     * 更新用户操作并同步更新本地的用户信息
     */
    private void updateUser(String text) {
        final User user = BmobUser.getCurrentUser(User.class);
        user.setUsername(text);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                loadingDialog.dismiss();
                if (e == null) {
                    popupView.dismiss();//关闭弹窗
                    ToastUtil.showTextToast(AboutActivity.this, "更新用户信息成功");
                    sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
                    editorMain = sprfMain.edit();
                    editorMain.putString("username", text);
                    editorMain.commit();
                    tvUsername.setText(text);
                } else {//202:用户名已存在  301：用户不能为空
                    ToastUtil.showTextToast(AboutActivity.this, "用户名已存在,换个试试");
                }
            }
        });
    }

    private BasePopupView popupView;

    @OnClick({R.id.rl_username, R.id.rl_check_updates, R.id.rl_share, R.id.rl_feedback, R.id.rl_change_password, R.id.rl_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_username://修改用户名
                popupView = new XPopup.Builder(this)
                        .autoDismiss(false) // 操作完毕后是否自动关闭弹窗，默认为true；比如点击ConfirmPopup的确认按钮，默认自动关闭；如果为false，则不会关闭
                        .asInputConfirm("修改用户名", "请输入新的用户名",
                                text -> {
                                    if (TextUtils.isEmpty(text)) {
                                        ToastUtil.showTextToast(AboutActivity.this, "用户名不能为空");
                                    } else {
                                        loadingDialog.show();
                                        updateUser(text);
                                    }
                                })
                        .show();
                break;
            case R.id.rl_check_updates://检查更新
                loadingDialog.show();
                initCallBack();
                presenter.latest("5fc866b023389f0c69e23c24", "6570963ae9a308ca993393518f865887");
                break;
            case R.id.rl_share://分享
                share();
                break;
            case R.id.rl_feedback://用户反馈
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", "https://support.qq.com/product/136399");
                startActivity(intent);
                break;
            case R.id.rl_change_password://修改密码
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.rl_logout://退出登录
                new XPopup.Builder(this).asConfirm("提示", "确定退出登录吗？",
                        () -> {
                            BmobUser.logOut();//退出登录，同时清除缓存用户对象。
                            startActivity(new Intent(AboutActivity.this, LoginActivity.class));
                            finish();
                            //结束之前所有的Activity
                            ActivityCollector.finishall();
                        })
                        .show();
                break;
        }
    }

    @Override
    public void latestData(LatestBean bean) {
        loadingDialog.dismiss();
        if (bean != null) {
            string = bean.getDirect_install_url();
            updata(bean);
        }
    }

    public void setInstallPermission() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先判断是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {
                AlertDialog alertDialog = new AlertDialog.Builder(AboutActivity.this)
                        .setTitle("安装权限")
                        .setMessage("需要打开允许来自此来源，请去设置中开启此权限")
                        .setCancelable(false)
                        .setNegativeButton("取消", (dialog, which) -> dialog.cancel())
                        .setPositiveButton("确定", (dialog, which) -> {
                            dialog.cancel();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                //此方法需要API>=26才能使用
                                toInstallPermissionSettingIntent();
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
                alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                return;
            } else {
                if (!PermissionUtils.isGrantSDCardReadPermission(AboutActivity.this)) {
                    PermissionUtils.requestSDCardReadPermission(AboutActivity.this, REQUEST_SD);
                } else {
                    InstallUtils.with(AboutActivity.this)
                            //必须-下载地址
                            .setApkUrl(string)
                            //非必须-下载回调
                            .setCallBack(downloadCallBack)
                            //开始下载
                            .startDownload();
                }
            }
        }
    }

    /**
     * 开启安装未知来源权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toInstallPermissionSettingIntent() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, INSTALL_PERMISS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INSTALL_PERMISS_CODE) {
            if (!PermissionUtils.isGrantSDCardReadPermission(AboutActivity.this)) {
                PermissionUtils.requestSDCardReadPermission(AboutActivity.this, REQUEST_SD);
            } else {
                InstallUtils.with(AboutActivity.this)
                        //必须-下载地址
                        .setApkUrl(string)
                        //非必须-下载回调
                        .setCallBack(downloadCallBack)
                        //开始下载
                        .startDownload();
            }
        }
    }

    private void updata(LatestBean bean) {
        if (getLocalVersion(AboutActivity.this) < Integer.parseInt(bean.getBuild())) {
            AlertDialog alertDialog = new AlertDialog.Builder(AboutActivity.this)
                    .setTitle("检测到新版本V" + bean.getVersionShort())
                    .setMessage(bean.getChangelog())
                    .setCancelable(false)
                    .setNegativeButton("暂不升级", (dialog, which) -> dialog.cancel())
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.cancel();

                        setInstallPermission();

                        //申请SD卡权限
                       /* if (!PermissionUtils.isGrantSDCardReadPermission(AboutActivity.this)) {
                            PermissionUtils.requestSDCardReadPermission(AboutActivity.this, REQUEST_SD);
                        } else {
                            InstallUtils.with(AboutActivity.this)
                                    //必须-下载地址
                                    .setApkUrl(string)
                                    //非必须-下载回调
                                    .setCallBack(downloadCallBack)
                                    //开始下载
                                    .startDownload();
                        }*/
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
            alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            ToastUtil.showTextToast(AboutActivity.this, "已是最新版本");
        }
    }

    @Override
    public void onError(String msg) {
        loadingDialog.dismiss();
        ToastUtil.showTextToast(this, msg);
    }
}
