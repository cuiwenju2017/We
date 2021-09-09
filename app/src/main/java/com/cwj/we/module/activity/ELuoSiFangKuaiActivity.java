package com.cwj.we.module.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 俄罗斯方块
 */
public class ELuoSiFangKuaiActivity extends BaseActivity {

    @BindView(R.id.grade1)
    Button grade1;
    @BindView(R.id.grade2)
    Button grade2;
    @BindView(R.id.grade3)
    Button grade3;
    @BindView(R.id.grade4)
    Button grade4;
    @BindView(R.id.grade5)
    Button grade5;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_e_luo_si_fang_kuai;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
                .init();
    }

    @OnClick({R.id.grade1, R.id.grade2, R.id.grade3, R.id.grade4, R.id.grade5})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, ELuoSiFangKuaiStartActivity.class);
        switch (view.getId()) {
            case R.id.grade1:
                intent.putExtra("grade", 1);
                break;
            case R.id.grade2:
                intent.putExtra("grade", 2);
                break;
            case R.id.grade3:
                intent.putExtra("grade", 3);
                break;
            case R.id.grade4:
                intent.putExtra("grade", 4);
                break;
            case R.id.grade5:
                intent.putExtra("grade", 5);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}