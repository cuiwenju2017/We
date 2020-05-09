package com.cwj.love_lhh.module;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseObserver;
import com.cwj.love_lhh.base.BasePresenter;
import com.cwj.love_lhh.bean.LatestBean;

public class AboutPrensenter extends BasePresenter<AboutView> {
    public AboutPrensenter(AboutView baseView){
        super(baseView);
    }

    /**
     * 广场列表数据
     */
    public void getLatest(String id,String api_token) {
        addDisposable(apiServer.getLatest(id,api_token), new BaseObserver<LatestBean>(baseView,true) {
            @Override
            public void onSuccess(LatestBean bean) {
                baseView.getLatest(bean);
            }

            @Override
            public void onError(String msg) {
                baseView.getLatestError(msg);
            }
        });
    }

}
