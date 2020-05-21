package com.cwj.love_lhh.module.ljfl;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseObserver;
import com.cwj.love_lhh.base.BasePresenter;
import com.cwj.love_lhh.bean.RubbishBean;

public class LJFLPrensenter extends BasePresenter<LJFLView> {
    public LJFLPrensenter(LJFLView baseView) {
        super(baseView);
    }

    public void rubbish(String name) {
        addDisposable(apiServer.rubbish(name), new BaseObserver<BaseBean<RubbishBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseBean<RubbishBean> bean) {
                baseView.rubbishSuccess(bean.data);
            }

            @Override
            public void onError(String msg) {
                baseView.rubbishFail(msg);
            }
        });
    }
}
