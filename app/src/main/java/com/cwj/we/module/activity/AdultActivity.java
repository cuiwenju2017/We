package com.cwj.we.module.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.GameBean;
import com.cwj.we.module.adapter.GameAdapter;
import com.cwj.we.utils.OneClickThree;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 成人影视
 */
public class AdultActivity extends BaseActivity {

    @BindView(R.id.rv_yingshi)
    RecyclerView rvYingshi;

    private List<GameBean> gameBeans = new ArrayList<>();
    private Intent intent;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adult;
    }

    @Override
    public void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvYingshi.setLayoutManager(layoutManager);
        GameAdapter adapter = new GameAdapter(gameBeans);
        rvYingshi.setAdapter(adapter);

        adapter.setOnclick((view1, position) -> {
            if (!OneClickThree.isFastClick()) {
                if (position == 0) {//56maoee.com
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://56maoee.com/");
                    startActivity(intent);
                } else if (position == 1) {//11caoff.com
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://11caoff.com/");
                    startActivity(intent);
                } else if (position == 2) {//sebb11.com
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://sebb11.com/");
                    startActivity(intent);
                } else if (position == 3) {//twav5.xyz
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "https://twav5.xyz/");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();

        GameBean maoee = new GameBean("56maoee.com", R.drawable.icon_byj);
        gameBeans.add(maoee);
        GameBean caoff = new GameBean("11caoff.com", R.drawable.icon_byj);
        gameBeans.add(caoff);
        GameBean sebb = new GameBean("sebb11.com", R.drawable.icon_byj);
        gameBeans.add(sebb);
        GameBean twav = new GameBean("twav5.xyz", R.drawable.icon_byj);
        gameBeans.add(twav);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}