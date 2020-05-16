package com.cwj.love_lhh.module.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.module.activity.AircraftBattleActivity;
import com.cwj.love_lhh.module.activity.GobangActivity;
import com.cwj.love_lhh.module.activity.PuzzleActivity;
import com.cwj.love_lhh.module.activity.WebViewActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GamesFragment extends Fragment {

    @BindView(R.id.ll_ball)
    LinearLayout llBall;
    @BindView(R.id.ll_hero)
    LinearLayout llHero;
    @BindView(R.id.ll_aircraft_battle)
    LinearLayout llAircraftBattle;
    Unbinder unbinder;
    @BindView(R.id.ll_puzzle)
    LinearLayout ll_puzzle;
    @BindView(R.id.ll_gobang)
    LinearLayout llGobang;
    @BindView(R.id.ll_paopaolong)
    LinearLayout llPaopaolong;
    @BindView(R.id.ll_diefangzhi)
    LinearLayout llDiefangzhi;
    @BindView(R.id.ll_feixingqi)
    LinearLayout llFeixingqi;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.ll_feixingqi, R.id.ll_diefangzhi, R.id.ll_paopaolong, R.id.ll_ball, R.id.ll_hero, R.id.ll_aircraft_battle, R.id.ll_puzzle, R.id.ll_gobang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_feixingqi:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://xcdn.php.cn/js/html5/HTML5+three%E5%AE%9E%E7%8E%B03D%E9%85%B7%E7%82%AB%E6%8B%BC%E9%AD%94%E6%96%B9%E6%B8%B8%E6%88%8F%E7%89%B9%E6%80%A7/index.html?sign=81af0d704fbd2e1602487e87af8e03f2&timestamp=1588848116");
                startActivity(intent);
                break;
            case R.id.ll_diefangzhi://叠房子
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://xcdn.php.cn/js/CSS3/JS%E5%8F%A0%E6%88%BF%E5%AD%90%E6%B6%88%E6%B6%88%E4%B9%90%E5%B0%8F%E6%B8%B8%E6%88%8F%E4%BB%A3%E7%A0%81/index.html?sign=abf273724af50f45227c4c4bd88633e4&timestamp=1588847018");
                startActivity(intent);
                break;
            case R.id.ll_paopaolong://泡泡龙
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://xcdn.php.cn/js/html5/H5%E7%86%8A%E7%8C%AB%E5%BC%B9%E8%B7%B3%E5%B0%8F%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=0aaa46d743354d1483cb60b7b85973e3&timestamp=1588846357");
                startActivity(intent);
                break;
            case R.id.ll_ball://滚动的球球
                intent = new Intent(getActivity(), WebViewActivity.class);
//        intent.putExtra("url", "file:///android_asset/gunqiu.html");
                intent.putExtra("url", "http://xcdn.php.cn/js/html5/H5%203D%E6%BB%9A%E7%90%83%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=8602777ddf8e8801fcaac1e826ae4c48&timestamp=1583716386");
                startActivity(intent);
                break;
            case R.id.ll_hero://方块英雄
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://xcdn.php.cn/js/html5/%E6%89%8B%E6%9C%BA%E6%96%B9%E5%9D%97%E8%8B%B1%E9%9B%84%E9%97%AF%E5%85%B3%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=882c74c1c690f68d70fb40a9d09bc121&timestamp=1584007385");
                startActivity(intent);
                break;
            case R.id.ll_aircraft_battle://飞机大战
                startActivity(new Intent(getActivity(), AircraftBattleActivity.class));
                break;
            case R.id.ll_puzzle://拼图
                startActivity(new Intent(getActivity(), PuzzleActivity.class));
                break;
            case R.id.ll_gobang://五子棋
                startActivity(new Intent(getActivity(), GobangActivity.class));
                break;

        }
    }
}
