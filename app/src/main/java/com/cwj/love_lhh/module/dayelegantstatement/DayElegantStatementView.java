package com.cwj.love_lhh.module.dayelegantstatement;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseView;
import com.cwj.love_lhh.bean.RecommendBean;

import java.util.List;

public interface DayElegantStatementView extends BaseView {
    
    void recommendError(String msg);

    void recommendData(List<RecommendBean.DataBean> data);
}
