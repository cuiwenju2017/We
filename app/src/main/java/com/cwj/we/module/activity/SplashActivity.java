package com.cwj.we.module.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.module.main.HomeActivity;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends BaseActivity {

    SharedPreferences sprfMain;
    @BindView(R.id.tv_sp)
    TextView tvSp;
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

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_welcome);
        tvSp.startAnimation(animation);

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
    }

    @Override
    public void onBackPressed() {

    }
}
