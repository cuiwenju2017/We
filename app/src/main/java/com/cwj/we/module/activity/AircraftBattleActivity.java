package com.cwj.we.module.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.common.GlobalConstant;
import com.cwj.we.http.API;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 飞机大战
 */
public class AircraftBattleActivity extends BaseActivity implements Button.OnClickListener {

    @BindView(R.id.cl_view)
    CoordinatorLayout clView;
    @BindView(R.id.tv_score)
    TextView tvScore;
    private int GAMEVIEW = 201;

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
        tvScore.setText("历史最高分：" + API.kv.decodeLong(GlobalConstant.feijidazhanScore));
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
        startActivityForResult(intent, GAMEVIEW);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GAMEVIEW && resultCode == Activity.RESULT_OK) {
            tvScore.setText("历史最高分：" + data.getLongExtra(GlobalConstant.feijidazhanScore, 0));
        }
    }
}
