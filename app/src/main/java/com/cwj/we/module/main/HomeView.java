package com.cwj.we.module.main;

import com.cwj.we.base.BaseView;
import com.cwj.we.bean.LatestBean;

public interface HomeView extends BaseView {
    void latestData(LatestBean bean);

    void onError(String msg);
}
