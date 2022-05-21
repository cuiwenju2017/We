package com.cwj.we.module.about;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.manager.DownloadManager;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.bean.LatestBean;
import com.cwj.we.module.activity.WebViewActivity;
import com.cwj.we.utils.LoadingDialog;
import com.cwj.we.utils.OneClickThree;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity<AboutPrensenter> implements AboutView {

    Unbinder unbinder;
    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;
    @BindView(R.id.rl_check_updates)
    RelativeLayout rlCheckUpdates;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;

    private LoadingDialog loadingDialog;
    private String string;
    private int REQUEST_SD = 200;
    private int REQUEST_SHARE = 202;
    private LatestBean updataBean;
    private BasePopupView basePopupView;
    private Intent intent;

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
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

    @OnClick({R.id.rl_check_updates, R.id.rl_share, R.id.rl_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_check_updates://检查更新
                if (!OneClickThree.isFastClick()) {
                    loadingDialog.show();
                    presenter.latest("5fc866b023389f0c69e23c24", "6570963ae9a308ca993393518f865887");
                }
                break;
            case R.id.rl_share://分享
                if (!OneClickThree.isFastClick()) {
                    basePopupView = new XPopup.Builder(this)
                            .asCustom(new CustomPopup(this))
                            .show();
                }
                break;
            case R.id.rl_feedback://用户反馈
                if (!OneClickThree.isFastClick()) {
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://support.qq.com/product/136399");
                    startActivity(intent);
                }
                break;
        }
    }

    class CustomPopup extends CenterPopupView {
        @BindView(R.id.btn_share)
        Button btnShare;
        @BindView(R.id.iv)
        ImageView iv;

        //注意：自定义弹窗本质是一个自定义View，但是只需重写一个参数的构造，其他的不要重写，所有的自定义弹窗都是这样。
        public CustomPopup(@NonNull Context context) {
            super(context);
        }

        // 返回自定义弹窗的布局
        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_popup_share;
        }

        // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
        @Override
        protected void onCreate() {
            super.onCreate();
            unbinder = ButterKnife.bind(this);
            btnShare.setOnClickListener(v -> {
                basePopupView.dismiss();
                share();//分享
            });

            iv.setOnLongClickListener(v -> {//长按识别二维码
                HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().setPhotoMode(true).create();
                HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(AboutActivity.this, BitmapFactory.decodeResource(getResources(), R.drawable.icon_qr_code), options);
                //处理扫码结果
                if (hmsScans != null && hmsScans.length > 0) {
                    //展示扫码结果
                    Intent intent = new Intent(AboutActivity.this, WebViewActivity.class);
                    intent.putExtra("name", "识别结果");
                    intent.putExtra("url", hmsScans[0].getOriginalValue());
                    startActivity(intent);
                }
                return false;
            });
        }

        // 设置最大宽度，看需要而定，
        @Override
        protected int getMaxWidth() {
            return super.getMaxWidth();
        }

        // 设置最大高度，看需要而定
        @Override
        protected int getMaxHeight() {
            return super.getMaxHeight();
        }

        // 设置自定义动画器，看需要而定
        @Override
        protected PopupAnimator getPopupAnimator() {
            return super.getPopupAnimator();
        }

        /**
         * 弹窗的宽度，用来动态设定当前弹窗的宽度，受getMaxWidth()限制
         *
         * @return
         */
        protected int getPopupWidth() {
            return 0;
        }

        /**
         * 弹窗的高度，用来动态设定当前弹窗的高度，受getMaxHeight()限制
         *
         * @return
         */
        protected int getPopupHeight() {
            return 0;
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
