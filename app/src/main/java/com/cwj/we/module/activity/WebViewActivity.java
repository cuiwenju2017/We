package com.cwj.we.module.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.http.API;
import com.cwj.we.utils.MarketUtils;
import com.cwj.we.utils.OneClickThree;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv)
    WebView wv;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.tv)
    TextView tv;

    private String url;
    private Intent intent;
    private ClipboardManager manager;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initData() {

    }

    @SuppressLint({"WrongConstant", "SetJavaScriptEnabled"})
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .titleBar(myToolbar)    //解决状态栏和布局重叠问题，任选其一
                .init();
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        boolean xbw = intent.getBooleanExtra("xbw", false);
        String name = getIntent().getStringExtra("name");
        if (xbw) {
            myToolbar.setVisibility(GONE);
        } else {
            myToolbar.setVisibility(VISIBLE);
            myToolbar.setTitle(name);
            // 显示导航按钮
//        toolbar.setNavigationIcon(R.drawable.icon_back);
            setSupportActionBar(myToolbar);
            // 显示标题和子标题
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) { //加载的url不是http/https协议地址
            tv.setVisibility(View.VISIBLE);
            wv.setVisibility(GONE);
            tv.setText("" + url);
        } else {
            wv.setVisibility(View.VISIBLE);
            tv.setVisibility(GONE);
        }

        WebChromeClient wvcc = new WebChromeClient();
        WebSettings webSettings = wv.getSettings();
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);//设置支持负载
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        webSettings.setJavaScriptEnabled(true);// 设置WebView属性，能够执行Javascript脚本
        webSettings.setDomStorageEnabled(true);//腾讯兔小巢反馈加入
        webSettings.setDefaultTextEncodingName("GBK");//设置编码格式
        webSettings.setPluginsEnabled(true);//设置webview支持插件
        wv.setWebChromeClient(wvcc);

        //使网页用WebView打开
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebView.HitTestResult hit = view.getHitTestResult();
                //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
                    //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                    Log.e("重定向", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
                    Log.e("重定向", "GetURL: " + view.getUrl() + "\n" + "getOriginalUrl()" + view.getOriginalUrl());
                    Log.d("重定向", "URL: " + url);
                }
                if (url == null) {
                    return false;
                }
                try {
                    if (url.startsWith("weixin://")) {//腾讯兔小巢反馈允许其拉起微信。
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        view.getContext().startActivity(intent);
                        return true;
                    } else if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
                        view.loadUrl(url);
                        return false; //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
                    } else { //加载的url是自定义协议地址
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        });

        /*** 视频播放相关的方法 **/
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(WebViewActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
                showCustomView(view, callback);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//播放时横屏幕，如果需要改变横竖屏，只需该参数就行了
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//不播放时竖屏
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
                if (newProgress == 100) {
                    progress.setVisibility(GONE);
                } else {
                    if (progress.getVisibility() == GONE)
                        progress.setVisibility(VISIBLE);
                    progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                myToolbar.setTitle(title);
            }
        });

        //下载监听
        wv.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> doCode(url, userAgent, contentDisposition, mimetype, contentLength));

        wv.loadUrl(url);
    }

    private void doCode(String s, String s1, String s2, String s3, long l) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(s));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) { //加载的url不是http/https协议地址
            getMenuInflater().inflate(R.menu.menu_toolbar_fuzhi, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_toolbar_demo, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar6://夸克浏览器打开
                if (MarketUtils.getTools().isAppInstalled(this, "com.quark.browser")) {//已安装
                    MarketUtils.getTools().openInstalledAppInURL(this,
                            "com.quark.browser", "com.ucpro.MainActivity",
                            (!wv.getUrl().startsWith("http://") && !wv.getUrl().startsWith("https://")) ? url : wv.getUrl());
                } else {
                    //没有安装通过应用包名到应用市场搜索下载安装
                    MarketUtils.getTools().openMarket(this, "com.quark.browser");
                }
                break;
            case R.id.toolbar5://解析
                // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                new XPopup.Builder(this)
                        .asBottomList("请选择一项", new String[]{"本地浏览器打开", "夸克浏览器打开",
                                        "QQ浏览器打开", "UC浏览器打开", "应用内打开"},
                                (position, text) -> {
                                    if (position == 0) {//本地浏览器打开
                                        // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                                        new XPopup.Builder(this)
                                                .asBottomList("请选择一项", new String[]{"无名小站解析1", "无名小站解析2", "无名小站解析3", "无名小站解析4", "万能命令解析"},
                                                        (position1, text1) -> {
                                                            intent = new Intent();
                                                            intent.setAction("android.intent.action.VIEW");
                                                            if (position1 == 0) {
                                                                intent.setData(Uri.parse(API.vip1 + wv.getUrl()));
                                                            } else if (position1 == 1) {
                                                                intent.setData(Uri.parse(API.vip2 + wv.getUrl()));
                                                            } else if (position1 == 2) {
                                                                intent.setData(Uri.parse(API.vip3 + wv.getUrl()));
                                                            } else if (position1 == 3) {
                                                                intent.setData(Uri.parse(API.vip4 + wv.getUrl()));
                                                            } else if (position1 == 4) {
                                                                intent.setData(Uri.parse(API.vip5 + wv.getUrl()));
                                                            }
                                                            startActivity(intent);
                                                        })
                                                .show();
                                    } else if (position == 1) {//夸克浏览器打开
                                        if (MarketUtils.getTools().isAppInstalled(this, "com.quark.browser")) {//已安装
                                            new XPopup.Builder(this)
                                                    .asBottomList("请选择一项", new String[]{"无名小站解析1", "无名小站解析2", "无名小站解析3", "无名小站解析4", "万能命令解析"},
                                                            (position1, text1) -> {
                                                                if (position1 == 0) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.quark.browser", "com.ucpro.MainActivity", API.vip1 + wv.getUrl());
                                                                } else if (position1 == 1) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.quark.browser", "com.ucpro.MainActivity", API.vip2 + wv.getUrl());
                                                                } else if (position1 == 2) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.quark.browser", "com.ucpro.MainActivity", API.vip3 + wv.getUrl());
                                                                } else if (position1 == 3) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.quark.browser", "com.ucpro.MainActivity", API.vip4 + wv.getUrl());
                                                                } else if (position1 == 4) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.quark.browser", "com.ucpro.MainActivity", API.vip5 + wv.getUrl());
                                                                }
                                                            })
                                                    .show();
                                        } else {
                                            //没有安装通过应用包名到应用市场搜索下载安装
                                            MarketUtils.getTools().openMarket(this, "com.quark.browser");
                                        }
                                    } else if (position == 2) {//QQ浏览器打开
                                        if (MarketUtils.getTools().isAppInstalled(this, "com.tencent.mtt")) {//已安装
                                            new XPopup.Builder(this)
                                                    .asBottomList("请选择一项", new String[]{"无名小站解析1", "无名小站解析2", "无名小站解析3", "无名小站解析4", "万能命令解析"},
                                                            (position1, text1) -> {
                                                                if (position1 == 0) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip1 + wv.getUrl());
                                                                } else if (position1 == 1) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip2 + wv.getUrl());
                                                                } else if (position1 == 2) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip3 + wv.getUrl());
                                                                } else if (position1 == 3) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip4 + wv.getUrl());
                                                                } else if (position1 == 4) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", API.vip5 + wv.getUrl());
                                                                }
                                                            })
                                                    .show();
                                        } else {
                                            //没有安装通过应用包名到应用市场搜索下载安装
                                            MarketUtils.getTools().openMarket(this, "com.tencent.mtt");
                                        }
                                    } else if (position == 3) {//UC浏览器打开
                                        if (MarketUtils.getTools().isAppInstalled(this, "com.UCMobile")) {//已安装
                                            new XPopup.Builder(this)
                                                    .asBottomList("请选择一项", new String[]{"无名小站解析1", "无名小站解析2", "无名小站解析3", "无名小站解析4", "万能命令解析"},
                                                            (position1, text1) -> {
                                                                if (position1 == 0) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip1 + wv.getUrl());
                                                                } else if (position1 == 1) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip2 + wv.getUrl());
                                                                } else if (position1 == 2) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip3 + wv.getUrl());
                                                                } else if (position1 == 3) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip4 + wv.getUrl());
                                                                } else if (position1 == 4) {
                                                                    MarketUtils.getTools().openInstalledAppInURL(this, "com.UCMobile", "com.UCMobile.main.UCMobile", API.vip5 + wv.getUrl());
                                                                }
                                                            })
                                                    .show();
                                        } else {
                                            //没有安装通过应用包名到应用市场搜索下载安装
                                            MarketUtils.getTools().openMarket(this, "com.UCMobile");
                                        }
                                    } else if (position == 4) {//应用内打开
                                        if (!OneClickThree.isFastClick()) {
                                            intent = new Intent(this, WebViewActivity.class);
                                            intent.putExtra("url", API.vip5 + wv.getUrl());
                                            startActivity(intent);
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.toolbar4://复制当前链接
                manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newHtmlText(null, (!wv.getUrl().startsWith("http://") &&
                                !wv.getUrl().startsWith("https://")) ? url : wv.getUrl(),
                        null));
                ToastUtil.showTextToast(this, "复制成功");
                break;
            case R.id.toolbar_fuzhi://复制内容
                manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newHtmlText(null, url, null));
                ToastUtil.showTextToast(this, "复制成功");
                break;
            case R.id.toolbar1://本地浏览器打开
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                if (!wv.getUrl().startsWith("http://") && !wv.getUrl().startsWith("https://")) { //加载的url不是http/https协议地址
                    intent.setData(Uri.parse(url));
                } else {
                    intent.setData(Uri.parse(wv.getUrl()));
                }
                startActivity(intent);
                break;
            case R.id.toolbar2://QQ浏览器打开
                if (MarketUtils.getTools().isAppInstalled(this, "com.tencent.mtt")) {//已安装
                    MarketUtils.getTools().openInstalledAppInURL(this,
                            "com.tencent.mtt", "com.tencent.mtt.MainActivity",
                            (!wv.getUrl().startsWith("http://") && !wv.getUrl().startsWith("https://")) ? url : wv.getUrl());
                } else {
                    //没有安装通过应用包名到应用市场搜索下载安装
                    MarketUtils.getTools().openMarket(this, "com.tencent.mtt");
                }
                break;
            case R.id.toolbar3://UC浏览器打开
                if (MarketUtils.getTools().isAppInstalled(this, "com.UCMobile")) {//已安装
                    MarketUtils.getTools().openInstalledAppInURL(this,
                            "com.UCMobile", "com.UCMobile.main.UCMobile",
                            (!wv.getUrl().startsWith("http://") && !wv.getUrl().startsWith("https://")) ? url : wv.getUrl());
                } else {
                    //没有安装通过应用包名到应用市场搜索下载安装
                    MarketUtils.getTools().openMarket(this, "com.UCMobile");
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private View customView;
    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private FrameLayout fullscreenContainer;
    private IX5WebChromeClient.CustomViewCallback customViewCallback;

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        WebViewActivity.this.getWindow().getDecorView();
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(WebViewActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        wv.setVisibility(VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {
        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 使点击回退按钮不会直接退出整个应用程序而是返回上一个页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                //回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面
                if (customView != null) {
                    hideCustomView();
                } else if (wv.canGoBack()) {
                    wv.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wv != null) {
            wv.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wv != null) {
            wv.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv != null) {
            wv.destroy();
        }
    }
}
