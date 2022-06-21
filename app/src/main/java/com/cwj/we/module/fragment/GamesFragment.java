package com.cwj.we.module.fragment;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.base.BaseFragment;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.GameBean;
import com.cwj.we.module.activity.AircraftBattleActivity;
import com.cwj.we.module.activity.DaanzhishuActivity;
import com.cwj.we.module.activity.ELuoSiFangKuaiActivity;
import com.cwj.we.module.activity.GobangActivity;
import com.cwj.we.module.activity.PuzzleActivity;
import com.cwj.we.module.activity.WebViewActivity;
import com.cwj.we.module.adapter.GameAdapter;
import com.cwj.we.module.jiqiren.JiqirenActivity;
import com.cwj.we.utils.OneClickThree;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 小游戏
 */
public class GamesFragment extends BaseFragment {

    @BindView(R.id.rv_game)
    RecyclerView rvGame;

    private List<GameBean> gameBeans = new ArrayList<>();
    private Intent intent;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_games;
    }

    @Override
    protected void initView() {
        GameBean fjdz = new GameBean("飞机大战", R.drawable.plane);
        gameBeans.add(fjdz);
        GameBean pt = new GameBean("拼图", R.drawable.bb);
        gameBeans.add(pt);
        GameBean wzq = new GameBean("五子棋", R.drawable.chess);
        gameBeans.add(wzq);
        GameBean xbw = new GameBean("小霸王", R.drawable.icon_xbw);
        gameBeans.add(xbw);
        GameBean zhinengjiqiren = new GameBean("智能机器人", R.drawable.icon_jiqiren);
        gameBeans.add(zhinengjiqiren);
        GameBean eluosifangkuan = new GameBean("俄罗斯方块", R.drawable.icon_eluosifangkuai);
        gameBeans.add(eluosifangkuan);
        GameBean daanzhishu = new GameBean("答案之书", R.drawable.icon_daanzhishu);
        gameBeans.add(daanzhishu);
    }

    protected void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGame.setLayoutManager(layoutManager);
        GameAdapter adapter = new GameAdapter(gameBeans);
        rvGame.setAdapter(adapter);

        adapter.setOnclick((view1, position) -> {
            if (!OneClickThree.isFastClick()) {
                if (position == 0) {//飞机大战
                    startActivity(new Intent(getActivity(), AircraftBattleActivity.class));
                } else if (position == 1) {//拼图
                    startActivity(new Intent(getActivity(), PuzzleActivity.class));
                } else if (position == 2) {//五子棋
                    startActivity(new Intent(getActivity(), GobangActivity.class));
                } else if (position == 3) {//小霸王
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "https://www.yikm.net/");
                    intent.putExtra("xbw", true);
                    startActivity(intent);
                } else if (position == 4) {//智能机器人
                    intent = new Intent(getActivity(), JiqirenActivity.class);
                    startActivity(intent);
                } else if (position == 5) {//俄罗斯方块
                    intent = new Intent(getActivity(), ELuoSiFangKuaiActivity.class);
                    startActivity(intent);
                } else if (position == 6) {//答案之书
                    intent = new Intent(getActivity(), DaanzhishuActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /*@Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)  //状态栏字体是深色，不写默认为亮色
                .init();
    }*/
}
