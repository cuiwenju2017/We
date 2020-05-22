package com.cwj.love_lhh.module.news;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseFragment;
import com.cwj.love_lhh.base.BaseObserver;
import com.cwj.love_lhh.base.BasePresenter;
import com.cwj.love_lhh.bean.JokesBean;
import com.cwj.love_lhh.bean.NewsListBean;

public class MsgContentPrensenter extends BasePresenter<MsgContentView> {
    public MsgContentPrensenter(MsgContentView baseView) {
        super(baseView);
    }

    /**
     * 根据新闻类型获取新闻列表
     *
     * @param typeId
     * @param page
     * @param type
     */
    public void newsList(Integer typeId, Integer page, final Integer type) {
        addDisposable(apiServer.newsList(typeId, page), new BaseObserver<NewsListBean>(baseView, true) {
            @Override
            public void onSuccess(NewsListBean bean) {
                baseView.newsListData(bean.getData(), type);
            }

            @Override
            public void onError(String msg) {
                baseView.jnewsListError(msg, type);
            }
        });
    }
}
