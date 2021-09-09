package com.cwj.we.module.activity;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.TextView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.EventBG;
import com.cwj.we.common.GlobalConstant;
import com.cwj.we.game.puzzle.GamePintuLayout;
import com.cwj.we.http.API;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拼图
 */
public class PuzzleActivity extends BaseActivity {

    @BindView(R.id.tv_Column)
    TextView tvColumn;
    @BindView(R.id.id_gameview)
    GamePintuLayout idGameview;
    @BindView(R.id.btn_chongzhi)
    Button btnChongzhi;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_puzzle;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        tvColumn.setText("难度：" + API.kv.decodeInt(GlobalConstant.Column, 3) + "X" + API.kv.decodeInt(GlobalConstant.Column, 3));
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBG eventBG) {
        switch (eventBG.getType()) {
            case "Column_SUCCESS":
                tvColumn.setText("难度：" + API.kv.decodeInt(GlobalConstant.Column, 3) + "X" + API.kv.decodeInt(GlobalConstant.Column, 3));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_chongzhi)
    public void onViewClicked() {//重置关卡
        if (API.kv.decodeInt(GlobalConstant.Column) == 3 || API.kv.decodeInt(GlobalConstant.Column) == 0) {
            ToastUtil.showTextToast(this, "已是原始关卡");
        } else {
            new XPopup.Builder(this).asConfirm("提示", "确定重置关卡吗？",
                    () -> {
                        idGameview.change();
                        idGameview.invalidate();
                    })
                    .show();
        }
    }
}
