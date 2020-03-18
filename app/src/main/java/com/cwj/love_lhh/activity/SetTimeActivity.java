package com.cwj.love_lhh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cwj.love_lhh.R;
import com.cwj.love_lhh.utils.ChinaDate;
import com.cwj.love_lhh.utils.TimeUtils;
import com.jaeger.library.StatusBarUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.Long.parseLong;

public class SetTimeActivity extends AppCompatActivity {

    @BindView(R.id.tv_together_time)
    TextView tvTogetherTime;
    @BindView(R.id.tv_get_married_time)
    TextView tvGetMarriedTime;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private Calendar selectedDate;
    private TimePickerView pvTime, pvCustomLunar;
    private String togetherTime;
    private ChinaDate lunar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
        long nowTime = TimeUtils.getTimeStame();
        togetherTime = TimeUtils.dateToString(nowTime);
        tvTogetherTime.setText(togetherTime);
        selectedDate = Calendar.getInstance();//系统当前时间

        lunar = new ChinaDate(selectedDate);
        tvGetMarriedTime.setText("" + lunar);
    }


    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    private String togetherT, getMarriedT;

    @OnClick({R.id.tv_together_time, R.id.tv_get_married_time, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_together_time://在一起日子的选择
                Calendar startDate = Calendar.getInstance();
//                Calendar endDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                startDate.set(1900, 0, 1);
//                endDate.set(2020, 11, 31);
                //时间选择器
                pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        togetherTime = getTime(date);
                        tvTogetherTime.setText(togetherTime);
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                        .setRangDate(startDate, selectedDate)//起始终止年月日设定
                        .setDividerColor(Color.RED)//当前选中日期线条颜色
                        .setSubmitColor(Color.RED)//确定按钮文字颜色
                        .setCancelColor(Color.RED)//取消按钮文字颜色
                        .build();
                pvTime.setDate(selectedDate);// 如果不设置的话，默认是系统时间*/
                pvTime.show();
                break;
            case R.id.tv_get_married_time://结婚日子的选择
                Calendar startDate2 = Calendar.getInstance();
                startDate2.set(1900, 0, 1);
                Calendar endDate = Calendar.getInstance();
                endDate.set(2100, 0, 1);
                //时间选择器 ，自定义布局
                pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        try {
                            Calendar setMarriedTime = Calendar.getInstance();
                            SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            setMarriedTime.setTime(chineseDateFormat.parse(getTime2(date)));
                            lunar = new ChinaDate(setMarriedTime);
                            tvGetMarriedTime.setText("" + lunar);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                })
                        .setDate(selectedDate)
                        .setRangDate(startDate2, endDate)
                        .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                            @Override
                            public void customLayout(final View v) {
                                final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                                tvSubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pvCustomLunar.returnData();
                                        pvCustomLunar.dismiss();
                                    }
                                });
                                ivCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pvCustomLunar.dismiss();
                                    }
                                });
                                //公农历切换
                                CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);

                                cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                        //自适应宽
                                        setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                                    }
                                });

                            }

                            /**
                             * 公农历切换后调整宽
                             * @param v
                             * @param yearWeight
                             * @param weight
                             */
                            private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                                ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                                View year = timePicker.getChildAt(0);
                                LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                                lp.weight = yearWeight;
                                year.setLayoutParams(lp);
                                for (int i = 1; i < timePicker.getChildCount(); i++) {
                                    View childAt = timePicker.getChildAt(i);
                                    LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                                    childLp.weight = weight;
                                    childAt.setLayoutParams(childLp);
                                }
                            }
                        })
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setDividerColor(Color.RED)
                        .build();
                pvCustomLunar.setDate(selectedDate);// 如果不设置的话，默认是系统时间*/
                pvCustomLunar.show();

                break;
            case R.id.tv_confirm://设置好了
                //取出上个页面保存的值（取数据）
                sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
                togetherT = sprfMain.getString("togetherTime", "");
                getMarriedT = sprfMain.getString("getMarriedTime", "");
                if (!TextUtils.isEmpty(togetherT) && !TextUtils.isEmpty(getMarriedT)) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
                    editorMain = sprfMain.edit();
                    editorMain.putString("togetherTime", togetherTime);
                    editorMain.putString("getMarriedTime", "" + lunar);
                    editorMain.commit();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, HomeActivity.class);
                    sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
                    editorMain = sprfMain.edit();
                    editorMain.putString("togetherTime", togetherTime);
                    editorMain.putString("getMarriedTime", "" + lunar);
                    editorMain.commit();
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private String getTime2(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
