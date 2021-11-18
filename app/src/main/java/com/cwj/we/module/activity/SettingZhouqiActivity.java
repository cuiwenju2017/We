package com.cwj.we.module.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.User;
import com.cwj.we.bean.Zhouqi;
import com.cwj.we.utils.LoadingDialog;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.core.BasePopupView;

import java.util.ArrayList;
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
 * 设置周期
 */
public class SettingZhouqiActivity extends BaseActivity {

    @BindView(R.id.tv_zhouqi_day)
    TextView tvZhouqiDay;
    @BindView(R.id.tv_jingqi_day)
    TextView tvJingqiDay;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_function)
    TextView tvRightFunction;
    @BindView(R.id.iv_right_function)
    ImageView ivRightFunction;

    private BasePopupView popupView;
    private List<String> zhouqiList = new ArrayList<>();
    private List<String> jingqiList = new ArrayList<>();
    private int zhouqiDay, jingqiDay;
    private OptionsPickerView pvOptions;
    private LoadingDialog loadingDialog;
    private String objectId, userObjectId;
    private Intent intent;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_zhouqi;
    }

    @Override
    public void initData() {
        for (int i = 1; i <= 90; i++) {
            zhouqiList.add(i + "天");
        }

        for (int i = 1; i <= 15; i++) {
            jingqiList.add(i + "天");
        }

        queryZhouqi();
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

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void done(List<Zhouqi> object, BmobException e) {
                    loadingDialog.dismiss();
                    if (e == null && object.size() > 0) {
                        objectId = object.get(0).getObjectId();
                        zhouqiDay = object.get(0).getZhouqiDay();
                        jingqiDay = object.get(0).getJingqiDay();
                        tvZhouqiDay.setText("" + zhouqiDay);
                        tvJingqiDay.setText("" + jingqiDay);
                    } else {
                        tvZhouqiDay.setText("");
                        tvJingqiDay.setText("");
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

        tvTitle.setText("周期设置");
        loadingDialog = new LoadingDialog(this, "");
    }

    @OnClick({R.id.btn_save, R.id.iv_back, R.id.tv_zhouqi_day, R.id.tv_jingqi_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_zhouqi_day://周期天数选择
                pvOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
                    //返回的分别是三个级别的选中位置
                    zhouqiDay = options1 + 1;
                    tvZhouqiDay.setText("" + zhouqiDay);
                })
                        .setDividerColor(Color.RED)//当前选中日期线条颜色
                        .setSubmitColor(Color.RED)//确定按钮文字颜色
                        .setCancelColor(Color.RED)//取消按钮文字颜色
                        .build();
                pvOptions.setPicker(zhouqiList);
                pvOptions.setSelectOptions(TextUtils.isEmpty(tvZhouqiDay.getText().toString()) ? 0 : Integer.parseInt(tvZhouqiDay.getText().toString()) - 1);
                pvOptions.show();
                break;
            case R.id.tv_jingqi_day://经期天数选择
                pvOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
                    //返回的分别是三个级别的选中位置
                    jingqiDay = options1 + 1;
                    tvJingqiDay.setText("" + jingqiDay);
                })
                        .setDividerColor(Color.RED)//当前选中日期线条颜色
                        .setSubmitColor(Color.RED)//确定按钮文字颜色
                        .setCancelColor(Color.RED)//取消按钮文字颜色
                        .build();
                pvOptions.setPicker(jingqiList);
                pvOptions.setSelectOptions(TextUtils.isEmpty(tvJingqiDay.getText().toString()) ? 0 : Integer.parseInt(tvJingqiDay.getText().toString()) - 1);
                pvOptions.show();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_save://保存
                if (TextUtils.isEmpty(tvZhouqiDay.getText())) {
                    ToastUtil.showTextToast(this, "周期天数不能为空");
                } else if (TextUtils.isEmpty(tvJingqiDay.getText())) {
                    ToastUtil.showTextToast(this, "经期天数不能为空");
                } else {
                    if (objectId == null) {
                        loadingDialog.show();
                        addZhouqi();
                    } else {
                        loadingDialog.show();
                        updataZhouqi();
                    }
                }
                break;
            default:
                break;
        }
    }

    SharedPreferences sprfMain;

    /**
     * 修改一对一关联，修改周期天数
     */
    private void updataZhouqi() {
        sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
        userObjectId = sprfMain.getString("userObjectId", "");
        User user = new User();
        user.setObjectId(userObjectId);
        Zhouqi zhouqi = new Zhouqi();
        zhouqi.setObjectId(objectId);
        zhouqi.setZhouqiDay(zhouqiDay);
        zhouqi.setJingqiDay(jingqiDay);
        //修改一对一关联
        zhouqi.setAuthor(user);
        zhouqi.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                loadingDialog.dismiss();
                if (e == null) {
                    ToastUtil.showTextToast(SettingZhouqiActivity.this, "修改成功");
                    intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    ToastUtil.showTextToast(SettingZhouqiActivity.this, e.getMessage());
                }
            }
        });
    }

    /**
     * 添加一对一关联，当前用户添加周期天数
     */
    private void addZhouqi() {
        if (BmobUser.isLogin()) {
            Zhouqi zhouqi = new Zhouqi();
            zhouqi.setZhouqiDay(zhouqiDay);
            zhouqi.setJingqiDay(jingqiDay);
            //添加一对一关联，用户关联日期
            zhouqi.setAuthor(BmobUser.getCurrentUser(User.class));
            zhouqi.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    loadingDialog.dismiss();
                    if (e == null) {
                        ToastUtil.showTextToast(SettingZhouqiActivity.this, "保存成功");
                        intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                    } else {
                        ToastUtil.showTextToast(SettingZhouqiActivity.this, e.getMessage());
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