package com.cwj.love_lhh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.activity.WebViewActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GamesFragment extends Fragment {

    @BindView(R.id.rl_ball)
    RelativeLayout rlBall;
    @BindView(R.id.rl_hero)
    RelativeLayout rlHero;
    Unbinder unbinder;

    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.rl_ball, R.id.rl_hero})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_ball:
                intent = new Intent(getActivity(), WebViewActivity.class);
//        intent.putExtra("url", "file:///android_asset/gunqiu.html");
                intent.putExtra("url", "http://xcdn.php.cn/js/html5/H5%203D%E6%BB%9A%E7%90%83%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=8602777ddf8e8801fcaac1e826ae4c48&timestamp=1583716386");
                startActivity(intent);
                break;
            case R.id.rl_hero:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://xcdn.php.cn/js/html5/%E6%89%8B%E6%9C%BA%E6%96%B9%E5%9D%97%E8%8B%B1%E9%9B%84%E9%97%AF%E5%85%B3%E6%B8%B8%E6%88%8F%E6%BA%90%E7%A0%81/index.html?sign=882c74c1c690f68d70fb40a9d09bc121&timestamp=1584007385");
                startActivity(intent);
                break;
        }
    }
}
