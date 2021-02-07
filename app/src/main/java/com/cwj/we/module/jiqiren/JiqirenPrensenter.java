package com.cwj.we.module.jiqiren;

import com.cwj.we.base.BaseObserver;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.MsgBean;

public class JiqirenPrensenter extends BasePresenter<JiqirenView> {
    public JiqirenPrensenter(JiqirenView baseView) {
        super(baseView);
    }

    /**
     * 智能机器人
     *
     * @param msg
     */
    public void msg(String msg) {
        addDisposable(qingyunkeApi.msg(msg), new BaseObserver<MsgBean>() {
            @Override
            public void onSuccess(MsgBean bean) {
                baseView.msgData(bean);
            }

            @Override
            public void onError(String msg) {
                baseView.onError(msg);
            }
        });
    }
}
