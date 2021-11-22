package com.cwj.we.module.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.Post;
import com.cwj.we.bean.User;
import com.cwj.we.utils.LoadingDialog;
import com.cwj.we.utils.OneClickThree;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 发帖
 */
public class AddTieZiActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_fabu)
    Button btnFabu;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rl_choose_type)
    RelativeLayout rlChooseType;
    private int type = 0;
    private LoadingDialog loadingDialog;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_tie_zi;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
                .init();
        tvTitle.setText("发布");
        tvType.setText("段子");
        loadingDialog = new LoadingDialog(this, "");
    }

    @OnClick({R.id.ll_back, R.id.btn_fabu, R.id.rl_choose_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_choose_type://帖子分类
                new XPopup.Builder(this)
                        //.maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"段子", "树洞", "百科", "其他"},
                                (position, text) -> {
                                    type = position;
                                    tvType.setText(text);
                                })
                        .show();
                break;
            case R.id.btn_fabu://发布帖子
                if (TextUtils.isEmpty(etTitle.getText().toString())) {
                    ToastUtil.showTextToast(this, "标题不能为空");
                } else if (TextUtils.isEmpty(tvType.getText().toString())) {
                    ToastUtil.showTextToast(this, "请选择类型");
                } else if (TextUtils.isEmpty(etContent.getText().toString())) {
                    ToastUtil.showTextToast(this, "内容不能为空");
                } else {
                    if (!OneClickThree.isFastClick()) {
                        loadingDialog.show();
                        savePost();
                    }
                }
                break;
            case R.id.ll_back:
                finish();
                break;
        }
    }

    /**
     * 添加一对一关联，当前用户发布帖子
     */
    private void savePost() {
        if (BmobUser.isLogin()) {
            Post post = new Post();
            post.setTitle(etTitle.getText().toString());
            post.setType(type);
            post.setContent(etContent.getText().toString());
            post.setUsername(BmobUser.getCurrentUser(User.class).getUsername());
            //添加一对一关联，用户关联帖子
            post.setAuthor(BmobUser.getCurrentUser(User.class));
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        loadingDialog.dismiss();
                        ToastUtil.showTextToast(AddTieZiActivity.this, "发布成功");
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        loadingDialog.dismiss();
                        Log.e("BMOB", e.toString());
                        ToastUtil.showTextToast(AddTieZiActivity.this, e.getMessage());
                    }
                }
            });
        } else {
            loadingDialog.dismiss();
            ToastUtil.showTextToast(this, "请先登录");
        }
    }
}