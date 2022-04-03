package com.cwj.we.module.dayima;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.JingqiTime;
import com.cwj.we.bean.User;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.suke.widget.SwitchButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 修改大姨妈
 */
public class UpdateDayimaActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_function)
    TextView tvRightFunction;
    @BindView(R.id.iv_right_function)
    ImageView ivRightFunction;
    @BindView(R.id.sc)
    SwitchButton sc;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private TimePickerView pvTime2;
    private Calendar selectedDate;
    private BasePopupView popupView;
    private String userObjectId;
    private String objectId;
    private String time;
    private boolean type;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_dayima;
    }

    @Override
    public void initData() {
        sc.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                type = true;
            } else {
                type = false;
            }
        });
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();

        tvTitle.setText("编辑");
        selectedDate = Calendar.getInstance();//系统当前时间
        objectId = getIntent().getStringExtra("objectId");
        time = getIntent().getStringExtra("time");
        type = getIntent().getBooleanExtra("type", false);
        tvTime.setText(time);
        if (type) {
            sc.setChecked(true);
        } else {
            sc.setChecked(false);
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private SharedPreferences sprfMain;

    /**
     * 修改一对一关联，修改日期
     */
    private void updatePostAuthor() {
        sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
        userObjectId = sprfMain.getString("userObjectId", "");
        User user = new User();
        user.setObjectId(userObjectId);
        JingqiTime jingqiTime = new JingqiTime();
        jingqiTime.setObjectId(objectId);
        jingqiTime.setTime(time);
        jingqiTime.setType(type);
        //修改一对一关联
        jingqiTime.setAuthor(user);
        jingqiTime.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                popupView.smartDismiss(); //会等待弹窗的开始动画执行完毕再进行消失，可以防止接口调用过快导致的动画不完整。
                if (e == null) {
                    ToastUtil.showTextToast(UpdateDayimaActivity.this, "修改成功");
                    Intent intent = new Intent();
                    intent.putExtra("time", time);
                    intent.putExtra("type", type);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.showTextToast(UpdateDayimaActivity.this, e.getMessage());
                }
            }
        });
    }

    private Calendar startTime = Calendar.getInstance();

    @OnClick({R.id.iv_back, R.id.tv_time, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_time://时间选择
                Calendar startDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                startDate.set(1900, 0, 1);
                //时间选择器
                pvTime2 = new TimePickerBuilder(context, (date, v) -> {
                    time = getTime(date);
                    tvTime.setText(time);
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setRangDate(startDate, selectedDate)//起始终止年月日设定
                        .setDividerColor(Color.RED)//当前选中日期线条颜色
                        .setSubmitColor(Color.RED)//确定按钮文字颜色
                        .setCancelColor(Color.RED)//取消按钮文字颜色
                        .build();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startT = null;
                try {
                    startT = sdf.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                startTime.setTime(startT);
                pvTime2.setDate(startTime);// 如果不设置的话，默认是系统时间*/
                pvTime2.show();
                break;
            case R.id.btn_commit://确认
                popupView = new XPopup.Builder(this)
                        .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                        .asLoading("")
                        .show();
                updatePostAuthor();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}