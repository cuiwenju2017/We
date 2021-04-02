package com.cwj.we.module.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.module.main.HomeActivity;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.FireworksPainter;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.stpv)
    SyncTextPathView stpv;
    SharedPreferences sprfMain;
    private String togetherTime, getMarriedTime;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {

    }

    public void initView() {
        fullScreen(true);//全屏显示

        //取出上个页面保存的值（取数据）
        sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
        togetherTime = sprfMain.getString("togetherTime", "");
        getMarriedTime = sprfMain.getString("getMarriedTime", "");

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (BmobUser.isLogin()) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);//n秒后执行Runnable中的run方法

        stpv.startAnimation(0, 1);
        //设置画笔特效
//        stpv.setPathPainter(new PenPainter());//笔形
        stpv.setPathPainter(new FireworksPainter());//火花
//        stpv.setPathPainter(new ArrowPainter());//箭头
        //设置动画播放完后填充颜色
        stpv.setFillColor(true);
    }

    @Override
    public void onBackPressed() {

    }
}
