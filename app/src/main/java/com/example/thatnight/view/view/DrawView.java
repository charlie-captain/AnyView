package com.example.thatnight.view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.thatnight.view.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DrawView extends View {

    private int mNormalColor;       //默认颜色
    private int mNormalThickness;   //默认笔粗
    private int mNormalBackground;  //默认背景
    private int mChangeThickness;   //改变的笔粗
    private int mChangeColor;       //改变颜色

    private Paint mPaint;
    private Path mPath;
    private float mPreX, mPreY;
    private List<DrawPath> mPathList;
    private List<DrawPath> mSaveList;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawView);
        mNormalColor = a.getColor(R.styleable.DrawView_normal_color, Color.BLACK);
        mNormalThickness = a.getInt(R.styleable.DrawView_normal_thickness, 5);
        mNormalBackground = a.getColor(R.styleable.DrawView_normal_background, Color.WHITE);
        a.recycle();
        init();
    }

    private void init() {
        mPathList = new ArrayList<>();
        mSaveList = new ArrayList<>();
        mChangeThickness = mNormalThickness;
        mChangeColor = mNormalColor;
        mPaint = new Paint();
        mPaint.setColor(mNormalColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mNormalThickness);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setBackgroundColor(mNormalBackground);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPathList != null && mPathList.size() > 0) {
            for (DrawPath path : mPathList) {
                if (path.mPath != null) {
                    canvas.drawPath(path.mPath, path.mPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                mPath.moveTo(event.getX(), event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                DrawPath drawPath = new DrawPath(mPath, mPaint);
                mPathList.add(drawPath);
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX()) / 2;
                float endY = (mPreY + event.getY()) / 2;
                mPath.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void reset() {
        init();
        invalidate();
        setBackgroundColor(mNormalBackground);
    }


    public void changePenColor(int color) {
        mChangeColor = color;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mChangeThickness);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void revert() {
        if (mPathList != null && mPathList.size() >= 1) {
            mSaveList.add(mPathList.get(mPathList.size() - 1));
            mPathList.remove(mPathList.size() - 1);
            invalidate();
        }
    }

    public void cancel() {
        if (mSaveList != null && mSaveList.size() >= 1) {
            mPathList.add(mSaveList.get(mSaveList.size() - 1));
            mSaveList.remove(mSaveList.size() - 1);
            invalidate();
        }
    }

    public void add() {
        mChangeThickness += 4;
        changePenColor(mChangeColor);
        invalidate();
    }

    public void minus() {
        mChangeThickness -= 4;
        changePenColor(mChangeColor);
        invalidate();
    }

    class DrawPath {
        Path mPath;
        Paint mPaint;

        public DrawPath(Path path, Paint paint) {
            mPath = path;
            mPaint = paint;
        }

        public DrawPath() {
        }
    }
}
