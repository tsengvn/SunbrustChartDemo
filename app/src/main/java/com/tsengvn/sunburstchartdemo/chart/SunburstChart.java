package com.tsengvn.sunburstchartdemo.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/27/15
 */
public class SunburstChart extends View {
    private ChartRenderer mChartRenderer;
    private GestureDetector mGestureDetector;
    private List<WrappedSlide> mWrappedSlides;

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
        mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        mWrappedSlides = new ArrayList<>();
    }

    public void setChartData(SunburstChartData data) {
        calculate(data);
        mChartRenderer.setupData(mWrappedSlides);
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


    private void calculate(SunburstChartData data) {
        mWrappedSlides.clear();
        prepareData(data.getSlides(), data.isUseValueForDrawing(), 0, 360, 1);
    }

    private void prepareData(List<Slide> slides, boolean isUseValueForDrawing, float startAngle, float totalSweepAngle, int level) {
        double sum = 0;
        for (Slide slide : slides) {
            if (isUseValueForDrawing) sum += slide.getValue();
            else sum += slide.getWeight();
        }

        double factor = totalSweepAngle / sum;
        for (Slide slide : slides) {
            WrappedSlide wrappedSlide = new WrappedSlide(slide);
            float sweepAngle = (float) (isUseValueForDrawing ? slide.getValue() * factor : slide.getWeight() * factor);
            wrappedSlide.setStartAngle(startAngle);
            wrappedSlide.setSweepAngle(sweepAngle);
            wrappedSlide.setLevel(level);
            mWrappedSlides.add(wrappedSlide);

            if (!slide.getChilds().isEmpty()) {
                prepareData(slide.getChilds(), isUseValueForDrawing, wrappedSlide.getStartAngle(),
                        wrappedSlide.getSweepAngle(), wrappedSlide.getLevel()+1);
            }

            startAngle += sweepAngle;
        }
    }

//    private void calculateAngle(List<Slide> slides, double sumValue, float startAngle, float totalSweepAngle) {
//        double factor = totalSweepAngle / sumValue;
//        for (Slide slide : slides) {
//            float sweepAngle = (float) (slide.getValue() * factor);
//            slide.setAngle(startAngle, sweepAngle);
//
//            if (!slide.getChilds().isEmpty()) {
//                calculateAngle(slide.getChilds(), slide.getValue(), startAngle, sweepAngle);
//            }
//
//            startAngle += sweepAngle;
//        }
//    }
}
