package com.cwj.love_lhh.module.duanzi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.base.BaseActivity;
import com.cwj.love_lhh.base.BaseRVAdapter;
import com.cwj.love_lhh.base.BaseRVHolder;
import com.cwj.love_lhh.bean.GetDataType;
import com.cwj.love_lhh.bean.JokesBean;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yechaoa.yutils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DuanziActivity extends BaseActivity<DuanziPrensenter> implements DuanziView {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.cl_view)
    CoordinatorLayout clView;

    private int page = 0;
    BaseRVAdapter<JokesBean.ListBean> adapter;
    private TextView tv_random;

    @Override
    protected DuanziPrensenter createPresenter() {
        return new DuanziPrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_duanzi;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, clView);//沉浸状态栏
    }

    @Override
    protected void initData() {
        presenter.jokes(page, GetDataType.GETDATA);

        adapter = new BaseRVAdapter<JokesBean.ListBean>(R.layout.item_jokes) {
            @Override
            public void onBindVH(BaseRVHolder holder, JokesBean.ListBean data, int position) {
                holder.setText(R.id.tv_content, data.getContent());
                holder.setText(R.id.tv_updateTime, "更新时间：" + data.getUpdateTime());
            }
        };

        //无数据视图显示
        View empty = LayoutInflater.from(this).inflate(R.layout.layout_no_data, null, false);
        adapter.setEmptyView(empty);

        //添加头布局
        View view = LayoutInflater.from(this).inflate(R.layout.view_header, null, false);
        adapter.setHeaderView(view);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {//下拉刷新
            page = 0;
            presenter.jokes(page, GetDataType.REFRESH);
            hideLoading();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {//上拉加载更多
            page++;
            presenter.jokes(page, GetDataType.LOADMORE);
            hideLoading();
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));//列数设置
    }

    @Override
    public void jokesError(String msg, Integer type) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void jokesData(JokesBean data, Integer type) {
        List<JokesBean.ListBean> jokes = data.getList();
        switch (type) {
            case GetDataType.GETDATA://获取数据成功
                adapter.setNewData(jokes);
                break;
            case GetDataType.REFRESH://刷新成功
                adapter.setNewData(jokes);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.setNoMoreData(false);
                break;
            case GetDataType.LOADMORE://加载成功
                if (jokes != null && !jokes.isEmpty()) {
                    adapter.addData(jokes);
                    if (jokes.size() < page) {
                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        mRefreshLayout.finishLoadMore();
                    }
                } else {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                break;
        }
    }
}
