package com.cwj.love_lhh.module.news;

import android.os.Bundle;
import android.util.Log;

import androidx.viewpager.widget.ViewPager;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.base.BaseActivity;
import com.cwj.love_lhh.bean.TypesBean;
import com.cwj.love_lhh.module.adapter.MsgContentFragmentAdapter;
import com.cwj.love_lhh.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 每日新闻
 */
public class NewsActivity extends BaseActivity<NewsPrensenter> implements NewsView {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private MsgContentFragmentAdapter adapter;

    @Override
    protected NewsPrensenter createPresenter() {
        return new NewsPrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
        presenter.types();
    }

    @Override
    protected void initData() {

    }

    private List<TypesBean.DataBean> datas;

    @Override
    public void typesData(List<TypesBean.DataBean> data) {
        datas = data;
        adapter = new MsgContentFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        // 更新适配器数据
        adapter.setList(datas);
    }

    @Override
    public void typesError(String msg) {
        ToastUtil.showTextToast(this, msg);
    }
}
