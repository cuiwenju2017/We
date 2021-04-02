package com.cwj.we.module.about;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cwj.we.BuildConfig;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.bean.LatestBean;
import com.cwj.we.bean.User;
import com.cwj.we.module.activity.ChangePasswordActivity;
import com.cwj.we.module.activity.LoginActivity;
import com.cwj.we.module.activity.WebViewActivity;
import com.cwj.we.utils.ActivityCollector;
import com.cwj.we.utils.LoadingDialog;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.ycbjie.ycupdatelib.AppUpdateUtils;
import com.ycbjie.ycupdatelib.PermissionUtils;
import com.ycbjie.ycupdatelib.UpdateFragment;

import java.io.File;

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
    private LatestBean updataBean;
    //这个是你的包名
    private static final String apkName = "yilu";
    private static final String[] mPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

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
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SD) {
            updata();
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
            updataBean = bean;
            updata();
        }
    }

    private void updata() {
        if (getLocalVersion(AboutActivity.this) < Integer.parseInt(updataBean.getBuild())) {
            PermissionUtils.init(this);
            boolean granted = PermissionUtils.isGranted(mPermission);
            if (!granted) {
                PermissionUtils permission = PermissionUtils.permission(mPermission);
                permission.callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        presenter.latest("5fc866b023389f0c69e23c24", "6570963ae9a308ca993393518f865887");
                    }

                    @Override
                    public void onDenied() {
                        PermissionUtils.openAppSettings();
                        ToastUtil.showTextToast(AboutActivity.this, "请允许存储权限");
                    }
                });
                permission.request();
            } else {
                //设置自定义下载文件路径
                AppUpdateUtils.APP_UPDATE_DOWN_APK_PATH = "apk" + File.separator + "downApk";
                UpdateFragment.showFragment(this, false, string, apkName, updataBean.getChangelog(), BuildConfig.APPLICATION_ID, null);
            }
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
