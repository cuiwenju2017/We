package com.cwj.love_lhh.module.news;

import com.cwj.love_lhh.base.BaseView;
import com.cwj.love_lhh.bean.NewsListBean;

import java.util.List;

public interface MsgContentView extends BaseView {
    void newsListData(List<NewsListBean.DataBean> data, Integer type);

    void jnewsListError(String msg, Integer type);
}
