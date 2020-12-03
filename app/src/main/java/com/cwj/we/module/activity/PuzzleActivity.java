package com.cwj.we.module.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cwj.we.R;
import com.jaeger.library.StatusBarUtil;

public class PuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
    }
}
