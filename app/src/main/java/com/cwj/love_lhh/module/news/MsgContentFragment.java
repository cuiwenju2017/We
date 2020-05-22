package com.cwj.love_lhh.module.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cwj.love_lhh.R;
import com.cwj.love_lhh.base.BaseFragment;
import com.cwj.love_lhh.base.BaseRVAdapter;
import com.cwj.love_lhh.base.BaseRVHolder;
import com.cwj.love_lhh.bean.GetDataType;
import com.cwj.love_lhh.bean.JokesBean;
import com.cwj.love_lhh.bean.NewsListBean;
import com.cwj.love_lhh.module.activity.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.GSYADVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.yechaoa.yutils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class MsgContentFragment extends BaseFragment<MsgContentPrensenter> implements MsgContentView {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private Integer typeId;
    private int page = 1;
    BaseRVAdapter<NewsListBean.DataBean> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        typeId = bundle.getInt("typeId");
    }

    @Override
    protected MsgContentPrensenter createPresenter() {
        return new MsgContentPrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg_content;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        presenter.newsList(typeId, page, GetDataType.GETDATA);

        if (typeId == 522) {
            adapter = new BaseRVAdapter<NewsListBean.DataBean>(R.layout.item_news_video) {
                @Override
                public void onBindVH(BaseRVHolder holder, NewsListBean.DataBean data, int position) {
                    holder.setText(R.id.tv_title, data.getTitle());
                    ImageView iv_bg = holder.getView(R.id.iv_bg);
                    if (data.getImgList() != null) {
                        Glide.with(getActivity()).load(data.getImgList().get(0)).into(iv_bg);
                    } else {
                        Glide.with(getActivity()).load(R.drawable.icon_day_news).into(iv_bg);
                    }

                    holder.getView(R.id.iv_start).setOnClickListener(v -> {
                        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                        intent.putExtra("title", data.getTitle());
                        intent.putExtra("url", data.getVideoList().get(0));
                        startActivity(intent);
                    });
                }
            };
        } else {
            adapter = new BaseRVAdapter<NewsListBean.DataBean>(R.layout.item_news) {
                @Override
                public void onBindVH(BaseRVHolder holder, NewsListBean.DataBean data, int position) {
                    ImageView iv_imgList = holder.getView(R.id.iv_imgList);
                    if (data.getImgList() != null) {
                        Glide.with(getActivity()).load(data.getImgList().get(0)).into(iv_imgList);
                    } else {
                        Glide.with(getActivity()).load(R.drawable.icon_day_news).into(iv_imgList);
                    }

                    holder.setText(R.id.tv_title, data.getTitle());
                    holder.setText(R.id.tv_source, data.getSource());
                    holder.setText(R.id.tv_postTime, data.getPostTime());
                }
            };
        }

        //无数据视图显示
        View empty = LayoutInflater.from(getActivity()).inflate(R.layout.layout_no_data, null, false);
        adapter.setEmptyView(empty);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {//下拉刷新
            page = 1;
            presenter.newsList(typeId, page, GetDataType.REFRESH);
            hideLoading();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {//上拉加载更多
            page++;
            presenter.newsList(typeId, page, GetDataType.LOADMORE);
            hideLoading();
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));//列数设置
    }

    @Override
    public void newsListData(List<NewsListBean.DataBean> data, Integer type) {
        List<NewsListBean.DataBean> newsList = data;
        switch (type) {
            case GetDataType.GETDATA://获取数据成功
                adapter.setNewData(newsList);
                break;
            case GetDataType.REFRESH://刷新成功
                adapter.setNewData(newsList);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.setNoMoreData(false);
                break;
            case GetDataType.LOADMORE://加载成功
                if (newsList != null && !newsList.isEmpty()) {
                    adapter.addData(newsList);
                    if (newsList.size() < page) {
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

    @Override
    public void jnewsListError(String msg, Integer type) {
        ToastUtil.showToast(msg);
    }
}
