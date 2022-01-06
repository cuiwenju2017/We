package com.cwj.we.module.tianqi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.bean.WeatherBean;
import com.cwj.we.common.GlobalConstant;
import com.cwj.we.http.API;
import com.cwj.we.utils.TimeUtils;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.rainy.weahter_bg_plug.WeatherBg;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 天气
 */
public class TianqiActivity extends BaseActivity<TianqiPrensenter> implements TianqiView {

    private static final int ADDRESS_CHOOSE = 201;
    @BindView(R.id.wb)
    WeatherBg wb;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_day_weather1)
    TextView tvDayWeather1;
    @BindView(R.id.tv_degree1)
    TextView tvDegree1;
    @BindView(R.id.tv_day_wind_direction1)
    TextView tvDayWindDirection1;
    @BindView(R.id.tv_time1)
    TextView tvTime1;
    @BindView(R.id.tv_day_weather2)
    TextView tvDayWeather2;
    @BindView(R.id.tv_degree2)
    TextView tvDegree2;
    @BindView(R.id.tv_day_wind_direction2)
    TextView tvDayWindDirection2;
    @BindView(R.id.tv_time2)
    TextView tvTime2;
    @BindView(R.id.tv_day_weather3)
    TextView tvDayWeather3;
    @BindView(R.id.tv_degree3)
    TextView tvDegree3;
    @BindView(R.id.tv_day_wind_direction3)
    TextView tvDayWindDirection3;
    @BindView(R.id.tv_time3)
    TextView tvTime3;
    @BindView(R.id.tv_day_weather4)
    TextView tvDayWeather4;
    @BindView(R.id.tv_degree4)
    TextView tvDegree4;
    @BindView(R.id.tv_day_wind_direction4)
    TextView tvDayWindDirection4;
    @BindView(R.id.tv_time4)
    TextView tvTime4;
    @BindView(R.id.tv_day_weather5)
    TextView tvDayWeather5;
    @BindView(R.id.tv_degree5)
    TextView tvDegree5;
    @BindView(R.id.tv_day_wind_direction5)
    TextView tvDayWindDirection5;
    @BindView(R.id.tv_time5)
    TextView tvTime5;
    @BindView(R.id.tv_day_weather6)
    TextView tvDayWeather6;
    @BindView(R.id.tv_degree6)
    TextView tvDegree6;
    @BindView(R.id.tv_day_wind_direction6)
    TextView tvDayWindDirection6;
    @BindView(R.id.tv_time6)
    TextView tvTime6;
    @BindView(R.id.tv_day_weather7)
    TextView tvDayWeather7;
    @BindView(R.id.tv_degree7)
    TextView tvDegree7;
    @BindView(R.id.tv_day_wind_direction7)
    TextView tvDayWindDirection7;
    @BindView(R.id.tv_time7)
    TextView tvTime7;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    private String province, city, county;
    private Intent intent;

    @Override
    protected TianqiPrensenter createPresenter() {
        return new TianqiPrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tianqi;
    }

    @Override
    public void initData() {
        province = API.kv.decodeString(GlobalConstant.province);
        city = API.kv.decodeString(GlobalConstant.city);
        county = API.kv.decodeString(GlobalConstant.county);
        if (province != null && city != null && county != null) {
            tvAddress.setText(county);
            presenter.weather(province, city, county);//腾讯天气
        } else {
            tvAddress.setText("请选择区域");
        }
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)  //状态栏字体是深色，不写默认为亮色
                .titleBar(ll)//解决状态栏和布局重叠问题，任选其一
                .init();
        wb.changeWeather("");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void weatherData(WeatherBean.DataBean data) {
        if (data != null && data.getForecast_24h() != null && data.getForecast_24h().get_$1() != null) {
            nsv.setVisibility(View.VISIBLE);
            if ("大雨".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                // heavyRainy：大雨（雷雨）
                // middleRainy：中雨
                // lightRainy：小雨
                // heavySnow：暴雪
                // middleSnow：中雪
                // lightSnow：小雪
                // thunder：雷（雷雨）
                // sunny：晴
                // cloudy：多云
                // overcast：灰蒙蒙（阴）
                // foggy：雾
                // hazy：朦胧（大雾）
                // dusty：雾霾
                // sunnyNight：晴朗的夜晚
                // cloudyNight：多云夜晚
                wb.changeWeather("heavyRainy");
            } else if ("中雨".equals(data.getForecast_24h().get_$1().getDay_weather()) || "中到大雨".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("middleRainy");
            } else if ("小雨".equals(data.getForecast_24h().get_$1().getDay_weather()) || "小到中雨".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("lightRainy");
            } else if ("暴雪".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("heavySnow");
            } else if ("中雪".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("middleSnow");
            } else if ("小雪".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("lightSnow");
            } else if ("雷阵雨".equals(data.getForecast_24h().get_$1().getDay_weather()) || "阵雨".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("thunder");
            } else if ("晴".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                int time = Integer.parseInt(TimeUtils.dateToString(TimeUtils.getTimeStame(), "HH"));
                if (time == 0 || time == 1 || time == 2 || time == 3 || time == 4 || time == 5 || time == 6 ||
                        time == 18 || time == 19 || time == 20 || time == 21 || time == 22 || time == 23) {
                    wb.changeWeather("sunnyNight");
                } else {
                    wb.changeWeather("sunny");
                }
            } else if ("多云".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                int time = Integer.parseInt(TimeUtils.dateToString(TimeUtils.getTimeStame(), "HH"));
                if (time == 0 || time == 1 || time == 2 || time == 3 || time == 4 || time == 5 || time == 6 ||
                        time == 18 || time == 19 || time == 20 || time == 21 || time == 22 || time == 23) {
                    wb.changeWeather("cloudyNight");
                } else {
                    wb.changeWeather("cloudy");
                }
            } else if ("阴".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("overcast");
            } else if ("雾".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("foggy");
            } else if ("大雾".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("hazy");
            } else if ("雾霾".equals(data.getForecast_24h().get_$1().getDay_weather()) || "霾".equals(data.getForecast_24h().get_$1().getDay_weather())) {
                wb.changeWeather("dusty");
            }

            tvDayWeather1.setText(data.getForecast_24h().get_$1().getDay_weather());
            tvDayWeather2.setText(data.getForecast_24h().get_$2().getDay_weather());
            tvDayWeather3.setText(data.getForecast_24h().get_$3().getDay_weather());
            tvDayWeather4.setText(data.getForecast_24h().get_$4().getDay_weather());
            tvDayWeather5.setText(data.getForecast_24h().get_$5().getDay_weather());
            tvDayWeather6.setText(data.getForecast_24h().get_$6().getDay_weather());
            tvDayWeather7.setText(data.getForecast_24h().get_$7().getDay_weather());

            tvDegree1.setText(data.getForecast_24h().get_$1().getMax_degree() + "/" + data.getForecast_24h().get_$1().getMin_degree() + "℃");
            tvDegree2.setText(data.getForecast_24h().get_$2().getMax_degree() + "/" + data.getForecast_24h().get_$2().getMin_degree() + "℃");
            tvDegree3.setText(data.getForecast_24h().get_$3().getMax_degree() + "/" + data.getForecast_24h().get_$3().getMin_degree() + "℃");
            tvDegree4.setText(data.getForecast_24h().get_$4().getMax_degree() + "/" + data.getForecast_24h().get_$4().getMin_degree() + "℃");
            tvDegree5.setText(data.getForecast_24h().get_$5().getMax_degree() + "/" + data.getForecast_24h().get_$5().getMin_degree() + "℃");
            tvDegree6.setText(data.getForecast_24h().get_$6().getMax_degree() + "/" + data.getForecast_24h().get_$6().getMin_degree() + "℃");
            tvDegree7.setText(data.getForecast_24h().get_$7().getMax_degree() + "/" + data.getForecast_24h().get_$7().getMin_degree() + "℃");

            tvDayWindDirection1.setText(data.getForecast_24h().get_$1().getDay_wind_direction());
            tvDayWindDirection2.setText(data.getForecast_24h().get_$2().getDay_wind_direction());
            tvDayWindDirection3.setText(data.getForecast_24h().get_$3().getDay_wind_direction());
            tvDayWindDirection4.setText(data.getForecast_24h().get_$4().getDay_wind_direction());
            tvDayWindDirection5.setText(data.getForecast_24h().get_$5().getDay_wind_direction());
            tvDayWindDirection6.setText(data.getForecast_24h().get_$6().getDay_wind_direction());
            tvDayWindDirection7.setText(data.getForecast_24h().get_$7().getDay_wind_direction());

            tvTime1.setText(data.getForecast_24h().get_$1().getTime());
            tvTime2.setText(data.getForecast_24h().get_$2().getTime());
            tvTime3.setText(data.getForecast_24h().get_$3().getTime());
            tvTime4.setText(data.getForecast_24h().get_$4().getTime());
            tvTime5.setText(data.getForecast_24h().get_$5().getTime());
            tvTime6.setText(data.getForecast_24h().get_$6().getTime());
            tvTime7.setText(data.getForecast_24h().get_$7().getTime());

            tvTip.setText(data.getTips().getObserve().get_$0() + "\n" + data.getTips().getObserve().get_$1());
        } else {
            nsv.setVisibility(View.GONE);
            wb.changeWeather("");
        }
    }

    @Override
    public void weatherDataFail(String message) {
        ToastUtil.showTextToast(this, message);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_CHOOSE && resultCode == Activity.RESULT_OK) {
            province = data.getStringExtra(GlobalConstant.province);
            city = data.getStringExtra(GlobalConstant.city);
            county = data.getStringExtra(GlobalConstant.county);
            tvAddress.setText(county);
            presenter.weather(province, city, county);//腾讯天气
        }
    }

    @OnClick({R.id.ll_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address://地址选择
                intent = new Intent(this, AddressChooseActivity.class);
                startActivityForResult(intent, ADDRESS_CHOOSE);
                break;
            default:
                break;
        }
    }
}