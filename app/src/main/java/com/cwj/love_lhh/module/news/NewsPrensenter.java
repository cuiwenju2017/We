package com.cwj.love_lhh.module.news;

import com.cwj.love_lhh.base.BaseObserver;
import com.cwj.love_lhh.base.BasePresenter;
import com.cwj.love_lhh.bean.RecommendBean;
import com.cwj.love_lhh.bean.TypesBean;

public class NewsPrensenter extends BasePresenter<NewsView> {
    public NewsPrensenter(NewsView baseView) {
        super(baseView);
    }

    /**
     * 获取所有新闻类型列表
     */
    public void types() {
        addDisposable(apiServer.types(), new BaseObserver<TypesBean>(baseView, true) {
            @Override
            public void onSuccess(TypesBean bean) {
                baseView.typesData(bean.getData());
            }

            @Override
            public void onError(String msg) {
                baseView.typesError(msg);
            }
        });
    }
}
