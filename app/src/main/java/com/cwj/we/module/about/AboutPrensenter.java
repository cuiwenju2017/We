package com.cwj.we.module.about;

import com.cwj.we.base.BaseObserver;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.LatestBean;

public class AboutPrensenter extends BasePresenter<AboutView> {
    public AboutPrensenter(AboutView baseView) {
        super(baseView);
    }

    /**
     * 检测版本更新
     *
     * @param id
     * @param api_token
     */
    public void latest(String id, String api_token) {
        addDisposable(apiServer.latest(id, api_token), new BaseObserver<LatestBean>() {
            @Override
            public void onSuccess(LatestBean bean) {
                baseView.latestData(bean);
            }

            @Override
            public void onError(String msg) {
                baseView.onError(msg);
            }
        });
    }
}
