package com.cwj.we.module.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.http.API;
import com.cwj.we.utils.MarketUtils;
import com.cwj.we.utils.OneClickThree;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频解析
 */
public class VipJiexiActivity extends BaseActivity {

    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.btn_jiexi)
    Button btnJiexi;
    @BindView(R.id.iv_tengxun)
    ImageView ivTengxun;
    @BindView(R.id.iv_aiqiyi)
    ImageView ivAiqiyi;
    @BindView(R.id.iv_youku)
    ImageView ivYouku;
    @BindView(R.id.iv_mangguo)
    ImageView ivMangguo;
    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.ll_context)
    LinearLayout llContext;

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

    private int num = 0;

    @OnClick({R.id.btn_jiexi, R.id.iv_tengxun, R.id.iv_aiqiyi, R.id.iv_youku, R.id.iv_mangguo,
            R.id.btn_open, R.id.ll_context})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_context:
                num = num + 1;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        num = 0;
                    }
                }, 1000);

                if (num > 3) {
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://64maoee.com/");
                    startActivity(intent);
                }
                break;
            case R.id.btn_open://直接打开网址
                if (TextUtils.isEmpty(etUrl.getText().toString())) {
                    ToastUtil.showTextToast(this, "视频地址不能为空");
                } else {
                    if (!OneClickThree.isFastClick()) {
                        intent = new Intent(this, WebViewActivity.class);
                        if (etUrl.getText().toString().trim().startsWith("http://") || etUrl.getText().toString().trim().startsWith("https://")) {
                            intent.putExtra("url", etUrl.getText().toString().trim());//打开网址
                        } else {
                            try {//百度搜索
                                intent.putExtra("url", "http://www.baidu.com/s?&ie=utf-8&oe=UTF-8&wd=" + URLEncoder.encode(etUrl.getText().toString().trim(), "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        startActivity(intent);
                    }
                }
                break;
            case R.id.iv_tengxun://腾讯视频
                if (!OneClickThree.isFastClick()) {
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://m.v.qq.com/");
                    startActivity(intent);
                }
                break;
            case R.id.iv_aiqiyi://爱奇艺
                if (!OneClickThree.isFastClick()) {
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://m.iqiyi.com/");
                    startActivity(intent);
                }
                break;
            case R.id.iv_youku://优酷
                if (!OneClickThree.isFastClick()) {
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://youku.com/");
                    startActivity(intent);
                }
                break;
            case R.id.iv_mangguo://芒果tv
                if (!OneClickThree.isFastClick()) {
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://m.mgtv.com/channel/home");
                    startActivity(intent);
                }
                break;
            case R.id.btn_jiexi://解析
                if (TextUtils.isEmpty(etUrl.getText().toString())) {
                    ToastUtil.showTextToast(this, "视频地址不能为空");
                } else {
                    if (!OneClickThree.isFastClick()) {
                        // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                        new XPopup.Builder(this)
                                .asBottomList("请选择一项", new String[]{"本地浏览器打开", "QQ浏览器打开", "UC浏览器打开", "应用内打开"},
                                        (position, text) -> {
                                            if (position == 0) {//本地浏览器打开
                                                // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                                                new XPopup.Builder(VipJiexiActivity.this)
                                                        .asBottomList("请选择一项", new String[]{"无名小站解析1", "无名小站解析2", "无名小站解析3", "无名小站解析4", "万能命令解析"},
                                                                (position1, text1) -> {
                                                                    intent = new Intent();
                                                                    intent.setAction("android.intent.action.VIEW");
                                                                    if (position1 == 0) {
                                                                        intent.setData(Uri.parse(API.vip1 + etUrl.getText().toString()));
                                                                    } else if (position1 == 1) {
                                                                        intent.setData(Uri.parse(API.vip2 + etUrl.getText().toString()));
                                                                    } else if (position1 == 2) {
                                                                        intent.setData(Uri.parse(API.vip3 + etUrl.getText().toString()));
                                                                    } else if (position1 == 3) {
                                                                        intent.setData(Uri.parse(API.vip4 + etUrl.getText().toString()));
                                                                    } else if (position1 == 4) {
                                                                        intent.setData(Uri.parse(API.vip5 + etUrl.getText().toString()));
                                                                    }
                                                                    startActivity(intent);
                                                                })
                                                        .show();
                                            } else if (position == 1) {//QQ浏览器打开
                                                if (MarketUtils.getTools().isAppInstalled(this, "com.tencent.mtt")) {//已安装
                                                    new XPopup.Builder(VipJiexiActivity.this)
                                                            .asBottomList("请选择一项", new String[]{"无名小站解析1", "无名小站解析2", "无名小站解析3", "无名小站解析4", "万能命令解析"},
                                                                    (position1, text1) -> {
                                                                        if (position1 == 0) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip1 + etUrl.getText().toString());
                                                                        } else if (position1 == 1) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip2 + etUrl.getText().toString());
                                                                        } else if (position1 == 2) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip3 + etUrl.getText().toString());
                                                                        } else if (position1 == 3) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip4 + etUrl.getText().toString());
                                                                        } else if (position1 == 4) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip5 + etUrl.getText().toString());
                                                                        }
                                                                    })
                                                            .show();
                                                } else {
                                                    //没有安装通过应用包名到应用市场搜索下载安装
                                                    MarketUtils.getTools().openMarket(this, "com.tencent.mtt");
                                                }
                                            } else if (position == 2) {//UC浏览器打开
                                                if (MarketUtils.getTools().isAppInstalled(this, "com.UCMobile")) {//已安装
                                                    new XPopup.Builder(VipJiexiActivity.this)
                                                            .asBottomList("请选择一项", new String[]{"无名小站解析1", "无名小站解析2", "无名小站解析3", "无名小站解析4", "万能命令解析"},
                                                                    (position1, text1) -> {
                                                                        if (position1 == 0) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip1 + etUrl.getText().toString());
                                                                        } else if (position1 == 1) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip2 + etUrl.getText().toString());
                                                                        } else if (position1 == 2) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip3 + etUrl.getText().toString());
                                                                        } else if (position1 == 3) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip4 + etUrl.getText().toString());
                                                                        } else if (position1 == 4) {
                                                                            MarketUtils.getTools().openInstalledAppInURL(VipJiexiActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip5 + etUrl.getText().toString());
                                                                        }
                                                                    })
                                                            .show();
                                                } else {
                                                    //没有安装通过应用包名到应用市场搜索下载安装
                                                    MarketUtils.getTools().openMarket(this, "com.UCMobile");
                                                }
                                            } else if (position == 3) {//应用内打开
                                                if (!OneClickThree.isFastClick()) {
                                                    intent = new Intent(this, WebViewActivity.class);
                                                    intent.putExtra("url", API.vip5 + etUrl.getText().toString());
                                                    startActivity(intent);
                                                }
                                            }
                                        })
                                .show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}