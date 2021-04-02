package com.cwj.we.module.ljxj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;

/**
 * 图片预览
 */
public class PhotoViewActivity extends BaseActivity {

    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.cl_view)
    CoordinatorLayout clView;

    private MyImageAdapter adapter;
    private List<String> imgList;
    private int currentPosition;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        imgList = intent.getStringArrayListExtra("dataBean");

        tv.setText((currentPosition + 1) + "/" + imgList.size());

        adapter = new MyImageAdapter(imgList, this);
        viewPage.setAdapter(adapter);
        viewPage.setCurrentItem(currentPosition, false);
        viewPage.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                tv.setText((currentPosition + 1) + "/" + imgList.size());
            }
        });
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .init();
    }
}
