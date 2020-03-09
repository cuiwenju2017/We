package com.cwj.love_lhh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cwj.love_lhh.R;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreActivity extends AppCompatActivity {

    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;
    @BindView(R.id.rl_baoweixingqiu)
    RelativeLayout rlBaoweixingqiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
        tvVersionNumber.setText("V" + getLocalVersionName(this));
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

    @OnClick(R.id.rl_baoweixingqiu)
    public void onViewClicked() {
        Intent intent = new Intent(this, WebViewActivity.class);
//        intent.putExtra("url", "file:///android_asset/gunqiu.html");
        intent.putExtra("url", "http://xcdn.php.cn/js/html5/H5%203D%E6%BB%9A%E7%90%83%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=8602777ddf8e8801fcaac1e826ae4c48&timestamp=1583716386");
        startActivity(intent);
    }
}
