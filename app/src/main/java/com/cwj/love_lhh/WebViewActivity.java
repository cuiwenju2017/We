package com.cwj.love_lhh;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.wv)
    WebView wv;
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
        StatusBarUtil.setTranslucentForImageView(this, 0, clView);//沉浸状态栏
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        // 设置WebView属性，能够执行Javascript脚本
        wv.getSettings().setJavaScriptEnabled(true);
        //语言设置防止加载乱码
        wv.getSettings().setDefaultTextEncodingName("GBK");
        // 即asserts文件夹下有一个color2.html
        wv.loadUrl(url);
    }

    @Override
    protected void onPause() {
        wv.reload();//关闭页面清除背景音效
        super.onPause();
    }
}
