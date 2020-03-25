package com.cwj.love_lhh.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.activity.CalculatorActivity;
import com.cwj.love_lhh.activity.ClockActivity;
import com.cwj.love_lhh.activity.CompassActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ToolFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.ll_calculator)
    LinearLayout llCalculator;
    @BindView(R.id.ll_compass)
    LinearLayout llCompass;
    @BindView(R.id.ll_clock)
    LinearLayout llClock;
    private Intent intent;
    private Uri content_url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tool, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.ll_calculator, R.id.ll_compass, R.id.ll_clock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_calculator://计算器
                startActivity(new Intent(getActivity(), CalculatorActivity.class));
                break;
            case R.id.ll_compass://指南针
                startActivity(new Intent(getActivity(), CompassActivity.class));
                break;
            case R.id.ll_clock://时钟
                startActivity(new Intent(getActivity(), ClockActivity.class));
                break;
           /* case R.id.ll_niubabavip://牛巴巴vip解析
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                content_url = Uri.parse("http://mv.688ing.com/?qqdrsign=0378b");
                intent.setData(content_url);
                startActivity(intent);
                break;*/
        }
    }
}
