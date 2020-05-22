package com.cwj.love_lhh.module.news;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.base.BaseActivity;
import com.cwj.love_lhh.base.BasePresenter;
import com.cwj.love_lhh.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 视频播放页
 */
public class VideoPlayerActivity extends BaseActivity {

    @BindView(R.id.detail_player)
    StandardGSYVideoPlayer gsyVideoPlayer;
    @BindView(R.id.cl_view)
    CoordinatorLayout clView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, clView);//沉浸状态栏
    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            gsyVideoPlayer.setUpLazy(url, true, null, null, title);
            //增加title
            gsyVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
            //设置返回键
            gsyVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
            //设置全屏按键功能
            gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gsyVideoPlayer.startWindowFullscreen(VideoPlayerActivity.this, false, true);
                }
            });

            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
            gsyVideoPlayer.setAutoFullWithSize(true);
            //音频焦点冲突时是否释放
            gsyVideoPlayer.setReleaseWhenLossAudio(false);
            //全屏动画
            gsyVideoPlayer.setShowFullAnimation(true);
            //小屏时不触摸滑动
            gsyVideoPlayer.setIsTouchWiget(false);
            gsyVideoPlayer.startPlayLogic();
        } else {
            ToastUtil.showTextToast(this, "该视频源已失效，请观看其他视频");
        }
    }
}
