package com.cwj.we.module.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cwj.we.R;
import com.gyf.immersionbar.ImmersionBar;

public class PuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
    }
}
