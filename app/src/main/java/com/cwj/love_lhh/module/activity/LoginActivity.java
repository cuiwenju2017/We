package com.cwj.love_lhh.module.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.cwj.love_lhh.R;
import com.cwj.love_lhh.bean.User;
import com.cwj.love_lhh.utils.LoadingDialog;
import com.cwj.love_lhh.utils.ToastUtil;
import com.hyb.library.PreventKeyboardBlockUtil;
import com.jaeger.library.StatusBarUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 登录
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
        loadingDialog = new LoadingDialog(LoginActivity.this, "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreventKeyboardBlockUtil.getInstance(this).setBtnView(tvRegister).register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreventKeyboardBlockUtil.getInstance(this).unRegister();
    }

    /**
     * 账号密码登录
     */
    private void loginByAccount(final View view) {
        //此处替换为你的用户名密码
        BmobUser.loginByAccount(etUsername.getText().toString(), etPassword.getText().toString(), new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    ToastUtil.showTextToast(LoginActivity.this, "登录成功");
                    loadingDialog.dismiss();
                    sprfMain = getSharedPreferences("counter", Context.MODE_PRIVATE);
                    editorMain = sprfMain.edit();
                    editorMain.putString("userObjectId", user.getObjectId());
                    editorMain.putString("username", user.getUsername());
                    editorMain.commit();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    loadingDialog.dismiss();
                    ToastUtil.showTextToast(LoginActivity.this, "用户名或密码错误");
                }
            }
        });
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    ToastUtil.showTextToast(LoginActivity.this, "账号或密码不能为空");
                } else {
                    loadingDialog.show();
                    loginByAccount(view);
                }
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
