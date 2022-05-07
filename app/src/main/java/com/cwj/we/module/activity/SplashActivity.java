package com.cwj.we.module.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.http.API;
import com.cwj.we.module.main.HomeActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_sp)
    TextView tvSp;

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

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_welcome);
        tvSp.startAnimation(animation);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (API.kv.decodeString("togetherTime") != null && API.kv.decodeString("getMarriedTime") != null &&
                    API.kv.decodeString("getMarriedTime2") != null) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, SetTimeActivity.class));
                finish();
            }
        }, 2000);//n秒后执行Runnable中的run方法
    }

    @Override
    public void onBackPressed() {

    }
}
