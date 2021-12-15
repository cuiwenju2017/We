package com.cwj.we.module.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.cwj.we.R;
import com.cwj.we.base.BaseFragment;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.Day;
import com.cwj.we.bean.EventBG;
import com.cwj.we.bean.User;
import com.cwj.we.common.GlobalConstant;
import com.cwj.we.module.about.AboutActivity;
import com.cwj.we.module.activity.LoginActivity;
import com.cwj.we.module.activity.SetTimeActivity;
import com.cwj.we.utils.ActivityCollector;
import com.cwj.we.utils.LunarUtils;
import com.cwj.we.utils.NotificationUtils;
import com.cwj.we.utils.OneClickThree;
import com.cwj.we.utils.PictureSelectorUtils;
import com.cwj.we.utils.TimeUtils;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.samlss.broccoli.Broccoli;
import me.samlss.broccoli.BroccoliGradientDrawable;
import me.samlss.broccoli.PlaceholderParameter;

/**
 * 我们
 */
public class UsFragment extends BaseFragment {

    @BindView(R.id.tv_together_time)
    TextView tv_together_time;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_in_harness_year)
    TextView tvInHarnessYear;
    @BindView(R.id.tv_get_married_year)
    TextView tvGetMarriedYear;
    @BindView(R.id.tv_change_date)
    TextView tvChangeDate;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_jh)
    TextView tvJh;
    @BindView(R.id.tv_y)
    TextView tvY;
    @BindView(R.id.tv_set_backgground)
    TextView tvSetBackgground;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_wedding_day)
    TextView tvWeddingDay;
    @BindView(R.id.tv_fall_in_love)
    TextView tvFallInLove;
    @BindView(R.id.hsv)
    HorizontalScrollView hsv;
    @BindView(R.id.tv_wedding_day_tip)
    TextView tvWeddingDayTip;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.ll_zaiyiqi_year)
    LinearLayout llZaiyiqiYear;
    @BindView(R.id.ll_jiehun_year)
    LinearLayout llJiehunYear;
    @BindView(R.id.ll_jiehunjinian)
    LinearLayout llJiehunjinian;
    @BindView(R.id.ll_zaiyiqijinian)
    LinearLayout llZaiyiqijinian;
    @BindView(R.id.ll_zaiyiqi)
    LinearLayout llZaiyiqi;
    @BindView(R.id.tv_yincang1)
    TextView tvYincang1;
    @BindView(R.id.tv_yincang2)
    TextView tvYincang2;
    @BindView(R.id.tv_yincang3)
    TextView tvYincang3;
    @BindView(R.id.tv_yincang4)
    TextView tvYincang4;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.ic)
    View ic;

    private String togetherTime, getMarriedTime, getMarriedTime2, getMarriedTime3, thisyeargetMarriedTime, nextyeargetMarriedTime;
    SharedPreferences sprfMain;
    private boolean isFrist = true;
    private boolean isFrist2 = true;
    private Broccoli mBroccoli;
    private BasePopupView popupView;
    private int ABOUT = 201;
    private Intent intent;

    private void initPlaceholders() {
        if (mBroccoli != null) {
            mBroccoli.clearAllPlaceholders();
        }
        mBroccoli = new Broccoli();

        mBroccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(llZaiyiqi)
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#33000000"),
                        Color.parseColor("#ffffff"), 0, 1000, new LinearInterpolator()))
                .build());

        mBroccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(tv_together_time)
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#33000000"),
                        Color.parseColor("#ffffff"), 0, 1000, new LinearInterpolator()))
                .build());

        mBroccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(tvTime)
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#33000000"),
                        Color.parseColor("#ffffff"), 0, 1000, new LinearInterpolator()))
                .build());

        mBroccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(llZaiyiqiYear)
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#33000000"),
                        Color.parseColor("#ffffff"), 0, 1000, new LinearInterpolator()))
                .build());

        mBroccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(llJiehunYear)
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#33000000"),
                        Color.parseColor("#ffffff"), 0, 1000, new LinearInterpolator()))
                .build());

        mBroccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(llJiehunjinian)
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#33000000"),
                        Color.parseColor("#ffffff"), 0, 1000, new LinearInterpolator()))
                .build());

        mBroccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(llZaiyiqijinian)
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#33000000"),
                        Color.parseColor("#ffffff"), 0, 1000, new LinearInterpolator()))
                .build());
        mBroccoli.show();
    }

    /**
     * 查询一对一关联，查询当前用户下的日期
     */
    private void queryPostAuthor() {
        tvYincang1.setVisibility(View.GONE);
        tvYincang2.setVisibility(View.GONE);
        tvYincang3.setVisibility(View.GONE);
        tvYincang4.setVisibility(View.GONE);
        tvJh.setVisibility(View.GONE);
        tvY.setVisibility(View.GONE);
        tvWeddingDayTip.setVisibility(View.GONE);
        initPlaceholders();

        if (BmobUser.isLogin()) {
            BmobQuery<Day> query = new BmobQuery<>();
            query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("author");
            query.findObjects(new FindListener<Day>() {

                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void done(List<Day> object, BmobException e) {
                    popupView.smartDismiss();
                    if (e == null && object.size() > 0) {
                        mBroccoli.clearAllPlaceholders();
                        tvYincang1.setVisibility(View.VISIBLE);
                        tvYincang2.setVisibility(View.VISIBLE);
                        tvYincang3.setVisibility(View.VISIBLE);
                        tvYincang4.setVisibility(View.VISIBLE);
                        tvJh.setVisibility(View.VISIBLE);
                        tvY.setVisibility(View.VISIBLE);
                        tvWeddingDayTip.setVisibility(View.VISIBLE);

                        rl.setVisibility(View.VISIBLE);
                        ic.setVisibility(View.GONE);
                        togetherTime = object.get(0).getTogetherTime();
                        getMarriedTime = object.get(0).getGetMarriedTime();
                        getMarriedTime2 = object.get(0).getGetMarriedTime2();
                        getMarriedTime3 = object.get(0).getGetMarriedTime3();
                        tvTime.setText(togetherTime + "我们在一起" + "\n\n" + getMarriedTime + "我们结婚");
                        update();//显示数据
                        //开始计时
                        handler.postDelayed(runnable, 1000);
                        //停止计时
                        //handler.removeCallbacks(runnable);
                        isFrist = true;
                        isFrist2 = true;
                    } else if (e == null && object.size() < 1) {//无日期去设置日期
                        startActivity(new Intent(getActivity(), SetTimeActivity.class));
                        getActivity().finish();
                    } else {
                        ic.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                        TextView tv = ic.findViewById(R.id.tv);
                        tv.setText("数据获取失败，请重试");
                    }
                }
            });
        } else {
            popupView.smartDismiss();
            ToastUtil.showTextToast(getActivity(), "请先登录");
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_us;
    }

    protected void initView() {
        EventBus.getDefault().register(this);
        queryPostAuthor();
        sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
        popupView = new XPopup.Builder(getActivity())
                .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                .asLoading("")
                .show();
    }

    @Override
    protected void initData() {
        ic.setOnClickListener(v -> {//刷新
            queryPostAuthor();
            popupView = new XPopup.Builder(getActivity())
                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                    .asLoading("")
                    .show();
        });
    }

    public static Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void run() {
            update();//获取新数据
            handler.postDelayed(this, 1000); //n秒刷新一次
        }
    };

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void update() {
        long nowTime, startTime, apartTime, remainderHour, remainderMinute, remainderSecond, thisYearTogetherTimestamp,
                nextyearTogetherTimestamp, thisYearGetMarriedTimestamp, nextyearGetMarriedTimestamp, getLunarTimestamp = 0, thisYearTimestamp;
        int inHarnessYear, getMarriedYear, setTogetherTime, setGetMarriedTime;
        String setTogetherDate, thisYearTogetherDate, nextyearTogetherDate, setGetMarriedDate, thisYearGetMarriedDate,
                nextyearGetMarriedDate, getLunarnowTime = null, thisYearDate;
        try {
            nowTime = TimeUtils.getTimeStame();//当前时间戳
            startTime = Long.parseLong(TimeUtils.dateToStamp2(togetherTime));//在一起的时间
            apartTime = (nowTime - startTime) / (1000 * 60 * 60 * 24);//天数
            remainderHour = ((nowTime - startTime) / (1000 * 60 * 60)) % 24;//小时
            remainderMinute = ((nowTime - startTime) / (1000 * 60)) % 60;//分钟
            remainderSecond = ((nowTime - startTime) / 1000) % 60;//秒
            tv_together_time.setText(apartTime + "天" + remainderHour + "小时" + remainderMinute + "分" + remainderSecond + "秒");

            setTogetherTime = Integer.parseInt(togetherTime.substring(0, 4));//取出在一起年
            setGetMarriedTime = Integer.parseInt(getMarriedTime.substring(3, 7));//取出结婚年
            setTogetherDate = togetherTime.substring(4, 10);//取出在一起月日
            thisYearDate = TimeUtils.dateToString(nowTime, "yyyy-MM-dd");//当前年月日
            thisYearTogetherDate = TimeUtils.dateToString(nowTime, "yyyy") + setTogetherDate;//取出今年在一起的年月日
            nextyearTogetherDate = (Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) + 1) + setTogetherDate;//取出下一年在一起的年月日
            thisYearTimestamp = Long.parseLong(TimeUtils.dateToStamp2(thisYearDate));//当前年月日的时间戳
            thisYearTogetherTimestamp = Long.parseLong(TimeUtils.dateToStamp2(thisYearTogetherDate));//今年在一起的年月日的时间戳
            nextyearTogetherTimestamp = Long.parseLong(TimeUtils.dateToStamp2(nextyearTogetherDate));//下一年在一起的年月日的时间戳

            try {
                getLunarnowTime = TimeUtils.dateToString(nowTime, "yyyy-MM-dd");
                getLunarTimestamp = Long.parseLong(TimeUtils.dateToStamp2(getLunarnowTime));//得到当前的时间戳
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ("闰".equals(getMarriedTime3.substring(5, 6))) {//2020-闰04-01
                thisYearGetMarriedDate = TimeUtils.dateToString(nowTime, "yyyy") + "-" + getMarriedTime3.substring(6, 11);//取出今年结婚的年月日
                nextyearGetMarriedDate = (Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) + 1) + "-" + getMarriedTime3.substring(6, 11);//取出下一年结婚的年月日

                int year = Integer.parseInt(thisYearGetMarriedDate.substring(0, 4));
                int month = Integer.parseInt(thisYearGetMarriedDate.substring(5, 7));
                int monthDay = Integer.parseInt(thisYearGetMarriedDate.substring(8, 10));
                thisyeargetMarriedTime = LunarUtils.getTranslateSolarString(year, month, monthDay);

                int nextyear = Integer.parseInt(nextyearGetMarriedDate.substring(0, 4));
                int nextmonth = Integer.parseInt(nextyearGetMarriedDate.substring(5, 7));
                int nextmonthDay = Integer.parseInt(nextyearGetMarriedDate.substring(8, 10));
                nextyeargetMarriedTime = LunarUtils.getTranslateSolarString(nextyear, nextmonth, nextmonthDay);
            } else {//2020-04-01
                thisYearGetMarriedDate = TimeUtils.dateToString(nowTime, "yyyy") + getMarriedTime3.substring(4, 10);//取出今年结婚的年月日
                nextyearGetMarriedDate = (Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) + 1) + getMarriedTime3.substring(4, 10);//取出下一年结婚的年月日

                int year = Integer.parseInt(thisYearGetMarriedDate.substring(0, 4));
                int month = Integer.parseInt(thisYearGetMarriedDate.substring(5, 7));
                int monthDay = Integer.parseInt(thisYearGetMarriedDate.substring(8, 10));
                thisyeargetMarriedTime = LunarUtils.getTranslateSolarString(year, month, monthDay);

                int nextyear = Integer.parseInt(nextyearGetMarriedDate.substring(0, 4));
                int nextmonth = Integer.parseInt(nextyearGetMarriedDate.substring(5, 7));
                int nextmonthDay = Integer.parseInt(nextyearGetMarriedDate.substring(8, 10));
                nextyeargetMarriedTime = LunarUtils.getTranslateSolarString(nextyear, nextmonth, nextmonthDay);
            }
            thisYearGetMarriedTimestamp = Long.parseLong(TimeUtils.dateToStamp2(thisyeargetMarriedTime));//今年结婚的年月日的时间戳
            nextyearGetMarriedTimestamp = Long.parseLong(TimeUtils.dateToStamp2(nextyeargetMarriedTime));//下一年结婚的年月日的时间戳

            inHarnessYear = Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) - setTogetherTime;//在一起年数
            getMarriedYear = Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) - setGetMarriedTime;//结婚年数
            tvInHarnessYear.setText("" + inHarnessYear);

            //相恋日提醒
            if ((thisYearTogetherTimestamp - thisYearTimestamp) > 0) {
                tvFallInLove.setText("" + (thisYearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");//相恋纪念日
            } else if ((thisYearTogetherTimestamp - thisYearTimestamp) == 0) {
                tvFallInLove.setText("" + (thisYearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");
                if (isFrist) {
                    NotificationUtils.showNotification(getActivity(), null, "今天是你们相恋的" + inHarnessYear + "周年，问候ta一下吧!", 0, "", 100, 0);
                    isFrist = false;
                }
            } else {
                tvFallInLove.setText("" + (nextyearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");
            }

            long getMarriedTimestamp = Long.parseLong(TimeUtils.dateToStamp2(getMarriedTime2));//阳历结婚时间毫秒数
            if ((thisYearTimestamp - getMarriedTimestamp) >= 0) {
                tvGetMarriedYear.setText("" + getMarriedYear);
                tvJh.setVisibility(View.VISIBLE);
                tvY.setVisibility(View.VISIBLE);
            } else {
                tvGetMarriedYear.setText("还有" + (getMarriedTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天我们就结婚啦");
                tvJh.setVisibility(View.GONE);
                tvY.setVisibility(View.GONE);
            }

            //结婚纪念日提醒
            if ((thisYearGetMarriedTimestamp - getLunarTimestamp) > 0 && ((getMarriedTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24) < 0) {
                tvWeddingDayTip.setText("距结婚纪念日还剩");
                tvWeddingDay.setText("" + (thisYearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");//结婚纪念日
            } else if ((thisYearGetMarriedTimestamp - getLunarTimestamp) == 0) {
                tvWeddingDayTip.setText("距结婚纪念日还剩");
                tvWeddingDay.setText("" + (thisYearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");
                if (isFrist2) {
                    NotificationUtils.showNotification(getActivity(), null, "今天是你们的" + getMarriedYear + "周年结婚纪念日，记得给ta一个惊喜哦!", 1, "", 100, 0);
                    isFrist2 = false;
                }
            } else if (((getMarriedTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24) > 0) {
                tvWeddingDayTip.setText("距结婚还剩");
                tvWeddingDay.setText("" + (getMarriedTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");//结婚纪念日
            } else {
                tvWeddingDayTip.setText("距结婚纪念日还剩");
                tvWeddingDay.setText("" + (nextyearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void doCode() {
        PictureSelectorUtils.ofImage(this, REQUEST_CODE_SELECT_USER_ICON);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //执行代码,这里是已经申请权限成功了,可以不用做处理
                    doCode();
                } else {
                    Toast.makeText(getActivity(), "权限申请失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    SharedPreferences.Editor editorMain;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//判断是否返回成功
            if (requestCode == REQUEST_SEARCH) {//判断来自哪个Activity
                queryPostAuthor();//刷新数据
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_USER_ICON) {
                String userIconPath = PictureSelectorUtils.forResult(resultCode, data);
                if (userIconPath == null) {
                } else {
                    EventBG eventBG = new EventBG("EVENT_SZ_BG", userIconPath);
                    EventBus.getDefault().post(eventBG);

                    sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
                    editorMain = sprfMain.edit();
                    editorMain.putString("path", userIconPath);
                    editorMain.commit();
                }
            }
        }

        if (requestCode == ABOUT && resultCode == Activity.RESULT_OK) {
            //停止计时
            handler.removeCallbacks(runnable);
            BmobUser.logOut();//退出登录，同时清除缓存用户对象。
            startActivity(new Intent(getActivity(), LoginActivity.class));
            //结束之前所有的Activity
            ActivityCollector.finishall();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBG eventBG) {
        switch (eventBG.getType()) {
            case GlobalConstant.ChangePassword_SUCCESS://修改密码成功
                //停止计时
                handler.removeCallbacks(runnable);
                break;
        }
    }

    public static final int REQUEST_SEARCH = 100;
    private static final int REQUEST_CODE_SELECT_USER_ICON = 100;

    @OnClick({R.id.tv_change_date, R.id.tv_about, R.id.tv_set_backgground, R.id.tv_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_date://日期修改
                if (!OneClickThree.isFastClick()) {
                    intent = new Intent(getActivity(), SetTimeActivity.class);
                    intent.putExtra("setTime", 2);
                    startActivityForResult(intent, REQUEST_SEARCH);
                }
                break;
            case R.id.tv_about://关于
                if (!OneClickThree.isFastClick()) {
                    intent = new Intent(getActivity(), AboutActivity.class);
                    startActivityForResult(intent, ABOUT);
                }
                break;
            case R.id.tv_set_backgground://设置背景
                if (!OneClickThree.isFastClick()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //没有权限则申请权限
                            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            //有权限直接执行,docode()不用做处理
                            doCode();
                        }
                    } else {
                        //小于6.0，不用申请权限，直接执行
                        doCode();
                    }
                }
                break;
            case R.id.tv_reset://重置背景
                if (TextUtils.isEmpty(sprfMain.getString("path", ""))) {
                    ToastUtil.showTextToast(getActivity(), "已经是原始背景,请勿重试！");
                } else {
                    if (!OneClickThree.isFastClick()) {
                        new XPopup.Builder(getActivity()).asConfirm("提示", "确定重置当前背景吗？",
                                () -> {
                                    editorMain = sprfMain.edit();
                                    editorMain.putString("path", "");
                                    editorMain.commit();

                                    EventBG eventBG = new EventBG("EVENT_CZ_BG", "");
                                    EventBus.getDefault().post(eventBG);
                                })
                                .show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)  //状态栏字体是深色，不写默认为亮色
                .init();
    }
}
