package com.cwj.we.module.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.base.BaseRVAdapter;
import com.cwj.we.base.BaseRVHolder;
import com.cwj.we.bean.Comment;
import com.cwj.we.bean.EventBG;
import com.cwj.we.bean.Post;
import com.cwj.we.bean.User;
import com.cwj.we.utils.LoadingDialog;
import com.cwj.we.utils.ToastUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import per.goweii.reveallayout.RevealLayout;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    private int skip = 1;
    private int limit = 15;
    private int srlType = 0;
    private Post post;
    SharedPreferences sprfMain;
    private LoadingDialog loadingDialog;
    private int pos;
    private String username;

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
        loadingDialog = new LoadingDialog(getActivity(), "");
        sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
        username = sprfMain.getString("username", "");
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
                TextView tv_like_num = holder.getView(R.id.tv_like_num);
                TextView tv_pinglun_num = holder.getView(R.id.tv_pinglun_num);
                TextView tv_delect_tiezi = holder.getView(R.id.tv_delect_tiezi);

                holder.setText(R.id.tv_title, data.getTitle());
                holder.setText(R.id.tv_content, data.getContent());
                if (data.getType() == 1) {
                    holder.setText(R.id.tv_author, "树洞");
                } else {
                    holder.setText(R.id.tv_author, "" + data.getUsername());
                }
                holder.setText(R.id.tv_create_date, data.getCreatedAt());

                if (username.equals(data.getUsername())) {
                    tv_delect_tiezi.setVisibility(VISIBLE);
                } else {
                    tv_delect_tiezi.setVisibility(GONE);
                }

                tv_delect_tiezi.setOnClickListener(v -> {//删除帖子
                    new XPopup.Builder(getContext()).asConfirm("提示", "确定删除该帖子吗？",
                            () -> {
                                loadingDialog.show();
                                pos = position;
                                removePostAuthor(data.getObjectId());
                            })
                            .show();
                });

                //查询出某个帖子的所有评论,同时将该评论的作者的信息也查询出来
                BmobQuery<Comment> queryPinglun = new BmobQuery<Comment>();
                //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
                post = new Post();
                post.setObjectId(data.getObjectId());
                queryPinglun.addWhereEqualTo("post", new BmobPointer(post));
                //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
                queryPinglun.include("user,post.author");
                queryPinglun.findObjects(new FindListener<Comment>() {
                    @Override
                    public void done(List<Comment> objects, BmobException e) {
                        if (objects.size() > 0) {
                            tv_pinglun_num.setVisibility(VISIBLE);
                            tv_pinglun_num.setText("" + objects.size());
                        } else {
                            tv_pinglun_num.setVisibility(GONE);
                        }
                    }
                });

                // 查询喜欢这个帖子的所有用户，因此查询的是用户表
                BmobQuery<User> query = new BmobQuery<User>();
                post = new Post();
                post.setObjectId(data.getObjectId());
                //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
                query.addWhereRelatedTo("likes", new BmobPointer(post));
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> object, BmobException e) {
                        if (e == null) {
                            if (object.size() > 0) {
                                tv_like_num.setVisibility(VISIBLE);
                                tv_like_num.setText("" + object.size());
                                sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
                                String username = sprfMain.getString("username", "");
                                for (int i = 0; i < object.size(); i++) {
                                    if (username.equals(object.get(i).getUsername())) {
                                        revealLayout.setChecked(true);
                                    }
                                }
                            } else {
                                revealLayout.setChecked(false);
                                tv_like_num.setVisibility(GONE);
                            }
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage());
                        }
                    }
                });

                holder.getView(R.id.iv_share).setOnClickListener(v -> {//分享
                    showLocationShare(data.getContent());
                });

                holder.getView(R.id.iv_pinglun).setOnClickListener(v -> {//评论
                    pos = position;
                    new XPopup.Builder(getContext())
                            .hasShadowBg(false) // 是否有半透明的背景，默认为true
                            .autoOpenSoftInput(false) //是否弹窗显示的同时打开输入法，只在包含输入框的弹窗内才有效，默认为false
                            .asCustom(new ZhihuCommentPopup(getContext(), data))
                            .show();
                });

                revealLayout.setOnCheckedChangeListener((revealLayout1, isChecked) -> {//点赞
                    if (isChecked) {
                        User user = BmobUser.getCurrentUser(User.class);
                        post = new Post();
                        post.setObjectId(data.getObjectId());
                        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                        BmobRelation relation = new BmobRelation();
                        //将当前用户添加到多对多关联中
                        relation.add(user);
                        //多对多关联指向`post`的`likes`字段
                        post.setLikes(relation);
                        post.update(new UpdateListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("bmob", "多对多关联添加成功");
                                    // 查询喜欢这个帖子的所有用户，因此查询的是用户表
                                    BmobQuery<User> query = new BmobQuery<User>();
                                    post = new Post();
                                    post.setObjectId(data.getObjectId());
                                    //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
                                    query.addWhereRelatedTo("likes", new BmobPointer(post));
                                    query.findObjects(new FindListener<User>() {
                                        @Override
                                        public void done(List<User> object, BmobException e) {
                                            if (e == null) {
                                                if (object.size() > 0) {
                                                    tv_like_num.setVisibility(VISIBLE);
                                                    tv_like_num.setText("" + object.size());
                                                    sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
                                                    String username = sprfMain.getString("username", "");
                                                    for (int i = 0; i < object.size(); i++) {
                                                        if (username.equals(object.get(i).getUsername())) {
                                                            revealLayout.setChecked(true);
                                                        }
                                                    }
                                                } else {
                                                    revealLayout.setChecked(false);
                                                    tv_like_num.setVisibility(GONE);
                                                }
                                            } else {
                                                Log.i("bmob", "失败：" + e.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    ToastUtil.showTextToast(getActivity(), e.getMessage());
                                }
                            }
                        });
                    } else {
                        post = new Post();
                        post.setObjectId(data.getObjectId());
                        User user = BmobUser.getCurrentUser(User.class);
                        BmobRelation relation = new BmobRelation();
                        relation.remove(user);
                        post.setLikes(relation);
                        post.update(new UpdateListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("bmob", "关联关系删除成功");
                                    // 查询喜欢这个帖子的所有用户，因此查询的是用户表
                                    BmobQuery<User> query = new BmobQuery<User>();
                                    post = new Post();
                                    post.setObjectId(data.getObjectId());
                                    //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
                                    query.addWhereRelatedTo("likes", new BmobPointer(post));
                                    query.findObjects(new FindListener<User>() {
                                        @Override
                                        public void done(List<User> object, BmobException e) {
                                            if (e == null) {
                                                if (object.size() > 0) {
                                                    tv_like_num.setVisibility(VISIBLE);
                                                    tv_like_num.setText("" + object.size());
                                                    sprfMain = getActivity().getSharedPreferences("counter", Context.MODE_PRIVATE);
                                                    String username = sprfMain.getString("username", "");
                                                    for (int i = 0; i < object.size(); i++) {
                                                        if (username.equals(object.get(i).getUsername())) {
                                                            revealLayout.setChecked(true);
                                                        }
                                                    }
                                                } else {
                                                    revealLayout.setChecked(false);
                                                    tv_like_num.setVisibility(GONE);
                                                }
                                            } else {
                                                Log.i("bmob", "失败：" + e.getMessage());
                                            }
                                        }
                                    });
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
            skip = 1;
            equal(type);
        });

        srl.setOnLoadMoreListener(refreshLayout -> {//加载更多
            srlType = 1;
            skip = ++skip;
            equal(type);
        });
    }

    /**
     * 删除一行数据
     */
    private void removePostAuthor(String objectId) {
        Post post = new Post();
        post.setObjectId(objectId);
        post.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    loadingDialog.dismiss();
                    adapter.remove(pos);
                    ToastUtil.showTextToast(getActivity(), "删除成功");
                } else {
                    loadingDialog.dismiss();
                    ToastUtil.showTextToast(getActivity(), "" + e.getMessage());
                }
            }
        });
    }

    public class ZhihuCommentPopup extends BottomPopupView {

        @BindView(R.id.enter)
        EditText enter;
        @BindView(R.id.send)
        Button send;
        @BindView(R.id.rv_pinglun)
        RecyclerView rvPinglun;
        @BindView(R.id.srl_pinglun)
        SmartRefreshLayout srlPinglun;
        private Post data;
        private BaseRVAdapter<Comment> commentBaseRVAdapter;
        private int currentPage = 1;
        private int connt = 15;
        private int srlTypePinglun = 0;
        private Context context;

        public ZhihuCommentPopup(@NonNull Context context, Post data) {
            super(context);
            this.data = data;
            this.context = context;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_bottom_popup;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            unbinder = ButterKnife.bind(this);
            queryPinglun();
            commentBaseRVAdapter = new BaseRVAdapter<Comment>(R.layout.item_pinglun) {
                @Override
                public void onBindVH(BaseRVHolder holder, Comment data, int position) {
                    TextView tv_delect = holder.getView(R.id.tv_delect);

                    holder.setText(R.id.tv_pinglun_username, data.getUser().getUsername());
                    holder.setText(R.id.tv_pinglun_content, data.getContent());
                    holder.setText(R.id.tv_pinglun_date, data.getCreatedAt());

                    if (username.equals(data.getUser().getUsername())) {
                        tv_delect.setVisibility(VISIBLE);
                    } else {
                        tv_delect.setVisibility(GONE);
                    }

                    tv_delect.setOnClickListener(v -> {//删除评论
                        new XPopup.Builder(getContext()).asConfirm("提示", "确定删除该条评论吗？",
                                () -> {
                                    loadingDialog.show();
                                    Comment comment = new Comment();
                                    comment.setObjectId(data.getObjectId());
                                    comment.delete(new UpdateListener() {

                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                loadingDialog.dismiss();
                                                ToastUtil.showTextToast(context, "删除成功");
                                                commentBaseRVAdapter.remove(position);
                                                adapter.notifyItemChanged(pos);
                                            } else {
                                                loadingDialog.dismiss();
                                                ToastUtil.showTextToast(context, "删除失败:" + e.getMessage());
                                            }
                                        }

                                    });
                                })
                                .show();
                    });
                }
            };

            View empty = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty_view, null, false);
            commentBaseRVAdapter.setEmptyView(empty);

            rvPinglun.setAdapter(commentBaseRVAdapter);
            rvPinglun.setLayoutManager(new GridLayoutManager(getActivity(), 1));//列数设置

            srlPinglun.setOnLoadMoreListener(refreshLayout -> {//加载更多
                srlTypePinglun = 1;
                currentPage = ++currentPage;
                queryPinglun();
            });
        }

        private void queryPinglun() {
            //查询出某个帖子的所有评论,同时将该评论的作者的信息也查询出来
            BmobQuery<Comment> queryPinglun = new BmobQuery<Comment>();
            //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
            post = new Post();
            post.setObjectId(data.getObjectId());
            queryPinglun.addWhereEqualTo("post", new BmobPointer(post));
            //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
            queryPinglun.include("user,post.author");
            queryPinglun.setPage(currentPage, connt).order("-createdAt").findObjects(new FindListener<Comment>() {
                @Override
                public void done(List<Comment> objects, BmobException e) {
                    if (e == null) {
                        if (srlTypePinglun == 0) {
                            srlPinglun.finishRefresh();
                            srlPinglun.setNoMoreData(false);
                            commentBaseRVAdapter.setNewData(objects);
                        } else if (srlTypePinglun == 1) {
                            commentBaseRVAdapter.addData(objects);
                            if (objects.size() < connt) {
                                srlPinglun.finishLoadMoreWithNoMoreData();
                            } else {
                                srlPinglun.finishLoadMore();
                            }
                        }
                    } else {
                        srlPinglun.finishLoadMoreWithNoMoreData();
                        Log.e("BMOB", e.toString());
                    }
                }
            });
        }

        // 最大高度为Window的0.85
        @Override
        protected int getMaxHeight() {
            return (int) (XPopupUtils.getWindowHeight(getContext()) * .60f);
        }

        @OnClick({R.id.send})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.send://评论
                    if (TextUtils.isEmpty(enter.getText().toString())) {
                        ToastUtil.showTextToast(getActivity(), "评论内容不能为空");
                    } else {
                        loadingDialog.show();
                        User user = BmobUser.getCurrentUser(User.class);
                        post = new Post();
                        post.setObjectId(data.getObjectId());
                        final Comment comment = new Comment();
                        comment.setContent(enter.getText().toString());
                        comment.setPost(post);
                        comment.setUser(user);
                        comment.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    loadingDialog.dismiss();
                                    enter.setText("");
                                    adapter.notifyItemChanged(pos);
                                    queryPinglun();
                                    commentBaseRVAdapter.notifyDataSetChanged();
                                } else {
                                    loadingDialog.dismiss();
                                    ToastUtil.showTextToast(getActivity(), e.getMessage());
                                }
                            }
                        });
                    }
                    break;
            }
        }
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
        query.setPage(skip, limit).order("-createdAt")
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