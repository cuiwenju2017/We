package com.cwj.we.module.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.User;
import com.cwj.we.utils.LoadingDialog;
import com.cwj.we.utils.OneClickThree;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_verify_password)
    EditText etVerifyPassword;
    @BindView(R.id.btn_regiest)
    Button btnRegiest;
    @BindView(R.id.ll_back)
    LinearLayout llBack;

    private LoadingDialog loadingDialog;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {

    }

    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
        loadingDialog = new LoadingDialog(RegisterActivity.this, "注册中...");
        tvTitle.setText("注册");
    }

    /**
     * 账号密码注册
     */
    private void signUp(final View view) {
        final User user = new User();
        user.setUsername("" + etUsername.getText().toString());
        user.setPassword("" + etPassword.getText().toString());
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    ToastUtil.showTextToast(RegisterActivity.this, "注册成功");
                    loadingDialog.dismiss();
                    finish();
                } else {
                    loadingDialog.dismiss();
                    ToastUtil.showTextToast(RegisterActivity.this, "该用户名已被注册，换个试试");
                }
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.btn_regiest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_regiest:
                if (TextUtils.isEmpty(etUsername.getText().toString())) {
                    ToastUtil.showTextToast(RegisterActivity.this, "用户名不能为空");
                } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    ToastUtil.showTextToast(RegisterActivity.this, "密码不能为空");
                } else if (!etPassword.getText().toString().equals(etVerifyPassword.getText().toString())) {
                    ToastUtil.showTextToast(RegisterActivity.this, "两次密码输入不一致");
                } else {
                    if (!OneClickThree.isFastClick()) {
                        loadingDialog.show();
                        signUp(view);
                    } else {
                        ToastUtil.showTextToast(this, "请不要频繁操作");
                    }
                }
                break;
        }
    }
}
