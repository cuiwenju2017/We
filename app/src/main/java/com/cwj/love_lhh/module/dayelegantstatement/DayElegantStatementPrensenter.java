package com.cwj.love_lhh.module.dayelegantstatement;

import com.cwj.love_lhh.base.BaseObserver;
import com.cwj.love_lhh.base.BasePresenter;
import com.cwj.love_lhh.bean.RecommendBean;

public class DayElegantStatementPrensenter extends BasePresenter<DayElegantStatementView> {
    public DayElegantStatementPrensenter(DayElegantStatementView baseView){
        super(baseView);
    }

    public void recommend(String count) {
        addDisposable(apiServer.recommend(count), new BaseObserver<RecommendBean>(baseView, true) {
            @Override
            public void onSuccess(RecommendBean bean) {
                baseView.recommendData(bean.getData());
            }

            @Override
            public void onError(String msg) {
                baseView.recommendError(msg);
            }
        });
    }

}
