package com.example.thatnight.view.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.example.thatnight.view.R;

import static android.content.ContentValues.TAG;

/**
 * Created by thatnight on 2017.10.13.
 */

public class WaveView extends View {

    private int mWaveDuration;  //波持续时间
    private float mWaveHeight;  //波高度
    private float mWaveWidth;   //波宽度
    private int mWaveColor;     //波颜色
    private float mOffset;      //偏移量
    private float mWidth, mHeight;

    private Paint mPaint;
    private Path mPath;

    private boolean isRunning = false;
    private boolean isInit = false;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WaveView, defStyleAttr, 0);
        mWaveDuration = a.getInt(R.styleable.WaveView_wave_duration, 1000);
        mWaveHeight = a.getFloat(R.styleable.WaveView_wave_height, 50);
        mWaveWidth = a.getFloat(R.styleable.WaveView_wave_width, -1);
        mWaveColor = a.getColor(R.styleable.WaveView_wave_color, Color.RED);

        a.recycle();
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mWaveColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (!isInit) {
            mWidth = w;
            mHeight = h;
            if (mWaveWidth == -1) {
                mWaveWidth = w;
            }
            isInit = true;
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isRunning || !isInit) {
            return;
        }
        mPath.reset();
        mPath.moveTo(-mWaveWidth + mOffset, mHeight - mWaveHeight);
        for (float i = -mWaveWidth; i < mWidth + mWaveWidth; i += mWaveWidth) {
            mPath.rQuadTo(mWaveWidth / 4, -mWaveHeight, mWaveWidth / 2, 0);
            mPath.rQuadTo(mWaveWidth / 4, mWaveHeight, mWaveWidth / 2, 0);
        }
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                startAnimation();
                isRunning = true;
            }
        }
    };

    public void start() {
        mHandler.sendEmptyMessage(1);
    }

    /*
        wave start flow
     */
    public void startAnimation() {
        final ValueAnimator animator = ValueAnimator.ofFloat(0, mWaveWidth);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(mWaveDuration);
        animator.setRepeatCount(Animation.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mOffset = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    /*

     */
    public void changeHeightAnim(final int percent) {
        ValueAnimator animator = ValueAnimator.ofFloat(mWaveHeight, mHeight * percent / 100);
        Log.d(TAG, "changeHeightAnim: " + mHeight * percent / 100);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(mWaveDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mWaveHeight = (float) animation.getAnimatedValue();
//                postInvalidate();
                Log.d(TAG, "onAnimationUpdate: " + mWaveHeight);
            }
        });
        animator.start();
    }

    public boolean isInit() {
        return isInit;
    }

}
