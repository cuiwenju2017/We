package com.cwj.we.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

public class BeatingHeartView extends View {


    /**
     * 跳动幅度
     **/
    private int beating = 70;

    private int mMeasureWidth;
    private int mWidthMode;
    private int mMeasureHeight;
    private int mHeightMode;
    /**
     * 偏移量
     **/
    private int offset, cfOffset;
    /**
     * 心形状
     **/
    private Paint heartPaint;
    /**
     * 心电图
     **/
    private Paint cgPaint, cgPointPaint;
    private PathMeasure pathMeasure;

    private volatile int pathLength;


    public BeatingHeartView(Context context) {
        super(context);
        init();
    }

    public BeatingHeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        heartPaint = new Paint();
        heartPaint.setAntiAlias(true);
        heartPaint.setStrokeWidth(6);
        heartPaint.setColor(Color.RED);


        cgPaint = new Paint();
        cgPaint.setAntiAlias(true);
        cgPaint.setColor(Color.GRAY);
        cgPaint.setStrokeWidth(7);
        cgPaint.setStyle(Paint.Style.STROKE);

        cgPointPaint = new Paint();
        cgPointPaint.setAntiAlias(true);
        cgPointPaint.setStyle(Paint.Style.STROKE);
        cgPointPaint.setStrokeWidth(7);
        cgPointPaint.setColor(Color.WHITE);
        cgPointPaint.setStrokeCap(Paint.Cap.ROUND);
        cgPointPaint.setShadowLayer(10, 0, 0, Color.RED);


        pathMeasure = new PathMeasure();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //进行测量

        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mWidthMode == MeasureSpec.AT_MOST && mHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 300);
        } else if (mWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, mMeasureHeight);
        } else if (mHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mMeasureWidth, 300);
        } else {
            setMeasuredDimension(mMeasureWidth, mMeasureHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制背景
        canvas.drawColor(Color.TRANSPARENT);

        drawHeart(canvas);
        drawCardiogram(canvas);
    }


    /**
     * 画心脏
     *
     * @param canvas
     */
    private void drawHeart(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // 绘制心形
        Path path = new Path();
        path.moveTo(width / 2, height / 4 + offset / 4);
        path.cubicTo((width * 6) / 7, height / 9,
                (width * 12) / 13 - offset, (height * 2) / 5,
                width / 2, (height * 7) / 14 - offset / 2);
        canvas.drawPath(path, heartPaint);

        Path path2 = new Path();
        path2.moveTo(width / 2, height / 4 + offset / 4);
        path2.cubicTo(width / 7, height / 9,
                width / 13 + offset, (height * 2) / 5,
                width / 2, (height * 7) / 14 - offset / 2);
        canvas.drawPath(path2, heartPaint);
    }


    /**
     * 画心电图
     *
     * @param canvas
     */
    private void drawCardiogram(Canvas canvas) {

        int baseWith = getWidth() / 10;
        int moveHeight = (getHeight() / 4 + (getHeight() * 7) / 12) / 2;

//      心电图轮廓
        Path path = new Path();
        path.moveTo(0, moveHeight);
        path.rLineTo((float) (baseWith * 2.5), 0);
        path.rLineTo(baseWith, -baseWith);
        path.rLineTo(baseWith, baseWith * 3);
        path.rLineTo(baseWith, -baseWith * 6);
        path.rLineTo(baseWith, baseWith * 5);
        path.rLineTo(baseWith, -baseWith);
        path.lineTo(getWidth(), moveHeight);

        //背景线
//        canvas.drawPath(path,cgPaint);

        //分割路径
        Path cutPath = new Path();
        pathMeasure.setPath(path, false);
        pathLength = (int) pathMeasure.getLength();
        pathMeasure.getSegment(-pathLength + cfOffset, cfOffset, cutPath, true);


        canvas.drawPath(cutPath, cgPointPaint);
    }


    /**
     * 打开动画
     */
    public void startAnimition() {
        final ValueAnimator anima = ValueAnimator.ofInt(0, beating, 0);
        anima.setInterpolator(new AccelerateDecelerateInterpolator());
        anima.setDuration(1300);
        anima.setRepeatCount(ValueAnimator.INFINITE);
        anima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int whtie = (int) anima.getAnimatedValue();
                offset = whtie;
                postInvalidate();
            }
        });
        anima.start();

        final ValueAnimator cgAnima = ValueAnimator.ofInt(0, pathLength * 2);
        cgAnima.setInterpolator(new AccelerateDecelerateInterpolator());
        cgAnima.setDuration(1300);
        cgAnima.setRepeatCount(ValueAnimator.INFINITE);
        cgAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cfOffset = (int) cgAnima.getAnimatedValue();
                postInvalidate();
            }
        });
        cgAnima.start();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("test", pathLength + "");
                startAnimition();
            }
        }, 500);
    }

}
