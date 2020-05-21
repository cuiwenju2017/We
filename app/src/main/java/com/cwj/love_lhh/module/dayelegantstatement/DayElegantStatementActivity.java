package com.cwj.love_lhh.module.dayelegantstatement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.base.BaseActivity;
import com.cwj.love_lhh.base.BaseBean;
import com.cwj.love_lhh.base.BaseRVAdapter;
import com.cwj.love_lhh.base.BaseRVHolder;
import com.cwj.love_lhh.bean.RecommendBean;
import com.cwj.love_lhh.utils.ToastUtil;
import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 每日精美语句
 */
public class DayElegantStatementActivity extends BaseActivity<DayElegantStatementPrensenter> implements DayElegantStatementView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected DayElegantStatementPrensenter createPresenter() {
        return new DayElegantStatementPrensenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_day_elegant_statement;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
    }

    BaseRVAdapter<RecommendBean.DataBean> adapter;
    private EchelonLayoutManager mLayoutManager;
    private int[] bgs = {R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4};

    @Override
    protected void initData() {
        presenter.recommend("20");

        adapter = new BaseRVAdapter<RecommendBean.DataBean>(R.layout.item_echelon) {
            @Override
            public void onBindVH(BaseRVHolder holder, RecommendBean.DataBean data, int position) {
                holder.setText(R.id.tv_desc, data.getContent());
                ImageView bg = holder.getView(R.id.img_bg);
                bg.setImageResource(bgs[position % 4]);
            }
        };

        mLayoutManager = new EchelonLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void recommendError(String msg) {
        ToastUtil.showTextToast(this, msg);
    }

    @Override
    public void recommendData(List<RecommendBean.DataBean> data) {
        if (data != null) {
            adapter.setNewData(data);
        }
    }
}
