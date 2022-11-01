package com.cwj.we.module.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.DayimaZhouqiBean;
import com.cwj.we.http.API;
import com.cwj.we.utils.TimeUtils;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.suke.widget.SwitchButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增、修改大姨妈
 */
public class AddDayimaActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.sb)
    SwitchButton sb;
    @BindView(R.id.btn_save)
    Button btnSave;
    private boolean isDayima;
    private String time;
    private long id;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_dayima;
    }

    @Override
    public void initData() {
        sb.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                isDayima = true;
            } else {
                isDayima = false;
            }
        });
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
        selectedDate = Calendar.getInstance();//系统当前时间

        id = getIntent().getLongExtra("id", 0);
        time = getIntent().getStringExtra("time");
        isDayima = getIntent().getBooleanExtra("isDayima", false);
        if (time != null) {
            tvTitle.setText("修改");
            tvTime.setText(time);
        } else {
            tvTitle.setText("新增");
            tvTime.setText(TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy-MM-dd"));
        }

        if (isDayima) {
            sb.setChecked(true);
        } else {
            sb.setChecked(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private BasePopupView basePopupView;

    class CustomPopup extends CenterPopupView {
        @BindView(R.id.et_zhouqi)
        EditText etZhouqi;
        @BindView(R.id.et_tianshu)
        EditText etTianshu;
        @BindView(R.id.btn_commit)
        Button btnCommit;

        //注意：自定义弹窗本质是一个自定义View，但是只需重写一个参数的构造，其他的不要重写，所有的自定义弹窗都是这样。
        public CustomPopup(@NonNull Context context) {
            super(context);
        }

        // 返回自定义弹窗的布局
        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_popup_zhouqi;
        }

        // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
        @Override
        protected void onCreate() {
            super.onCreate();
            ButterKnife.bind(this);
            if (API.kv.decodeString("zhouqi") != null) {
                etZhouqi.setText("" + API.kv.decodeString("zhouqi"));
            }

            if (API.kv.decodeString("tianshu") != null) {
                etTianshu.setText("" + API.kv.decodeString("tianshu"));
            }
        }

        // 设置最大宽度，看需要而定，
        @Override
        protected int getMaxWidth() {
            return super.getMaxWidth();
        }

        // 设置最大高度，看需要而定
        @Override
        protected int getMaxHeight() {
            return super.getMaxHeight();
        }

        // 设置自定义动画器，看需要而定
        @Override
        protected PopupAnimator getPopupAnimator() {
            return super.getPopupAnimator();
        }

        /**
         * 弹窗的宽度，用来动态设定当前弹窗的宽度，受getMaxWidth()限制
         *
         * @return
         */
        protected int getPopupWidth() {
            return 0;
        }

        /**
         * 弹窗的高度，用来动态设定当前弹窗的高度，受getMaxHeight()限制
         *
         * @return
         */
        protected int getPopupHeight() {
            return 0;
        }

        @OnClick({R.id.btn_commit})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.btn_commit://确定周期
                    if (TextUtils.isEmpty(etZhouqi.getText().toString().trim())) {
                        ToastUtil.showTextToast(AddDayimaActivity.this, "请输入月经周期");
                    } else if (TextUtils.isEmpty(etTianshu.getText().toString().trim())) {
                        ToastUtil.showTextToast(AddDayimaActivity.this, "请输入月经天数");
                    } else {
                        API.kv.encode("zhouqi", etZhouqi.getText().toString().trim());
                        API.kv.encode("tianshu", etTianshu.getText().toString().trim());
                        ToastUtil.showTextToast(AddDayimaActivity.this, "设置成功");
                        setResult(Activity.RESULT_OK);
                        basePopupView.dismiss();
                    }
                    break;
            }
        }

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private TimePickerView pvTime;
    private Calendar selectedDate;
    private SimpleDateFormat chineseDateFormat;

    @OnClick({R.id.iv_setting, R.id.tv_time, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting://设置周期
                basePopupView = new XPopup.Builder(this)
                        .asCustom(new CustomPopup(this))
                        .show();
                break;
            case R.id.tv_time://选择时间
                Calendar startDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                startDate.set(1900, 0, 1);
                //时间选择器
                pvTime = new TimePickerBuilder(this, (date, v) -> {
                    tvTime.setText(getTime(date));
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setRangDate(startDate, selectedDate)//起始终止年月日设定
                        .setDividerColor(Color.RED)//当前选中日期线条颜色
                        .setSubmitColor(Color.RED)//确定按钮文字颜色
                        .setCancelColor(Color.RED)//取消按钮文字颜色
                        .build();
                if (time == null || TextUtils.isEmpty(time)) {
                    pvTime.setDate(selectedDate);// 如果不设置的话，默认是系统时间*/
                } else {
                    Calendar settTTime = Calendar.getInstance();
                    chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        settTTime.setTime(chineseDateFormat.parse(time));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    pvTime.setDate(settTTime);// 如果不设置的话，默认是系统时间*/
                }
                pvTime.show();
                break;
            case R.id.btn_save://保存
                if (API.kv.decodeString("zhouqi") == null || API.kv.decodeString("tianshu") == null) {
                    ToastUtil.showTextToast(this, "请先设置月经周期和天数");
                } else if (TextUtils.isEmpty(tvTime.getText().toString().trim())) {
                    ToastUtil.showTextToast(this, "请选择日期");
                } else {
                    DayimaZhouqiBean dayimaZhouqiBean = new DayimaZhouqiBean();
                    dayimaZhouqiBean.setTime(tvTime.getText().toString().trim());
                    dayimaZhouqiBean.setDayima(isDayima);
                    if (id > 0) {
                        dayimaZhouqiBean.saveOrUpdate("id = ?", "" + id);
                        ToastUtil.showTextToast(this, "修改成功");
                        Intent intent = new Intent();
                        intent.putExtra("time", tvTime.getText().toString().trim());
                        intent.putExtra("isDayima", isDayima);
                        setResult(Activity.RESULT_OK, intent);
                    } else {
                        dayimaZhouqiBean.save();
                        ToastUtil.showTextToast(this, "保存成功");
                        setResult(Activity.RESULT_OK);
                    }
                    finish();
                }
                break;
        }
    }
}