package com.cwj.we.module.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.base.BaseRVAdapter;
import com.cwj.we.base.BaseRVHolder;
import com.cwj.we.bean.JingqiTime;
import com.cwj.we.bean.User;
import com.cwj.we.bean.Zhouqi;
import com.cwj.we.utils.LoadingDialog;
import com.cwj.we.utils.TimeUtils;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 大姨妈
 */
public class DayimaActivity extends BaseActivity {

    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.btn_isZou)
    Button btnIsZou;
    @BindView(R.id.btn_isLai)
    Button btnIsLai;
    @BindView(R.id.ll)
    LinearLayout ll;

    private TimePickerView pvTime;
    private String time;
    private Calendar selectedDate;
    private Intent intent;
    private LoadingDialog loadingDialog;
    private int skip = 1;
    private int limit = 15;
    private int srlType = 0;
    private BaseRVAdapter<JingqiTime> adapter;
    private String state;
    private int pos;
    private int zhouqiDay, jingqiDay;
    private int ZQ_SETTING = 201;
    private String lastTime;
    private boolean lastType;
    private List<JingqiTime> jingqiTimeSize;

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
        queryJingqiList();
        adapter = new BaseRVAdapter<JingqiTime>(R.layout.item_dayima) {
            @Override
            public void onBindVH(BaseRVHolder holder, JingqiTime data, int position) {
                holder.setText(R.id.tv_time, data.getTime());
                if (data.isType()) {
                    state = "大姨妈来了";
                } else {
                    state = "大姨妈走了";
                }
                holder.setText(R.id.tv_isState, state);

                holder.getView(R.id.tv_delete).setOnClickListener(v -> {//删除
                    new XPopup.Builder(DayimaActivity.this).asConfirm("提示", "确定删除这条数据吗？",
                            () -> {
                                loadingDialog.show();
                                pos = position;
                                removeJingqiTime(data.getObjectId());
                            })
                            .show();
                });
            }
        };

        View empty = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, null, false);
        TextView tv = empty.findViewById(R.id.tv);
        tv.setTextColor(getResources().getColor(R.color.black));
        adapter.setEmptyView(empty);

        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(this, 1));//列数设置

        srl.setOnRefreshListener(refreshLayout -> {//刷新
            srlType = 0;
            skip = 1;
            queryJingqiList();
        });

        srl.setOnLoadMoreListener(refreshLayout -> {//加载更多
            srlType = 1;
            skip = ++skip;
            queryJingqiList();
        });
    }

    /**
     * 查询一对一关联，查询当前用户下周期天数
     */
    private void queryZhouqi() {
        if (BmobUser.isLogin()) {
            BmobQuery<Zhouqi> query = new BmobQuery<>();
            query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("author");
            query.findObjects(new FindListener<Zhouqi>() {

                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void done(List<Zhouqi> object, BmobException e) {
                    loadingDialog.dismiss();
                    if (e == null && object.size() > 0) {
                        if (jingqiTimeSize.size() > 0) {
                            lastTime = jingqiTimeSize.get(0).getTime();
                            lastType = jingqiTimeSize.get(0).isType();
                            ll.setVisibility(View.VISIBLE);
                            zhouqiDay = object.get(0).getZhouqiDay();//周期天数
                            jingqiDay = object.get(0).getJingqiDay();//月经天数

                            if (lastType) {
                                try {
                                    long longLastTime = Long.parseLong(TimeUtils.dateToStamp2(lastTime));//最后一条数据时间戳
                                    Date date = new Date(longLastTime);
                                    long longZou = Long.parseLong(TimeUtils.dateToStamp2(convDate2Str(datePlus(date, jingqiDay), "yyyy-MM-dd")));//大姨妈走的时间戳
                                    tvTip.setText("大姨妈预计" + convDate2Str(datePlus(date, jingqiDay), "yyyy-MM-dd") + "走," +
                                            "还有" + ((longZou - TimeUtils.getTimeStame()) / (24 * 60 * 60 * 1000) + 1) + "天");
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                try {
                                    long longLastTime = Long.parseLong(TimeUtils.dateToStamp2(lastTime));//最后一条数据时间戳
                                    Date date = new Date(longLastTime);
                                    long longLai = Long.parseLong(TimeUtils.dateToStamp2(convDate2Str(datePlus(date, zhouqiDay), "yyyy-MM-dd")));//大姨妈来的时间戳
                                    tvTip.setText("大姨妈预计" + convDate2Str(datePlus(date, zhouqiDay), "yyyy-MM-dd") + "来," +
                                            "还有" + ((longLai - TimeUtils.getTimeStame()) / (24 * 60 * 60 * 1000) + 1) + "天");
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } else if (srlType != 1) {
                            ll.setVisibility(View.VISIBLE);
                            tvTip.setText("快来记录一下吧");
                        }
                    } else {
                        tvTip.setText("请先设置经期周期哦");
                    }
                }
            });
        } else {
            loadingDialog.dismiss();
            ToastUtil.showTextToast(this, "请先登录");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    /**
     * 日期加减。
     *
     * @param base 基础日期
     * @param days 增加天数(减天数则用负数)
     * @return 计算结果
     */
    public static Date datePlus(Date base, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(base);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 将日期转换成指定格式的字符串。
     *
     * @param date   日期
     * @param format 输出格式
     * @return 日期字符串
     */
    public static String convDate2Str(Date date, String format) {
        if (date == null) {
            return "";
        }
        return DateFormat.format(format, date).toString();
    }

    /**
     * 删除一行数据
     */
    private void removeJingqiTime(String objectId) {
        JingqiTime jingqiTime = new JingqiTime();
        jingqiTime.setObjectId(objectId);
        jingqiTime.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    loadingDialog.dismiss();
                    adapter.remove(pos);
                    ToastUtil.showTextToast(DayimaActivity.this, "删除成功");
                    srlType = 0;
                    skip = 1;
                    queryJingqiList();
                } else {
                    loadingDialog.dismiss();
                    ToastUtil.showTextToast(DayimaActivity.this, "" + e.getMessage());
                }
            }
        });
    }

    /**
     * 查询一对一关联，查询当前用户下经期列表
     */
    private void queryJingqiList() {
        if (BmobUser.isLogin()) {
            BmobQuery<JingqiTime> query = new BmobQuery<>();
            query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("author");
            query.setPage(skip, limit).order("-time").findObjects(new FindListener<JingqiTime>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void done(List<JingqiTime> object, BmobException e) {
                    if (e == null) {
                        jingqiTimeSize = object;
                        queryZhouqi();

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
                    }
                }
            });
        } else {
            loadingDialog.dismiss();
            ToastUtil.showTextToast(this, "请先登录");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
        loadingDialog = new LoadingDialog(this, "");

        selectedDate = Calendar.getInstance();//系统当前时间
        time = TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy-MM-dd");
        tvTime.setText(time);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ZQ_SETTING && resultCode == Activity.RESULT_OK) {
            queryZhouqi();
        }
    }

    @OnClick({R.id.iv_setting, R.id.tv_time, R.id.btn_isZou, R.id.btn_isLai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting://设置周期
                intent = new Intent(this, SettingZhouqiActivity.class);
                startActivityForResult(intent, ZQ_SETTING);
                break;
            case R.id.tv_time://时间选择
                Calendar startDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                startDate.set(1900, 0, 1);
                //时间选择器
                pvTime = new TimePickerBuilder(this, (date, v) -> {
                    time = getTime(date);
                    tvTime.setText(time);
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setRangDate(startDate, selectedDate)//起始终止年月日设定
                        .setDividerColor(Color.RED)//当前选中日期线条颜色
                        .setSubmitColor(Color.RED)//确定按钮文字颜色
                        .setCancelColor(Color.RED)//取消按钮文字颜色
                        .build();
                pvTime.setDate(selectedDate);// 如果不设置的话，默认是系统时间*/
                pvTime.show();
                break;
            case R.id.btn_isZou://大姨妈走了
                new XPopup.Builder(DayimaActivity.this).asConfirm("提示", "确定记录吗？",
                        () -> {
                            loadingDialog.show();
                            addJingqi(false);
                        })
                        .show();
                break;
            case R.id.btn_isLai://大姨妈来了
                new XPopup.Builder(DayimaActivity.this).asConfirm("提示", "确定记录吗？",
                        () -> {
                            loadingDialog.show();
                            addJingqi(true);
                        })
                        .show();
                break;
        }
    }

    /**
     * 添加一对一关联，当前用户添加经期
     */
    private void addJingqi(boolean type) {
        if (BmobUser.isLogin()) {
            JingqiTime jingqiTime = new JingqiTime();
            jingqiTime.setTime(time);
            jingqiTime.setType(type);
            //添加一对一关联，用户关联日期
            jingqiTime.setAuthor(BmobUser.getCurrentUser(User.class));
            jingqiTime.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    loadingDialog.dismiss();
                    if (e == null) {
                        ToastUtil.showTextToast(DayimaActivity.this, "记录成功");
                        srlType = 0;
                        skip = 1;
                        queryJingqiList();
                    } else {
                        ToastUtil.showTextToast(DayimaActivity.this, e.getMessage());
                    }
                }
            });
        } else {
            loadingDialog.dismiss();
            ToastUtil.showTextToast(this, "请先登录");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}