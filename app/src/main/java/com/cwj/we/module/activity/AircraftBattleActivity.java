package com.cwj.we.module.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 飞机大战
 */
public class AircraftBattleActivity extends BaseActivity implements Button.OnClickListener {

    @BindView(R.id.cl_view)
    CoordinatorLayout clView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aircraft_battle;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                .init();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnGame) {
            startGame();
        }
    }

    public void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
