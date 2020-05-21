package com.cwj.love_lhh.module.ljfl;

import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseView;
import com.cwj.love_lhh.bean.RubbishBean;

public interface LJFLView extends BaseView {

    void rubbishFail(String msg);

    void rubbishSuccess(RubbishBean data);
}
