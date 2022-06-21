package com.cwj.we.module.fragment;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.base.BaseFragment;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.GameBean;
import com.cwj.we.module.activity.VipJiexiActivity;
import com.cwj.we.module.activity.WebViewActivity;
import com.cwj.we.module.adapter.GameAdapter;
import com.cwj.we.utils.OneClickThree;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 影视
 */
public class YingshiFragment extends BaseFragment {

    @BindView(R.id.rv_yingshi)
    RecyclerView rvYingshi;
    private Intent intent;
    private List<GameBean> gameBeans = new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yingshi;
    }

    @Override
    protected void initView() {
        GameBean ddt = new GameBean("热影库", R.drawable.icon_byj);
        gameBeans.add(ddt);
        GameBean spjx = new GameBean("视频解析", R.drawable.icon_byj);
        gameBeans.add(spjx);
        GameBean rrsp = new GameBean("人人视频", R.drawable.icon_byj);
        gameBeans.add(rrsp);
        GameBean dszb = new GameBean("电视直播", R.drawable.icon_byj);
        gameBeans.add(dszb);
        GameBean wdyy = new GameBean("武德影院", R.drawable.icon_byj);
        gameBeans.add(wdyy);
    }

    @Override
    protected void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvYingshi.setLayoutManager(layoutManager);
        GameAdapter adapter = new GameAdapter(gameBeans);
        rvYingshi.setAdapter(adapter);

        adapter.setOnclick((view1, position) -> {
            if (!OneClickThree.isFastClick()) {
                if (position == 0) {//热影库
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://www.reyingku.cc/");
                    startActivity(intent);
                } else if (position == 1) {//视频解析
                    intent = new Intent(getActivity(), VipJiexiActivity.class);
                    startActivity(intent);
                } else if (position == 2) {//人人视频
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://m.rr.tv/");
                    startActivity(intent);
                } else if (position == 3) {//电视直播
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://m.hao5.net/");
                    startActivity(intent);
                } else if (position == 4) {//武德影院
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://wudeyy.com/");
                    startActivity(intent);
                }
            }
        });
    }

   /* @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)  //状态栏字体是深色，不写默认为亮色
                .init();
    }*/
}
