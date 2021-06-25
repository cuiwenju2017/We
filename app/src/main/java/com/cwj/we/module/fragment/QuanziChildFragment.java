package com.cwj.we.module.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.base.BaseRVAdapter;
import com.cwj.we.base.BaseRVHolder;
import com.cwj.we.bean.EventBG;
import com.cwj.we.bean.Post;
import com.cwj.we.bean.User;
import com.cwj.we.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import per.goweii.reveallayout.RevealLayout;

/**
 * 圈子子项
 */
public class QuanziChildFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private int type;
    private BaseRVAdapter<Post> adapter;
    private int skip = 0;
    private int limit = 15;
    private int srlType = 0;

    public QuanziChildFragment(Integer type) {
        Bundle b = new Bundle();
        b.putInt("type", type);
        setArguments(b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanzi_child, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        type = getArguments().getInt("type");
        initDate(type);
        return view;
    }

    private void initDate(int type) {
        equal(type);
        adapter = new BaseRVAdapter<Post>(R.layout.item_post) {
            @Override
            public void onBindVH(BaseRVHolder holder, Post data, int position) {
                RevealLayout revealLayout = holder.getView(R.id.revealLayout);
                holder.setText(R.id.tv_title, data.getTitle());
                holder.setText(R.id.tv_content, data.getContent());
                holder.setText(R.id.tv_author, "" + data.getUsername());
                holder.setText(R.id.tv_create_date, data.getCreatedAt());

                holder.getView(R.id.iv_share).setOnClickListener(v -> {//分享
                    showLocationShare(data.getContent());
                });

                revealLayout.setOnCheckedChangeListener((revealLayout1, isChecked) -> {//点赞
                    if (isChecked) {
                        User user = BmobUser.getCurrentUser(User.class);
                        Post post = new Post();
                        post.setObjectId(data.getObjectId());
                        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                        BmobRelation relation = new BmobRelation();
                        //将当前用户添加到多对多关联中
                        relation.add(user);
                        //多对多关联指向`post`的`likes`字段
                        post.setLikes(relation);
                        post.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("bmob", "多对多关联添加成功");
                                } else {
                                    ToastUtil.showTextToast(getActivity(), e.getMessage());
                                }
                            }

                        });
                    } else {
                        Post post = new Post();
                        post.setObjectId(data.getObjectId());
                        User user = BmobUser.getCurrentUser(User.class);
                        BmobRelation relation = new BmobRelation();
                        relation.remove(user);
                        post.setLikes(relation);
                        post.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("bmob", "关联关系删除成功");
                                } else {
                                    ToastUtil.showTextToast(getActivity(), e.getMessage());
                                }
                            }
                        });
                    }
                });
            }
        };

        View empty = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty_view, null, false);
        adapter.setEmptyView(empty);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));//列数设置

        srl.setOnRefreshListener(refreshLayout -> {//刷新
            srlType = 0;
            skip = 0;
            equal(type);
        });

        srl.setOnLoadMoreListener(refreshLayout -> {//加载更多
            srlType = 1;
            skip = ++skip;
            equal(type);
        });
    }

    /**
     * 调用本地分享文本
     */
    private void showLocationShare(String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);//注意：这里只是分享文本内容
        sendIntent.setType("text/*");
        startActivity(Intent.createChooser(sendIntent, "分享到"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBG eventBG) {
        switch (eventBG.getType()) {
            case "ADD_SUCCESS":
                equal(type);
                break;
        }
    }

    private void equal(int type) {
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("type", type);
        query.setLimit(limit).setSkip(skip).order("-createdAt")
                .findObjects(new FindListener<Post>() {
                    @Override
                    public void done(List<Post> object, BmobException e) {
                        if (e == null) {
                            if (srlType == 0) {
                                srl.finishRefresh();
                                srl.setNoMoreData(false);
                                adapter.setNewData(object);
                            } else if (srlType == 1) {
                                adapter.addData(object);
                                if (object.size() < limit) {
                                    srl.finishLoadMoreWithNoMoreData();
                                } else {
                                    srl.finishLoadMore();
                                }
                            }
                        } else {
                            srl.finishLoadMoreWithNoMoreData();
                            Log.e("BMOB", e.toString());
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}