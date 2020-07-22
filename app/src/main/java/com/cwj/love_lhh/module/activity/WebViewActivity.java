package com.cwj.love_lhh.module.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.view.ProgressWebView;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.wv)
    ProgressWebView wv;
    @BindView(R.id.cl_view)
    CoordinatorLayout clView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
//        StatusBarUtil.setTranslucentForImageView(this, 0, clView);//沉浸状态栏
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        /*// 设置WebView属性，能够执行Javascript脚本
        wv.getSettings().setJavaScriptEnabled(true);
        //语言设置防止加载乱码
        wv.getSettings().setDefaultTextEncodingName("GBK");
        // 即asserts文件夹下有一个color2.html
        wv.loadUrl(url);*/

        wv.getSettings().setJavaScriptEnabled(true);// 设置WebView属性，能够执行Javascript脚本
        wv.getSettings().setDomStorageEnabled(true);//腾讯兔小巢反馈加入

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
                if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
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
            }
        });
        wv.loadUrl(url);

        //腾讯兔小巢反馈允许其拉起微信。
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);

                if (url == null) {
                    return false;
                }
                try {
                    if (url.startsWith("weixin://")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        view.getContext().startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
                view.loadUrl(url);
                return true;
            }
        };
        wv.setWebViewClient(webViewClient);
    }

    /**
     * 使点击回退按钮不会直接退出整个应用程序而是返回上一个页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {
            wv.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
