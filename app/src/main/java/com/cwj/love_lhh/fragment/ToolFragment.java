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
import com.cwj.love_lhh.activity.CalculatorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ToolFragment extends Fragment {

    @BindView(R.id.rl_calculator)
    RelativeLayout rlCalculator;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tool, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.rl_calculator, R.id.rl_hero})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_calculator:
                startActivity(new Intent(getActivity(), CalculatorActivity.class));
                break;
        }
    }
}
