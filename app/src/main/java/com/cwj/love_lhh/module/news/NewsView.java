package com.cwj.love_lhh.module.news;

import com.cwj.love_lhh.base.BaseView;
import com.cwj.love_lhh.bean.TypesBean;

import java.util.List;

public interface NewsView extends BaseView {
    void typesData(List<TypesBean.DataBean> data);

    void typesError(String msg);
}
