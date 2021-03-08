package com.cwj.we.module.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
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
import com.cwj.we.R;
import com.cwj.we.bean.Day;
import com.cwj.we.bean.User;
import com.cwj.we.module.about.AboutActivity;
import com.cwj.we.module.activity.LoginActivity;
import com.cwj.we.module.activity.SetTimeActivity;
import com.cwj.we.utils.ChinaDate;
import com.cwj.we.utils.LunarUtils;
import com.cwj.we.utils.NotificationUtils;
import com.cwj.we.utils.PictureSelectorUtils;
import com.cwj.we.utils.TimeUtils;
import com.cwj.we.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 我们
 */
public class UsFragment extends Fragment {

    Unbinder unbinder;
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
    @BindView(R.id.hsv)
    HorizontalScrollView hsv;

    private String togetherTime, getMarriedTime, getMarriedTime2, getMarriedTime3, thisyeargetMarriedTime, nextyeargetMarriedTime, url;
    SharedPreferences sprfMain;
    private boolean isFrist = true;
    private boolean isFrist2 = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        queryPostAuthor();
        initView();
        return view;
    }

    /**
     * 查询一对一关联，查询当前用户下的日期
     */
    private void queryPostAuthor() {
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
                    if (e == null) {
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
                    } else {
                        startActivity(new Intent(getActivity(), SetTimeActivity.class));
                        getActivity().finish();
                    }
                }

            });
        } else {
            ToastUtil.showTextToast(getActivity(), "请先登录");
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        StatusBarUtil.setTranslucentForImageView(getActivity(), 0, clView);//沉浸状态栏

        sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
        //设置背景
        if (TextUtils.isEmpty(sprfMain.getString("path", ""))) {
            Glide.with(this).load(R.drawable.we_bg).into(ivBg);
        } else {
            Glide.with(this).load(Uri.fromFile(new File(sprfMain.getString("path", "")))).into(ivBg);
        }
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

            inHarnessYear = Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) - setTogetherTime;//在一起年数
            getMarriedYear = Integer.parseInt(TimeUtils.dateToString(nowTime, "yyyy")) - setGetMarriedTime;//结婚年数
            tvInHarnessYear.setText("" + inHarnessYear);

            //相恋日提醒
            if ((thisYearTogetherTimestamp - thisYearTimestamp) > 0) {
                tvFallInLove.setText("" + (thisYearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");//相恋纪念日
            } else if ((thisYearTogetherTimestamp - thisYearTimestamp) == 0) {
                tvFallInLove.setText("" + (thisYearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");
//                sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
                if (isFrist) {
                    NotificationUtils.showNotification(getActivity(), null, "今天是你们相恋的" + inHarnessYear + "周年，问候ta一下吧!", 0, "", 100, 0);
                    isFrist = false;
                }
            } else {
                tvFallInLove.setText("" + (nextyearTogetherTimestamp - thisYearTimestamp) / 1000 / 60 / 60 / 24 + "天");
            }

            //结婚纪念日提醒
            if ((thisYearGetMarriedTimestamp - getLunarTimestamp) > 0) {
                tvWeddingDay.setText("" + (thisYearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");//结婚纪念日
            } else if ((thisYearGetMarriedTimestamp - getLunarTimestamp) == 0) {
                tvWeddingDay.setText("" + (thisYearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");
                if (isFrist2) {
                    NotificationUtils.showNotification(getActivity(), null, "今天是你们的" + getMarriedYear + "周年结婚纪念日，记得给ta一个惊喜哦!", 1, "", 100, 0);
                    isFrist2 = false;
                }
            } else {
                tvWeddingDay.setText("" + (nextyearGetMarriedTimestamp - getLunarTimestamp) / 1000 / 60 / 60 / 24 + "天");
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
                    Glide.with(this).load(Uri.fromFile(new File(userIconPath))).into(ivBg);
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
                intent.putExtra("setTime", 2);
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
                    ToastUtil.showTextToast(getActivity(), "已经是原始背景,请勿重试！");
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示")
                            .setMessage("确定重置当前背景吗？")
                            .setCancelable(true)
                            .setNegativeButton("取消", (dialog, which) -> dialog.cancel())
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.cancel();
                                editorMain = sprfMain.edit();
                                editorMain.putString("path", "");
                                editorMain.commit();
                                Glide.with(getActivity()).load(R.drawable.we_bg).into(ivBg);
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
