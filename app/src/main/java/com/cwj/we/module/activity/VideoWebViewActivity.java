package com.cwj.we.module.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.utils.MarketUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.ycbjie.webviewlib.base.X5WebChromeClient;
import com.ycbjie.webviewlib.base.X5WebViewClient;
import com.ycbjie.webviewlib.cache.WebResponseAdapter;
import com.ycbjie.webviewlib.cache.WebViewCacheDelegate;
import com.ycbjie.webviewlib.client.JsX5WebViewClient;
import com.ycbjie.webviewlib.inter.InterWebListener;
import com.ycbjie.webviewlib.inter.VideoWebListener;
import com.ycbjie.webviewlib.utils.X5WebUtils;
import com.ycbjie.webviewlib.view.X5WebView;
import com.ycbjie.webviewlib.widget.WebProgress;

public class VideoWebViewActivity extends BaseActivity {

    private X5WebView webView;
    private X5WebChromeClient x5WebChromeClient;
    private X5WebViewClient x5WebViewClient;
    private WebProgress progress;
    private Toolbar toolbar;
    private String name;
    private String urlStr;
    private Intent intent;
    private String movieUrl;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //全屏播放退出全屏
            if (x5WebChromeClient != null && x5WebChromeClient.inCustomView()) {
                x5WebChromeClient.hideCustomView();
                return true;
                //返回网页上一页
            } else if (webView.pageCanGoBack()) {
                //退出网页
                return webView.pageGoBack();
            } else {
                handleFinish();
            }
        }
        return false;
    }

    public void handleFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (x5WebChromeClient != null) {
                x5WebChromeClient.removeVideoView();
            }
            webView.destroy();
        } catch (Exception e) {
            Log.e("X5WebViewActivity", e.getMessage());
        }
        super.onDestroy();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_web_view;
    }

    @Override
    public void initView() {
        name = getIntent().getStringExtra("name");
        webView = findViewById(R.id.web_view);
        toolbar = findViewById(R.id.my_toolbar);

        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .titleBar(toolbar)    //解决状态栏和布局重叠问题，任选其一
                .init();

        toolbar.setTitle(name);
        // 显示导航按钮
//        toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(toolbar);
        // 显示标题和子标题
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        movieUrl = getIntent().getStringExtra("movieUrl");
        webView.loadUrl(movieUrl);
        progress = findViewById(R.id.progress);
        progress.show();
        progress.setColor(this.getResources().getColor(R.color.colorAccent));

        YcX5WebViewClient webViewClient = new YcX5WebViewClient(webView, this);
        webView.setWebViewClient(webViewClient);
        YcX5WebChromeClient webChromeClient = new YcX5WebChromeClient(webView, this);
        webView.setWebChromeClient(webChromeClient);
        x5WebChromeClient = webView.getX5WebChromeClient();
        x5WebViewClient = webView.getX5WebViewClient();
        x5WebChromeClient.setWebListener(interWebListener);
        x5WebViewClient.setWebListener(interWebListener);
        //设置是否自定义视频视图
        webView.setShowCustomVideo(false);
        x5WebChromeClient.setVideoWebListener(new VideoWebListener() {
            @Override
            public void showVideoFullView() {
                //视频全频播放时监听
            }

            @Override
            public void hindVideoFullView() {
                //隐藏全频播放，也就是正常播放视频
            }

            @Override
            public void showWebView() {
                //显示webView
            }

            @Override
            public void hindWebView() {
                //隐藏webView
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar1://本地浏览器打开
                if (urlStr == null) {
                    urlStr = movieUrl;
                }
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(urlStr));
                startActivity(intent);
                break;
            case R.id.toolbar2://QQ浏览器打开
                if (urlStr == null) {
                    urlStr = movieUrl;
                }
                if (MarketUtils.getTools().isAppInstalled(this, "com.tencent.mtt")) {//已安装
                    MarketUtils.getTools().openInstalledAppInURL(VideoWebViewActivity.this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", urlStr);
                } else {
                    //没有安装通过应用包名到应用市场搜索下载安装
                    MarketUtils.getTools().openMarket(this, "com.tencent.mtt");
                }
                break;
            case R.id.toolbar3://UC浏览器打开
                if (urlStr == null) {
                    urlStr = movieUrl;
                }
                if (MarketUtils.getTools().isAppInstalled(this, "com.UCMobile")) {//已安装
                    MarketUtils.getTools().openInstalledAppInURL(VideoWebViewActivity.this, "com.UCMobile", "com.UCMobile.main.UCMobile", urlStr);
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

    private InterWebListener interWebListener = new InterWebListener() {
        @Override
        public void hindProgressBar() {
            progress.hide();
        }

        @Override
        public void showErrorView(@X5WebUtils.ErrorType int type) {
            switch (type) {
                //没有网络
                case X5WebUtils.ErrorMode.NO_NET:
                    break;
                //404，网页无法打开
                case X5WebUtils.ErrorMode.STATE_404:

                    break;
                //onReceivedError，请求网络出现error
                case X5WebUtils.ErrorMode.RECEIVED_ERROR:

                    break;
                //在加载资源时通知主机应用程序发生SSL错误
                case X5WebUtils.ErrorMode.SSL_ERROR:

                    break;
                default:
                    break;
            }
        }

        @Override
        public void startProgress(int newProgress) {
            progress.setWebProgress(newProgress);
        }

        @Override
        public void showTitle(String title) {

        }
    };

    private class YcX5WebViewClient extends JsX5WebViewClient {
        public YcX5WebViewClient(X5WebView webView, Context context) {
            super(webView, context);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("aaa", url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.d("bbb", request.getUrl().toString());
            urlStr = request.getUrl().toString();
            return super.shouldOverrideUrlLoading(view, request);
        }

        /**
         * 此方法废弃于API21，调用于非UI线程，拦截资源请求并返回响应数据，返回null时WebView将继续加载资源
         * 注意：API21以下的AJAX请求会走onLoadResource，无法通过此方法拦截
         * <p>
         * 其中 WebResourceRequest 封装了请求，WebResourceResponse 封装了响应
         * 封装了一个Web资源的响应信息，包含：响应数据流，编码，MIME类型，API21后添加了响应头，状态码与状态描述
         *
         * @param webView view
         * @param s       s
         */
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            WebResourceResponse request = WebViewCacheDelegate.getInstance().interceptRequest(s);
            return WebResponseAdapter.adapter(request);
        }

        /**
         * 此方法添加于API21，调用于非UI线程，拦截资源请求并返回数据，返回null时WebView将继续加载资源
         * <p>
         * 其中 WebResourceRequest 封装了请求，WebResourceResponse 封装了响应
         * 封装了一个Web资源的响应信息，包含：响应数据流，编码，MIME类型，API21后添加了响应头，状态码与状态描述
         *
         * @param webView            view
         * @param webResourceRequest request，添加于API21，封装了一个Web资源的请求信息，
         *                           包含：请求地址，请求方法，请求头，是否主框架，是否用户点击，是否重定向
         * @return
         */
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            WebResourceResponse request = WebViewCacheDelegate.getInstance().
                    interceptRequest(webResourceRequest);
            return WebResponseAdapter.adapter(request);
        }
    }

    private class YcX5WebChromeClient extends X5WebChromeClient {
        public YcX5WebChromeClient(X5WebView webView, Activity activity) {
            super(webView, activity);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }
}