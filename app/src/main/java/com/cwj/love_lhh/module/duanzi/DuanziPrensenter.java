package com.cwj.love_lhh.module.duanzi;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseObserver;
import com.cwj.love_lhh.base.BasePresenter;
import com.cwj.love_lhh.bean.JokesBean;

public class DuanziPrensenter extends BasePresenter<DuanziView> {
    public DuanziPrensenter(DuanziView baseView) {
        super(baseView);
    }

    public void jokes(Integer page, final Integer type) {
        addDisposable(apiServer.jokes(page), new BaseObserver<BaseBean<JokesBean>>(baseView, true) {
            @Override
            public void onSuccess(BaseBean<JokesBean> bean) {
                baseView.jokesData(bean.data, type);
            }

            @Override
            public void onError(String msg) {
                baseView.jokesError(msg, type);
            }
        });
    }
}
