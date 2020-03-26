package com.cwj.love_lhh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.app.App;
import com.cwj.love_lhh.utils.ActivityCollector;
import com.cwj.love_lhh.utils.LoadingDialog;
import com.cwj.love_lhh.utils.ToastUtil;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_verify_password)
    EditText etVerifyPassword;
    @BindView(R.id.btn_change_password)
    Button btnChangePassword;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
        loadingDialog = new LoadingDialog(ChangePasswordActivity.this, "提交中...");
        tvTitle.setText("修改密码");
    }

    /**
     * 提供旧密码修改密码
     */
    private void updatePassword(final View view) {
        BmobUser.updateCurrentUserPassword(etOldPassword.getText().toString(), etNewPassword.getText().toString(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.showTextToast(ChangePasswordActivity.this, "修改成功");
                    loadingDialog.dismiss();
                    BmobUser.logOut();//退出登录，同时清除缓存用户对象。
                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                    finish();
                    //结束之前所有的Activity
                    ActivityCollector.finishall();
                } else {
                    loadingDialog.dismiss();
                    ToastUtil.showTextToast(ChangePasswordActivity.this, "原密码不正确");
                }
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.btn_change_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_change_password://修改密码
                if (TextUtils.isEmpty(etOldPassword.getText().toString())) {
                    ToastUtil.showTextToast(ChangePasswordActivity.this, "原密码不能为空");
                } else if (TextUtils.isEmpty(etNewPassword.getText().toString())) {
                    ToastUtil.showTextToast(ChangePasswordActivity.this, "新密码不能为空");
                } else if (!etNewPassword.getText().toString().equals(etVerifyPassword.getText().toString())) {
                    ToastUtil.showTextToast(ChangePasswordActivity.this, "两次密码输入不一致");
                } else {
                    loadingDialog.show();
                    updatePassword(view);
                }
                break;
        }
    }
}
