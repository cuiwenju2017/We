package com.cwj.love_lhh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.model.Day;
import com.cwj.love_lhh.model.User;
import com.cwj.love_lhh.utils.ToastUtil;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import yanzhikai.textpath.AsyncTextPathView;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.ArrowPainter;
import yanzhikai.textpath.painter.FireworksPainter;
import yanzhikai.textpath.painter.PenPainter;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.stpv)
    SyncTextPathView stpv;
    SharedPreferences sprfMain;
    private String togetherTime, getMarriedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();

        //取出上个页面保存的值（取数据）
        sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
        togetherTime = sprfMain.getString("togetherTime", "");
        getMarriedTime = sprfMain.getString("getMarriedTime", "");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BmobUser.isLogin()) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
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
