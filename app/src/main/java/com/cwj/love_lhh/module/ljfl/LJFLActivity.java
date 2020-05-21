package com.cwj.love_lhh.module.ljfl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.base.BaseActivity;
import com.cwj.love_lhh.base.BaseRVAdapter;
import com.cwj.love_lhh.base.BaseRVHolder;
import com.cwj.love_lhh.bean.RubbishBean;
import com.cwj.love_lhh.module.activity.WebViewActivity;
import com.cwj.love_lhh.utils.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 垃圾分类
 */
public class LJFLActivity extends BaseActivity<LJFLPrensenter> implements LJFLView {

    @BindView(R.id.cl_view)
    CoordinatorLayout clView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.btn_query)
    Button btnQuery;
    @BindView(R.id.tv_goodsType)
    TextView tvGoodsType;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.ll_result)
    LinearLayout llResult;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected LJFLPrensenter createPresenter() {
        return new LJFLPrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_l_j_f_l;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, clView);//沉浸状态栏
    }

    BaseRVAdapter<RubbishBean.RecommendListBean> adapter;

    @Override
    protected void initData() {
        adapter = new BaseRVAdapter<RubbishBean.RecommendListBean>(R.layout.item_recommend) {
            @Override
            public void onBindVH(BaseRVHolder holder, RubbishBean.RecommendListBean data, int position) {
                holder.setText(R.id.tv_goodsType, data.getGoodsName() + "：" + data.getGoodsType());
            }
        };

        //无数据视图显示
        View empty = LayoutInflater.from(this).inflate(R.layout.layout_no_data, null, false);
        adapter.setEmptyView(empty);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(this, 1));//列数设置
    }

    private Intent intent;

    @OnClick({R.id.fab, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab://指南
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", "https://jingyan.baidu.com/article/f54ae2fcfcbd9c5f93b8491c.html");
                startActivity(intent);
                break;
            case R.id.btn_query:
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ToastUtil.showTextToast(this, "请输入查询内容");
                } else {
                    presenter.rubbish(etName.getText().toString());
                }
                break;
        }
    }

    @Override
    public void rubbishFail(String msg) {
        ToastUtil.showTextToast(this, msg);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void rubbishSuccess(RubbishBean data) {
        llResult.setVisibility(View.VISIBLE);
        llRecommend.setVisibility(View.VISIBLE);
        if (data.getAim() == null) {
            tvGoodsType.setText("暂无该内容");
        } else {
            tvGoodsType.setText(data.getAim().getGoodsName() + "：" + data.getAim().getGoodsType());
        }
        adapter.setNewData(data.getRecommendList());
    }
}
