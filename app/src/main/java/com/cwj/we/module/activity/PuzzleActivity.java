package com.cwj.we.module.activity;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.gyf.immersionbar.ImmersionBar;

/**
 * 拼图
 */
public class PuzzleActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_puzzle;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
    }
}
