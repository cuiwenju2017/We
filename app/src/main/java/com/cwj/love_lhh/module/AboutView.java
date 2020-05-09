package com.cwj.love_lhh.module;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseView;
import com.cwj.love_lhh.bean.LatestBean;

public interface AboutView extends BaseView {

    void getLatest(LatestBean bean);

    void getLatestError(String msg);
}
