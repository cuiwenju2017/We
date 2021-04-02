package com.cwj.we.module.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.we.R;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockActivity extends AppCompatActivity {

    @BindView(R.id.cl_view)
    CoordinatorLayout clView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                .init();
    }
}
