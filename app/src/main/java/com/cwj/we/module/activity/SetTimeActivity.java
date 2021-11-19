package com.cwj.we.module.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.Day;
import com.cwj.we.bean.User;
import com.cwj.we.module.main.HomeActivity;
import com.cwj.we.utils.ChinaDate;
import com.cwj.we.utils.ChinaDate2;
import com.cwj.we.utils.OneClickThree;
import com.cwj.we.utils.TimeUtils;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SetTimeActivity extends BaseActivity {

    @BindView(R.id.tv_together_time)
    TextView tvTogetherTime;
    @BindView(R.id.tv_get_married_time)
    TextView tvGetMarriedTime;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Calendar selectedDate;
    private TimePickerView pvTime, pvCustomLunar;
    private String togetherTime, getMarriedTime, tT, gT, gT2, getMarriedTime3, objectId, userObjectId;
    private ChinaDate lunar;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_time;
    }

    @Override
    public void initData() {

    }

    private int setTime;

    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
        selectedDate = Calendar.getInstance();//系统当前时间
        queryPostAuthor();
        setTime = getIntent().getIntExtra("setTime", 0);
    }

    /**
     * 添加一对一关联，当前用户添加日期
     */
    private void savePost() {
        if (BmobUser.isLogin()) {
            Day day = new Day();
            day.setTogetherTime(togetherTime);
            day.setGetMarriedTime("" + lunar);
            day.setGetMarriedTime2(getMarriedTime);
            day.setGetMarriedTime3(getMarriedTime3);
            //添加一对一关联，用户关联日期
            day.setAuthor(BmobUser.getCurrentUser(User.class));
            day.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    popupView.smartDismiss(); //会等待弹窗的开始动画执行完毕再进行消失，可以防止接口调用过快导致的动画不完整。
                    if (e == null) {
                    } else {
                        ToastUtil.showTextToast(SetTimeActivity.this, e.getMessage());
                    }
                }
            });
        } else {
            ToastUtil.showTextToast(SetTimeActivity.this, "请先登录");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    /**
     * 查询一对一关联，查询当前用户下的日期
     */
    private void queryPostAuthor() {
        popupView = new XPopup.Builder(this)
                .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                .asLoading("")
                .show();
        if (BmobUser.isLogin()) {
            BmobQuery<Day> query = new BmobQuery<>();
            query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("author");
            query.findObjects(new FindListener<Day>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void done(List<Day> object, BmobException e) {
                    if (e == null && object.size() > 0) {
                        objectId = object.get(0).getObjectId();
                        tT = object.get(0).getTogetherTime();
                        gT = object.get(0).getGetMarriedTime();
                        gT2 = object.get(0).getGetMarriedTime2();
                        togetherTime = tT;
                        Calendar setMarriedTime = Calendar.getInstance();
                        SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            setMarriedTime.setTime(chineseDateFormat.parse(gT2));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        lunar = new ChinaDate(setMarriedTime);
                        getMarriedTime = gT2;
                        try {
                            getMarriedTime3 = ChinaDate2.solarToLunar(getMarriedTime, true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        long nowTime = TimeUtils.getTimeStame();
                        togetherTime = TimeUtils.dateToString(nowTime, "yyyy-MM-dd");
                        lunar = new ChinaDate(selectedDate);
                        getMarriedTime = TimeUtils.dateToString(nowTime, "yyyy-MM-dd");
                        try {
                            getMarriedTime3 = ChinaDate2.solarToLunar(getMarriedTime, true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        tT = togetherTime;
                        gT2 = getMarriedTime3;
                    }
                    tvTogetherTime.setText(togetherTime);
                    tvGetMarriedTime.setText("" + lunar);
                    popupView.smartDismiss(); //会等待弹窗的开始动画执行完毕再进行消失，可以防止接口调用过快导致的动画不完整。
                }
            });
        } else {
            popupView.smartDismiss(); //会等待弹窗的开始动画执行完毕再进行消失，可以防止接口调用过快导致的动画不完整。
            ToastUtil.showTextToast(SetTimeActivity.this, "请先登录");
            startActivity(new Intent(SetTimeActivity.this, LoginActivity.class));
            finish();
        }
    }

    SharedPreferences sprfMain;

    /**
     * 修改一对一关联，修改日期
     */
    private void updatePostAuthor() {
        sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
        userObjectId = sprfMain.getString("userObjectId", "");
        User user = new User();
        user.setObjectId(userObjectId);
        Day day = new Day();
        day.setObjectId(objectId);
        day.setTogetherTime(togetherTime);
        day.setGetMarriedTime("" + lunar);
        day.setGetMarriedTime2(getMarriedTime);
        day.setGetMarriedTime3(getMarriedTime3);
        //修改一对一关联，修改帖子和用户的关系
        day.setAuthor(user);
        day.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                popupView.smartDismiss(); //会等待弹窗的开始动画执行完毕再进行消失，可以防止接口调用过快导致的动画不完整。
                if (e == null) {
                    ToastUtil.showTextToast(SetTimeActivity.this, "修改成功");
                    Intent intent = new Intent(SetTimeActivity.this, HomeActivity.class);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.showTextToast(SetTimeActivity.this, e.getMessage());
                }
            }
        });
    }

    private SimpleDateFormat chineseDateFormat;
    private BasePopupView popupView;

    @OnClick({R.id.tv_together_time, R.id.tv_get_married_time, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_together_time://在一起日子的选择
                Calendar startDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                startDate.set(1900, 0, 1);
                //时间选择器
                pvTime = new TimePickerBuilder(this, (date, v) -> {
                    togetherTime = getTime2(date);
                    tvTogetherTime.setText(togetherTime);
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setRangDate(startDate, selectedDate)//起始终止年月日设定
                        .setDividerColor(Color.RED)//当前选中日期线条颜色
                        .setSubmitColor(Color.RED)//确定按钮文字颜色
                        .setCancelColor(Color.RED)//取消按钮文字颜色
                        .build();

                Calendar settTTime = Calendar.getInstance();
                chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    settTTime.setTime(chineseDateFormat.parse(tT));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                pvTime.setDate(TextUtils.isEmpty(tT) ? selectedDate : settTTime);// 如果不设置的话，默认是系统时间*/
                pvTime.show();
                break;
            case R.id.tv_get_married_time://结婚日子的选择
                Calendar startDate2 = Calendar.getInstance();
                startDate2.set(1900, 0, 1);
                Calendar endDate = Calendar.getInstance();
                endDate.set(2100, 0, 1);
                //时间选择器 ，自定义布局
                pvCustomLunar = new TimePickerBuilder(this, (date, v) -> {//选中事件回调
                    getMarriedTime = getTime2(date);
                    try {
                        getMarriedTime3 = ChinaDate2.solarToLunar(getMarriedTime, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Calendar setMarriedTime = Calendar.getInstance();
                        chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        setMarriedTime.setTime(chineseDateFormat.parse(getTime2(date)));
                        lunar = new ChinaDate(setMarriedTime);
                        tvGetMarriedTime.setText("" + lunar);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                })
                        .setDate(selectedDate)
                        .setRangDate(startDate2, endDate)
                        .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                            @Override
                            public void customLayout(final View v) {
                                final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                                tvSubmit.setOnClickListener(v1 -> {
                                    pvCustomLunar.returnData();
                                    pvCustomLunar.dismiss();
                                });
                                ivCancel.setOnClickListener(v12 -> pvCustomLunar.dismiss());
                                //公农历切换
                                CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);

                                cb_lunar.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                    pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                    //自适应宽
                                    setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
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

                Calendar setgTTime = Calendar.getInstance();
                chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    setgTTime.setTime(chineseDateFormat.parse(gT2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (setTime == 2) {
                    pvCustomLunar.setDate(TextUtils.isEmpty(gT2) ? selectedDate : setgTTime);// 如果不设置的话，默认是系统时间*/
                } else {
                    pvCustomLunar.setDate(selectedDate);// 如果不设置的话，默认是系统时间*/
                }
                pvCustomLunar.show();
                break;
            case R.id.tv_confirm://设置好了
                long startTime, getMarried;
                try {
                    startTime = Long.parseLong(TimeUtils.dateToStamp2(togetherTime));//在一起时间戳
                    getMarried = Long.parseLong(TimeUtils.dateToStamp2(getMarriedTime));//结婚时间戳

                    if (startTime > getMarried) {
                        Toast.makeText(this, "结婚时间不能早于在一起的时间", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!OneClickThree.isFastClick()) {
                            popupView = new XPopup.Builder(this)
                                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                                    .asLoading("")
                                    .show();
                            if (TextUtils.isEmpty(tT) || TextUtils.isEmpty(gT)) {
                                Intent intent = new Intent(this, HomeActivity.class);
                                savePost();//新增数据
                                startActivity(intent);
                                finish();
                            } else {
                                updatePostAuthor();//修改数据
                            }
                        } else {
                            ToastUtil.showTextToast(this, "请不要频繁操作");
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
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
