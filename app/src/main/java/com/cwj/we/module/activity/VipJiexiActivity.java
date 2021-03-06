package com.cwj.we.module.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.utils.MarketUtils;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 视频解析
 */
public class VipJiexiActivity extends BaseActivity {

    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.btn_jiexi)
    Button btnJiexi;
    private Intent intent;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_jiexi;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
    }

    private String vip1 = "https://www.administratorw.com/index/qqvod.php?url=";
    private String vip2 = "https://www.administratorw.com/video.php?url=";
    private String vip3 = "http://www.82190555.com/video.php?url=";
    private String vip4 = "http://www.sfsft.com/video.php?url=";

    @OnClick({R.id.btn_jiexi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jiexi://解析
                if (TextUtils.isEmpty(etUrl.getText().toString())) {
                    ToastUtil.showTextToast(this, "视频地址不能为空");
                } else {
                    // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                    new XPopup.Builder(this)
                            .asBottomList("请选择一项", new String[]{"本地浏览器打开", "QQ浏览器打开", "UC浏览器打开"},
                                    (position, text) -> {
                                        if (position == 0) {
                                            // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                                            new XPopup.Builder(VipJiexiActivity.this)
                                                    .asBottomList("请选择一项", new String[]{"解析通道1", "解析通道2", "解析通道3", "解析通道4"},
                                                            (position1, text1) -> {
                                                                if (position1 == 0) {
                                                                    intent = new Intent();
                                                                    intent.setAction("android.intent.action.VIEW");
                                                                    intent.setData(Uri.parse(vip1 + etUrl.getText().toString()));
                                                                    startActivity(intent);
                                                                } else if (position1 == 1) {
                                                                    intent = new Intent();
                                                                    intent.setAction("android.intent.action.VIEW");
                                                                    intent.setData(Uri.parse(vip2 + etUrl.getText().toString()));
                                                                    startActivity(intent);
                                                                } else if (position1 == 2) {
                                                                    intent = new Intent();
                                                                    intent.setAction("android.intent.action.VIEW");
                                                                    intent.setData(Uri.parse(vip3 + etUrl.getText().toString()));
                                                                    startActivity(intent);
                                                                } else if (position1 == 3) {
                                                                    intent = new Intent();
                                                                    intent.setAction("android.intent.action.VIEW");
                                                                    intent.setData(Uri.parse(vip4 + etUrl.getText().toString()));
                                                                    startActivity(intent);
                                                                }
                                                            })
                                                    .show();
                                        } else if (position == 1) {
                                            if (MarketUtils.getTools().isAppInstalled(this, "com.tencent.mtt")) {//已安装
                                                new XPopup.Builder(VipJiexiActivity.this)
                                                        .asBottomList("请选择一项", new String[]{"解析通道1", "解析通道2", "解析通道3", "解析通道4"},
                                                                (position1, text1) -> {
                                                                    if (position1 == 0) {
                                                                        MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", vip1 + etUrl.getText().toString());
                                                                    } else if (position1 == 1) {
                                                                        MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", vip2 + etUrl.getText().toString());
                                                                    } else if (position1 == 2) {
                                                                        MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", vip3 + etUrl.getText().toString());
                                                                    } else if (position1 == 3) {
                                                                        MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", vip4 + etUrl.getText().toString());
                                                                    }
                                                                })
                                                        .show();
                                            } else {
                                                //没有安装通过应用包名到应用市场搜索下载安装
                                                MarketUtils.getTools().openMarket(this, "com.tencent.mtt");
                                            }
                                        } else if (position == 2) {
                                            if (MarketUtils.getTools().isAppInstalled(this, "com.UCMobile")) {//已安装
                                                new XPopup.Builder(VipJiexiActivity.this)
                                                        .asBottomList("请选择一项", new String[]{"解析通道1", "解析通道2", "解析通道3", "解析通道4"},
                                                                (position1, text1) -> {
                                                                    if (position1 == 0) {
                                                                        MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", vip1 + etUrl.getText().toString());
                                                                    } else if (position1 == 1) {
                                                                        MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", vip1 + etUrl.getText().toString());
                                                                    } else if (position1 == 2) {
                                                                        MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", vip1 + etUrl.getText().toString());
                                                                    } else if (position1 == 3) {
                                                                        MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", vip1 + etUrl.getText().toString());
                                                                    }
                                                                })
                                                        .show();
                                            } else {
                                                //没有安装通过应用包名到应用市场搜索下载安装
                                                MarketUtils.getTools().openMarket(this, "com.UCMobile");
                                            }
                                        }
                                    })
                            .show();
                }
                break;
        }
    }
}