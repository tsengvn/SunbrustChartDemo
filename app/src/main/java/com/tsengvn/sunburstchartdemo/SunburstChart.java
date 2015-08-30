package com.tsengvn.sunburstchartdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.tsengvn.sunburstchartdemo.renderer.ChartRenderer;
import com.tsengvn.sunburstchartdemo.renderer.IValueRenderer;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/27/15
 */
public class SunburstChart extends View {
    private ChartRenderer mChartRenderer;
    private GestureDetector mGestureDetector;
    public SunburstChart(Context context) {
        super(context);
        init();
    }

    public SunburstChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        mChartRenderer = new ChartRenderer(getContext());
        mGestureDetector = new GestureDetector(mGestureListener);
    }

    public void setChartData(SunburstChartData data) {
        mChartRenderer.setupData(data);
        invalidate();
    }

    public void setValueRenderer(IValueRenderer valueRenderer) {
        mChartRenderer.setValueRenderer(valueRenderer);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mChartRenderer.setupView(width, height, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mChartRenderer.render(canvas);
    }

    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            boolean valid = mChartRenderer.handleClick(e.getX(), e.getY());
            invalidate();
            return valid;
        }
    };

}
