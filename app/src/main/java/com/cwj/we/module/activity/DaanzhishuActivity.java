package com.cwj.we.module.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.game.daanzhishu.Answer;
import com.cwj.we.utils.OneClickThree;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 答案之书
 */
public class DaanzhishuActivity extends BaseActivity {

    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.ll_text)
    LinearLayout llText;

    private ObjectAnimator objectAnimator;
    private String[] answerList = Answer.ANSWER;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daanzhishu;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
                .init();
        ll.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_open, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back://再次提问
                if (!OneClickThree.isFastClick()) {
                    ll.setVisibility(View.VISIBLE);
                    llText.setVisibility(View.GONE);
                    PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f);
                    PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f);
                    PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f);
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(ll, valuesHolder1, valuesHolder2, valuesHolder3);
                    objectAnimator.setDuration(200).start();
                }
                break;
            case R.id.btn_open://打开
                if (!OneClickThree.isFastClick()) {
                    PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("rotationY", 0.0f, 1080.0f);
                    PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f);
                    PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f);
                    PropertyValuesHolder valuesHolder4 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f);
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(ll, valuesHolder1, valuesHolder2, valuesHolder3, valuesHolder4);
                    objectAnimator.setDuration(2000).start();

                    Random random = new Random();
                    List<String> resultList = new ArrayList<>(answerList.length);
                    Collections.addAll(resultList, answerList);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(() -> {
                        ll.setVisibility(View.GONE);
                        llText.setVisibility(View.VISIBLE);
                        tvText.setText("" + resultList.get(random.nextInt(resultList.size())));
                    }, 2 * 1000);//n秒后执行Runnable中的run方法
                }
                break;
            default:
                break;
        }
    }
}