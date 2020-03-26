package com.cwj.love_lhh.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.model.User;
import com.cwj.love_lhh.utils.LoadingDialog;
import com.cwj.love_lhh.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册
 */
public class RegisterActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
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
                    loadingDialog.show();
                    signUp(view);
                }
                break;
        }
    }
}
