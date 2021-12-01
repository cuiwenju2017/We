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
import com.gyf.immersionbar.ImmersionBar;

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
        GameBean gdqq = new GameBean("滚动的球球", R.drawable.loader);
        gameBeans.add(gdqq);
        GameBean fkyx = new GameBean("方块英雄", R.drawable.icon_fangkuaiyingxiong);
        gameBeans.add(fkyx);
        GameBean fjdz = new GameBean("飞机大战", R.drawable.plane);
        gameBeans.add(fjdz);
        GameBean pt = new GameBean("拼图", R.drawable.bb);
        gameBeans.add(pt);
        GameBean wzq = new GameBean("五子棋", R.drawable.chess);
        gameBeans.add(wzq);
        GameBean xmtt = new GameBean("熊猫弹跳", R.drawable.panda_bounce);
        gameBeans.add(xmtt);
        GameBean dfz = new GameBean("叠房子", R.drawable.diefangzi);
        gameBeans.add(dfz);
        GameBean mfyx = new GameBean("魔方游戏", R.drawable.mofantg);
        gameBeans.add(mfyx);
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
                if (position == 0) {//滚动的球球
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    //intent.putExtra("url", "file:///android_asset/gunqiu.html");
                    intent.putExtra("url", "http://xcdn.php.cn/js/html5/H5%203D%E6%BB%9A%E7%90%83%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=8602777ddf8e8801fcaac1e826ae4c48&timestamp=1583716386");
                    startActivity(intent);
                } else if (position == 1) {//方块英雄
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://xcdn.php.cn/js/html5/%E6%89%8B%E6%9C%BA%E6%96%B9%E5%9D%97%E8%8B%B1%E9%9B%84%E9%97%AF%E5%85%B3%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=882c74c1c690f68d70fb40a9d09bc121&timestamp=1584007385");
                    startActivity(intent);
                } else if (position == 2) {//飞机大战
                    startActivity(new Intent(getActivity(), AircraftBattleActivity.class));
                } else if (position == 3) {//拼图
                    startActivity(new Intent(getActivity(), PuzzleActivity.class));
                } else if (position == 4) {//五子棋
                    startActivity(new Intent(getActivity(), GobangActivity.class));
                } else if (position == 5) {//熊猫弹跳
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://xcdn.php.cn/js/html5/H5%E7%86%8A%E7%8C%AB%E5%BC%B9%E8%B7%B3%E5%B0%8F%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=0aaa46d743354d1483cb60b7b85973e3&timestamp=1588846357");
                    startActivity(intent);
                } else if (position == 6) {//叠房子
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://xcdn.php.cn/js/CSS3/JS%E5%8F%A0%E6%88%BF%E5%AD%90%E6%B6%88%E6%B6%88%E4%B9%90%E5%B0%8F%E6%B8%B8%E6%88%8F%E4%BB%A3%E7%A0%81/index.html?sign=abf273724af50f45227c4c4bd88633e4&timestamp=1588847018");
                    startActivity(intent);
                } else if (position == 7) {//魔方游戏
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://xcdn.php.cn/js/html5/HTML5+three%E5%AE%9E%E7%8E%B03D%E9%85%B7%E7%82%AB%E6%8B%BC%E9%AD%94%E6%96%B9%E6%B8%B8%E6%88%8F%E7%89%B9%E6%80%A7/index.html?sign=81af0d704fbd2e1602487e87af8e03f2&timestamp=1588848116");
                    startActivity(intent);
                } else if (position == 8) {//小霸王
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "https://www.yikm.net/");
                    startActivity(intent);
                } else if (position == 9) {//智能机器人
                    intent = new Intent(getActivity(), JiqirenActivity.class);
                    startActivity(intent);
                } else if (position == 10) {//俄罗斯方块
                    intent = new Intent(getActivity(), ELuoSiFangKuaiActivity.class);
                    startActivity(intent);
                } else if (position == 11) {//答案之书
                    intent = new Intent(getActivity(), DaanzhishuActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)  //状态栏字体是深色，不写默认为亮色
                .init();
    }
}
