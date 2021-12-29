package com.cwj.we.module.tianqi;

import com.cwj.we.base.BaseObserver;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.WeatherBean;

public class TianqiPrensenter extends BasePresenter<TianqiView> {
    public TianqiPrensenter(TianqiView baseView) {
        super(baseView);
    }

    /**
     * 腾讯天气
     *
     * @param province
     * @param city
     * @param county
     */
    public void weather(String province, String city, String county) {
        addDisposable(apiServer.weather(province, city, county), new BaseObserver<WeatherBean>() {
            @Override
            public void onSuccess(WeatherBean bean) {
                if (bean.getStatus() == 200) {
                    baseView.weatherData(bean.getData());
                } else {
                    baseView.weatherDataFail(bean.getMessage());
                }
            }

            @Override
            public void onError(String msg) {
                baseView.onError(msg);
            }
        });
    }
}
