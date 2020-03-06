package com.cwj.love_lhh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import yanzhikai.textpath.AsyncTextPathView;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.ArrowPainter;
import yanzhikai.textpath.painter.FireworksPainter;
import yanzhikai.textpath.painter.PenPainter;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.stpv)
    SyncTextPathView stpv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 3333);//n秒后执行Runnable中的run方法
    }

    private void initView() {
//        StatusBarUtil.setTransparent(this);
        stpv.startAnimation(0, 1);
        //设置画笔特效
//        stpv.setPathPainter(new PenPainter());//笔形
        stpv.setPathPainter(new FireworksPainter());//火花
//        stpv.setPathPainter(new ArrowPainter());//箭头
        //设置动画播放完后填充颜色
        stpv.setFillColor(true);
    }

}
