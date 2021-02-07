package com.cwj.we.module.jiqiren;

import com.cwj.we.base.BaseView;
import com.cwj.we.bean.MsgBean;

public interface JiqirenView extends BaseView {
    void msgData(MsgBean bean);

    void onError(String msg);
}
