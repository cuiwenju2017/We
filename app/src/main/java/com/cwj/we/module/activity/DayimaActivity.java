package com.cwj.we.module.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.base.BaseRVAdapter;
import com.cwj.we.base.BaseRVHolder;
import com.cwj.we.bean.DayimaZhouqiBean;
import com.cwj.we.http.API;
import com.cwj.we.utils.TimeUtils;
import com.cwj.we.utils.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.litepal.LitePal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 大姨妈
 */
public class DayimaActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    private BaseRVAdapter<DayimaZhouqiBean> adapter;
    private List<DayimaZhouqiBean> list = new ArrayList<>();
    private int ADD = 201;
    private int UPDATE_DAYIMA = 202;
    private int pos;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dayima;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        srl.setEnableLoadMore(false);
        adapter = new BaseRVAdapter<DayimaZhouqiBean>(R.layout.item_zhouqi) {
            @Override
            public void onBindVH(BaseRVHolder holder, DayimaZhouqiBean data, int position) {
                TextView tv_isdayima = holder.getView(R.id.tv_isdayima);
                ImageView iv = holder.getView(R.id.iv);
                LinearLayout ll_context = holder.getView(R.id.ll_context);

                holder.setText(R.id.tv_time, data.getTime());
                if (data.isDayima()) {
                    tv_isdayima.setText("大姨妈来了");
                    Glide.with(DayimaActivity.this).load(R.drawable.icon_islai).into(iv);
                } else {
                    tv_isdayima.setText("大姨妈走了");
                    Glide.with(DayimaActivity.this).load(R.drawable.icon_iszou).into(iv);
                }

                ll_context.setOnLongClickListener(v -> {//删除数据
                    new XPopup.Builder(DayimaActivity.this).asConfirm("提示",
                            "确定删除该数据吗",
                            () -> {
                                LitePal.delete(DayimaZhouqiBean.class, data.getId());
                                adapter.remove(position);
                                if (position == 0) {
                                    update();
                                }
                            })
                            .show();
                    return false;
                });

                ll_context.setOnClickListener(v -> { //只允许修改第一条数据
                    pos = position;
                    if (pos == 0) {
                        Intent intent = new Intent(DayimaActivity.this, AddDayimaActivity.class);
                        intent.putExtra("id", data.getId());
                        intent.putExtra("time", data.getTime());
                        intent.putExtra("isDayima", data.isDayima());
                        startActivityForResult(intent, UPDATE_DAYIMA);
                    } else {
                        ToastUtil.showTextToast(DayimaActivity.this, "只允许修改第一条数据");
                    }
                });
            }
        };
        //设置空视图
        View empty = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, null,
                false);
        adapter.setEmptyView(empty);

        //刷新
        srl.setOnRefreshListener(refreshLayout -> {
            list = LitePal.order("time desc").find(DayimaZhouqiBean.class, true);//倒序排序
            adapter.setNewData(list);
            srl.finishRefresh();
        });

        //设置适配器
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(this, 1));//列数设置

        list = LitePal.order("time desc").find(DayimaZhouqiBean.class, true);//倒序排序
        adapter.setNewData(list);

        update();
    }

    private long time;

    @SuppressLint("SetTextI18n")
    private void update() {
        if (list != null && list.size() > 0) {
            tvTip.setVisibility(View.VISIBLE);
            if (list.get(0).isDayima()) {
                try {
                    long time = Long.parseLong(TimeUtils.dateToStamp2(list.get(0).getTime()));
                    long tianshu = Long.parseLong(API.kv.decodeString("tianshu")) * 24 * 60 * 60 * 1000;

                    long timezou = Long.parseLong(TimeUtils.dateToStamp2(TimeUtils.dateToString((time + tianshu))));
                    long tianzou = timezou - Long.parseLong(TimeUtils.dateToStamp2(TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy-MM-dd")));

                    if (tianzou <= 0) {
                        tvTip.setText("大姨妈预计" + TimeUtils.dateToString((time + tianshu), "yyyy-MM-dd") +
                                "走");
                    } else {
                        tvTip.setText("大姨妈预计" + TimeUtils.dateToString((time + tianshu), "yyyy-MM-dd") +
                                "走，还有" + (tianzou / 1000 / 60 / 60 / 24) + "天");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (list.size() > 1) {
                        time = Long.parseLong(TimeUtils.dateToStamp2(list.get(1).getTime()));
                    } else {
                        long tianshu = Long.parseLong(API.kv.decodeString("tianshu")) * 24 * 60 * 60 * 1000;
                        time = Long.parseLong(TimeUtils.dateToStamp2(list.get(0).getTime())) - tianshu;
                    }

                    long zhoqi = Long.parseLong(API.kv.decodeString("zhouqi")) * 24 * 60 * 60 * 1000;

                    long timeLai = Long.parseLong(TimeUtils.dateToStamp2(TimeUtils.dateToString((time + zhoqi))));
                    long tianlai = timeLai - Long.parseLong(TimeUtils.dateToStamp2(TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy-MM-dd")));

                    if (tianlai <= 0) {
                        tvTip.setText("大姨妈预计" + TimeUtils.dateToString((time + zhoqi), "yyyy-MM-dd") +
                                "来");
                    } else {
                        tvTip.setText("大姨妈预计" + TimeUtils.dateToString((time + zhoqi), "yyyy-MM-dd") +
                                "来，还有" + (tianlai / 1000 / 60 / 60 / 24) + "天");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            tvTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD && resultCode == Activity.RESULT_OK) {
            list = LitePal.order("time desc").find(DayimaZhouqiBean.class, true);//倒序排序
            adapter.setNewData(list);
            if (list.size() > 24) {//只保存24条数据（约一年）
                LitePal.delete(DayimaZhouqiBean.class, list.get(list.size() - 1).getId());
                adapter.remove(list.size() - 1);
            }
            update();
        } else if (requestCode == UPDATE_DAYIMA && resultCode == Activity.RESULT_OK) {
            String time = data.getStringExtra("time");
            boolean isDayima = data.getBooleanExtra("isDayima", false);
            adapter.getData().get(pos).setTime(time);
            adapter.getData().get(pos).setDayima(isDayima);
            adapter.notifyItemChanged(pos);

            if (pos == 0) {
                update();
            }
        }
    }

    @OnClick({R.id.fab_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_add://新增
                Intent intent = new Intent(this, AddDayimaActivity.class);
                startActivityForResult(intent, ADD);
                break;
        }
    }
}