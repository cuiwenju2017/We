package com.cwj.love_lhh.module.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.game.gobang.BaseActivity;
import com.cwj.love_lhh.game.gobang.DialogUtil;
import com.cwj.love_lhh.game.gobang.PannelView;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 五子棋
 */
public class GobangActivity extends BaseActivity implements PannelView.OnGameOverListener,
        PannelView.OnTurnChangedListener {

    private static final String IS_CUR_WHITE_TURN = "is_cur_white_turn";

    @BindView(R.id.v_pannel)
    PannelView mPannelView;
    @BindView(R.id.iv_which_turn)
    ImageView mWhichTurnImage;
    @BindView(R.id.tv_regret)
    TextView mRegretText;
    @BindView(R.id.tv_new_game)
    TextView tvNewGame;
    @BindView(R.id.cl_view)
    CoordinatorLayout clView;

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private AnimationSet mAnimSet;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_gobang;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        StatusBarUtil.setTranslucentForImageView(this, 0, clView);//沉浸状态栏
        mPannelView.registerGameOverListner(this);
        mPannelView.registerTurnChangedListener(this);
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        mWhichTurnImage.setImageBitmap(mWhitePiece);
        initAnim();
    }

    private void initAnim() {
        mAnimSet = new AnimationSet(true);
        AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1.0f);
        ScaleAnimation scaleAnim = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, 0.5f, 0.5f);
        mAnimSet.addAnimation(alphaAnim);
        mAnimSet.addAnimation(scaleAnim);
        mAnimSet.setDuration(500);
        mAnimSet.setRepeatMode(Animation.RESTART);
        mAnimSet.setRepeatCount(Animation.INFINITE);
        mWhichTurnImage.startAnimation(mAnimSet);
    }

    @OnClick({R.id.tv_new_game, R.id.tv_regret})
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.tv_new_game:
                if (mPannelView != null) {
                    mPannelView.restart();
                }
                break;
            case R.id.tv_regret:
                if (mPannelView != null) {
                    mPannelView.regretLastStep();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {//Activity销毁时保存的当前轮到谁下子
            boolean isCurWhiteTurn = savedInstanceState.getBoolean(IS_CUR_WHITE_TURN, true);
            mWhichTurnImage.setImageBitmap(isCurWhiteTurn ? mWhitePiece : mBlackPiece);
        }
    }

    /**
     * Activity销毁时存储当前轮到谁下子
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_CUR_WHITE_TURN, mPannelView.getIsCurWhiteTurn());
    }

    @Override
    public void onGameOver(boolean isWhiteWin) {
        DialogUtil.createDialog(mDecorView, isWhiteWin ? getString(R.string.white_win) : getString(R.string.black_win),
                "", getString(R.string.restart), null, new Runnable() {
                    @Override
                    public void run() {
                        if (mPannelView != null) {
                            mPannelView.restart();
                        }
                    }
                });
    }

    @Override
    public void onTurnChangeListener(boolean isWhiteTurn) {
        mWhichTurnImage.setImageBitmap(isWhiteTurn ? mWhitePiece : mBlackPiece);
        mWhichTurnImage.startAnimation(mAnimSet);
    }
}
