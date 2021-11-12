package com.cwj.we.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.components.ImmersionFragment;
import com.yechaoa.yutils.ActivityUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description : BaseFragment
 *
 * @author XuCanyou666
 * @date 2020/2/7
 */
public abstract class BaseFragment<P extends BasePresenter> extends ImmersionFragment implements BaseView {

    private Unbinder unbinder;
    protected Context mContext;

    protected P presenter;

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        //得到context,在后面的子类Fragment中都可以直接调用
        mContext = ActivityUtil.getCurrentActivity();
        presenter = createPresenter();
        initView();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //do something
        unbinder.unbind();
        //销毁时，解除绑定
        if (presenter != null) {
            presenter.detachView();
        }
    }

    private void initListener() {
    }
}
