package com.cwj.love_lhh.module.duanzi;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseView;
import com.cwj.love_lhh.bean.JokesBean;

public interface DuanziView extends BaseView {
    void jokesError(String msg, Integer type);

    void jokesData(JokesBean data, Integer type);
}
