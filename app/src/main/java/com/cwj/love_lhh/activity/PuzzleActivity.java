package com.cwj.love_lhh.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cwj.love_lhh.R;
import com.jaeger.library.StatusBarUtil;

public class PuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        StatusBarUtil.setLightMode(this);//状态栏字体暗色设置
    }
}
