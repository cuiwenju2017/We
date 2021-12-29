package com.cwj.we.module.tianqi;

import com.cwj.we.base.BaseView;
import com.cwj.we.bean.WeatherBean;

public interface TianqiView extends BaseView {
    void weatherData(WeatherBean.DataBean data);

    void weatherDataFail(String message);

    void onError(String msg);
}
