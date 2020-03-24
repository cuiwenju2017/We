package com.cwj.love_lhh.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cwj.love_lhh.R;
import com.cwj.love_lhh.activity.AboutActivity;
import com.cwj.love_lhh.activity.HomeActivity;
import com.cwj.love_lhh.activity.SetTimeActivity;
import com.cwj.love_lhh.utils.ChinaDate;
import com.cwj.love_lhh.utils.ChinaDate2;
import com.cwj.love_lhh.utils.LunarUtils;
import com.cwj.love_lhh.utils.NotificationUtils;
import com.cwj.love_lhh.utils.PictureSelectorUtils;
import com.cwj.love_lhh.utils.TimeUtils;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//我们
public class UsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.wv)
    WebView wv;
    @BindView(R.id.tv)
    TextView tv;
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
    @BindView(R.id.cl_view)
    CoordinatorLayout clView;
    @BindView(R.id.tv_jh)
    TextView tvJh;
    @BindView(R.id.tv_y)
    TextView tvY;
    @BindView(R.id.tv_set_backgground)
    TextView tvSetBackgground;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_wedding_day)
    TextView tvWeddingDay;
    @BindView(R.id.tv_fall_in_love)
    TextView tvFallInLove;

    private String togetherTime, getMarriedTime, getMarriedTime2, getMarriedTime3, thisyeargetMarriedTime, nextyeargetMarriedTime;
    SharedPreferences sprfMain;
    private boolean isFrist = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        StatusBarUtil.setTranslucentForImageView(getActivity(), 0, clView);//沉浸状态栏
        // 设置WebView属性，能够执行Javascript脚本
        wv.getSettings().setJavaScriptEnabled(true);
        //语言设置防止加载乱码
        wv.getSettings().setDefaultTextEncodingName("GBK");
        // 即asserts文件夹下有一个color2.html
        wv.loadUrl("file:///android_asset/index.html");

        //取出上个页面保存的值（取数据）
        sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
        togetherTime = sprfMain.getString("togetherTime", "");
        getMarriedTime = sprfMain.getString("getMarriedTime", "");
        getMarriedTime2 = sprfMain.getString("getMarriedTime2", "");
        getMarriedTime3 = sprfMain.getString("getMarriedTime3", "");

        tvTime.setText(togetherTime + "我们在一起" + "\n\n" + getMarriedTime + "我们结婚");

        update();//显示数据

        //开始计时
        handler.postDelayed(runnable, 1000);
        //停止计时
//        handler.removeCallbacks(runnable);

        //设置背景
        if (TextUtils.isEmpty(sprfMain.getString("path", ""))) {
            wv.setVisibility(View.VISIBLE);
        } else {
            wv.setVisibility(View.GONE);
            Glide.with(this).load(Uri.fromFile(new File(sprfMain.getString("path", "")))).into(ivBg);
        }

        isFrist = true;
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void run() {
            update();//获取新数据
            handler.postDelayed(this, 1000); //n秒刷新一次
        }
    };

    private ChinaDate lunar;

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
            apartTime = (nowTime - startTime) / 1000 / 60 / 60 / 24;//天数
            remainderHour = (nowTime - startTime) / 1000 / 60 / 60 % 24;//小时
            remainderMinute = (nowTime - startTime) / 1000 / 60 % 60;//分钟
            remainderSecond = (nowTime - startTime) / 1000 % 60;//秒
            tv.setText(apartTime + "天" + remainderHour + "小时" + remainderMinute + "分" + remainderSecond + "秒");

            setTogetherTime = Integer.parseInt(togetherTime.substring(0, 4));//取出在一起年
            setGetMarriedTime = Integer.parseInt(getMarriedTime.substring(3, 7));//取出结婚年

            setTogetherDate = togetherTime.substring(4, 10);//取出在一起月日
            thisYearDate = TimeUtils.dateToString(nowTime, "yyyy-MM-dd");//当前年月日
            thisYearTogetherDate = TimeUtils.dateToString(nowTime, "yyyy") + setTogetherDate;//取出今年在一起的年月日
            nextyearTogetherDate = (Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) + 1) + setTogetherDate;//取出下一年在一起的年月日
            thisYearTimestamp = Long.parseLong(TimeUtils.dateToStamp2(thisYearDate));//当前年月日的时间戳
            thisYearTogetherTimestamp = Long.parseLong(TimeUtils.dateToStamp2(thisYearTogetherDate));//今年在一起的年月日的时间戳
            nextyearTogetherTimestamp = Long.parseLong(TimeUtils.dateToStamp2(nextyearTogetherDate));//下一年在一起的年月日的时间戳
            if ((thisYearTogetherTimestamp - thisYearTimestamp) > 0) {
                tvFallInLove.setText("" + (thisYearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");//相恋纪念日
            } else if ((thisYearTogetherTimestamp - thisYearTimestamp) == 0) {
                tvFallInLove.setText("" + (thisYearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");
                if (isFrist) {
                    NotificationUtils.showNotification(getActivity(), null, "今天是你们的相恋日，问候ta一下吧!", 0, "", 0, 0);
                    isFrist = false;
                }
            } else {
                tvFallInLove.setText("" + (nextyearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");
            }

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
                thisyeargetMarriedTime = LunarUtils.getTranslateSolarString(year, -month, monthDay);

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
            if ((thisYearGetMarriedTimestamp - getLunarTimestamp) > 0) {
                tvWeddingDay.setText("" + (thisYearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");//结婚纪念日
            } else if ((thisYearGetMarriedTimestamp - getLunarTimestamp) == 0) {
                tvWeddingDay.setText("" + (thisYearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");
                if (isFrist) {
                    NotificationUtils.showNotification(getActivity(), null, "今天是你们的结婚纪念日，记得给ta一个惊喜哦!", 0, "", 0, 0);
                    isFrist = false;
                }
            } else {
                tvWeddingDay.setText("" + (nextyearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");
            }

            inHarnessYear = Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) - setTogetherTime;//在一起年数
            getMarriedYear = Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) - setGetMarriedTime;//结婚年数
            tvInHarnessYear.setText("" + inHarnessYear);
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
                initView();
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_USER_ICON) {
                String userIconPath = PictureSelectorUtils.forResult(resultCode, data);
                if (userIconPath == null) {
                } else {
                    Glide.with(this).load(Uri.fromFile(new File(userIconPath))).into(ivBg);
                    wv.setVisibility(View.GONE);
                    ivBg.setVisibility(View.VISIBLE);
                    sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
                    editorMain = sprfMain.edit();
                    editorMain.putString("path", userIconPath);
                    editorMain.commit();
                }
            }
        }
    }

    public static final int REQUEST_SEARCH = 100;
    private static final int REQUEST_CODE_SELECT_USER_ICON = 100;

    @OnClick({R.id.tv_change_date, R.id.tv_about, R.id.tv_set_backgground, R.id.tv_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_date://日期修改
                Intent intent = new Intent(getActivity(), SetTimeActivity.class);
                startActivityForResult(intent, REQUEST_SEARCH);
                break;
            case R.id.tv_about://关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.tv_set_backgground://设置背景
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
                break;
            case R.id.tv_reset://重置背景
                if (TextUtils.isEmpty(sprfMain.getString("path", ""))) {
                    Toast.makeText(getActivity(), "已经是原始背景,请勿重试！", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示")
                            .setMessage("确定重置当前背景吗？")
                            .setCancelable(true)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    wv.setVisibility(View.VISIBLE);
                                    ivBg.setVisibility(View.GONE);
                                    editorMain = sprfMain.edit();
                                    editorMain.putString("path", "");
                                    editorMain.commit();
                                }
                            })
                            .create();
                    alertDialog.show();
                    //设置颜色和弹窗宽度一定要放在show之下，要不然会报错或者不生效
                    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                }
                break;
        }
    }
}
