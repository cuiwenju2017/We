package com.cwj.we.module.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 七键钢琴
 */
public class QijiangangqinActivity extends BaseActivity {

    @BindView(R.id.tv_dou)
    TextView tvDou;
    @BindView(R.id.tv_rai)
    TextView tvRai;
    @BindView(R.id.tv_mi)
    TextView tvMi;
    @BindView(R.id.tv_fa)
    TextView tvFa;
    @BindView(R.id.tv_sao)
    TextView tvSao;
    @BindView(R.id.tv_la)
    TextView tvLa;
    @BindView(R.id.tv_xi)
    TextView tvXi;

    SoundPool sp;//声明SoundPool的引用
    HashMap<Integer, Integer> hm;//声明HashMap来存放声音文件
    int currStaeamId;//当前正播放的streamId


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_qijiangangqin;
    }

    @Override
    protected void initView() {
        initSoundPool();//初始化声音池的方法
    }

    private void initSoundPool() {//初始化声音池
        sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);//创建SoundPool对象
        hm = new HashMap<Integer, Integer>();//创建HashMap对象
        //加载声音文件，并且设置为1号声音放入hm中
        hm.put(1, sp.load(this, R.raw.dou, 1));
        hm.put(2, sp.load(this, R.raw.rai, 2));
        hm.put(3, sp.load(this, R.raw.mi, 3));
        hm.put(4, sp.load(this, R.raw.fa, 4));
        hm.put(5, sp.load(this, R.raw.sao, 5));
        hm.put(6, sp.load(this, R.raw.la, 6));
        hm.put(7, sp.load(this, R.raw.xi, 7));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_dou, R.id.tv_rai, R.id.tv_mi, R.id.tv_fa, R.id.tv_sao, R.id.tv_la, R.id.tv_xi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dou:
                playSound(1, 0);//播放1号声音资源，且播放一次
                break;
            case R.id.tv_rai:
                playSound(2, 0);
                break;
            case R.id.tv_mi:
                playSound(3, 0);
                break;
            case R.id.tv_fa:
                playSound(4, 0);
                break;
            case R.id.tv_sao:
                playSound(5, 0);
                break;
            case R.id.tv_la:
                playSound(6, 0);
                break;
            case R.id.tv_xi:
                playSound(7, 0);
                break;
        }
    }

    private void playSound(int sound, int loop) {//获取AudioManager引用
        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        //获取当前音量
        float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        //获取系统最大音量
        float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //计算得到播放音量
        float volume = streamVolumeCurrent / streamVolumeMax;
        //调用SoundPool的play方法来播放声音文件
        currStaeamId = sp.play(hm.get(sound), volume, volume, 1, loop, 1.0f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.stop(currStaeamId);//停止正在播放的某个声音
    }
}