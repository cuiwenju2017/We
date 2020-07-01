package com.cwj.love_lhh.module.lpclock;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.base.BaseActivity;
import com.cwj.love_lhh.base.BasePresenter;
import com.cwj.love_lhh.utils.DateUtils;
import com.cwj.love_lhh.view.AutoRotateDayView;
import com.cwj.love_lhh.view.AutoRotateHoursView;
import com.cwj.love_lhh.view.AutoRotateMinuteView;
import com.cwj.love_lhh.view.AutoRotateMonthView;
import com.cwj.love_lhh.view.AutoRotateSecondView;
import com.cwj.love_lhh.view.AutoRotateWeekView;
import com.jaeger.library.StatusBarUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 轮盘时钟
 */
public class LPClockActivity extends BaseActivity {

    @BindView(R.id.cl_view)
    CoordinatorLayout clView;
    private AutoRotateSecondView timeView_second;
    private AutoRotateMinuteView timeView_minute;
    private AutoRotateHoursView timeView_hours;
    private AutoRotateWeekView timeView_week;
    private AutoRotateDayView timeView_day;
    private AutoRotateMonthView timeView_month;
    private TextView timeView_year;
    private View v_dividing2;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_l_p_clock;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, clView);//沉浸状态栏
        timeView_second = (AutoRotateSecondView) findViewById(R.id.timeView_second);
        timeView_minute = (AutoRotateMinuteView) findViewById(R.id.timeView_minute);
        timeView_hours = (AutoRotateHoursView) findViewById(R.id.timeView_hours);
        timeView_week = findViewById(R.id.timeView_week);
        timeView_day = (AutoRotateDayView) findViewById(R.id.timeView_day);
        timeView_month = (AutoRotateMonthView) findViewById(R.id.timeView_month);
        timeView_year = (TextView) findViewById(R.id.timeView_year);
        v_dividing2 = findViewById(R.id.v_dividing2);

        initListener();
        setData();
    }

    public void initListener() {
        //设置因月份的改变，改变年和天的监听
        timeView_month.setChangeTimeListener(new AutoRotateMonthView.OnChangeTimeListener() {
            @Override
            public void changeYear(final int year) {
                timeView_year.post(new Runnable() {
                    @Override
                    public void run() {
                        timeView_year.setText("" + year);
                    }
                });
            }

            @Override
            public void changeDay(final int count) {
                timeView_day.post(new Runnable() {
                    @Override
                    public void run() {
                        timeView_day.setTime(1, count);
                    }
                });
            }
        });
        //秒钟绘制完后，设置水平渐变条的宽度
        final ViewTreeObserver viewTreeObserver = timeView_second.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeOnPreDrawListener(this);
                }
                ViewGroup.LayoutParams layoutParams = v_dividing2.getLayoutParams();
                layoutParams.width = timeView_second.getViewMaxWidth();
                v_dividing2.setLayoutParams(layoutParams);
                return true;
            }
        });
    }

    private void setData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

//        Log.i("Calendar_years", "-" + year);
//        Log.i("Calendar_month", "-" + month);
//        Log.i("Calendar_week", "-" + week);
//        Log.i("Calendar_day", "-" + day);
//        Log.i("Calendar_hours", "-" + hours);
//        Log.i("Calendar_minutes", "-" + minutes);
//        Log.i("Calendar_seconds", "-" + seconds);

        timeView_second.setTime(seconds).start();
        timeView_minute.setTime(minutes).start();
        timeView_hours.setTime(hours).start();
        timeView_week.setTime(week).start();
        timeView_day.setTime(day, DateUtils.getDays(year, month)).start();
        timeView_month.setTime(month, DateUtils.getDays(year, month)).start();

        timeView_year.setText("" + year + "年");
    }

    @Override
    protected void initData() {

    }
}
