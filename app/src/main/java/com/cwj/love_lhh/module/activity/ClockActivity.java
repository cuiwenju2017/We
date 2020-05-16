package com.cwj.love_lhh.module.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.love_lhh.R;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockActivity extends AppCompatActivity {

    @BindView(R.id.cl_view)
    CoordinatorLayout clView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucentForImageView(this, 0, clView);//沉浸状态栏
    }
}
