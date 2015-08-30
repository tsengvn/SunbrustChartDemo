package com.tsengvn.sunburstchartdemo.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

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
    private boolean mIsShown = false;
    private int mAnimationDuration = 400;

    private IAnimationRenderer mAnimationRenderer = new SweepAnimationRenderer();

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
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public void setChartData(SunburstChartData data) {
        calculate(data);
        mChartRenderer.setupData(mWrappedSlides);

        if (mIsShown) {
            invalidate();
        }
    }

    public void setValueRenderer(IValueRenderer valueRenderer) {
        mChartRenderer.setValueRenderer(valueRenderer);
        if (mIsShown) {
            invalidate();
        }
    }

    public void setHighlightParentOnSelected(boolean highlightParentOnSelected) {
        mChartRenderer.setHighlightParentOnSelected(highlightParentOnSelected);
    }

    public void show(final boolean withAnimation) {
        mIsShown = true;

        if (withAnimation) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setDuration(mAnimationDuration);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    List<WrappedSlide> animatedWrappedSlides = new ArrayList<>();

                    for (WrappedSlide wrappedSlide : mWrappedSlides) {
                        WrappedSlide animatedWrappedSlide = new WrappedSlide(wrappedSlide.getSlide());
                        animatedWrappedSlide.setSweepAngle(wrappedSlide.getSweepAngle());
                        animatedWrappedSlide.setStartAngle(wrappedSlide.getStartAngle());
                        animatedWrappedSlide.setLevel(wrappedSlide.getLevel());

                        mAnimationRenderer.onAnimated(animation.getAnimatedFraction(), wrappedSlide, animatedWrappedSlide);
                        animatedWrappedSlides.add(animatedWrappedSlide);
                    }

                    mChartRenderer.setupData(animatedWrappedSlides);
                    ViewCompat.postInvalidateOnAnimation(SunburstChart.this);
                }
            });
            valueAnimator.start();
        } else {
            mChartRenderer.setupData(mWrappedSlides);
            invalidate();
        }

    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mChartRenderer.setupView(getWidth(), getHeight(), getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsShown) {
            mChartRenderer.render(canvas);
        }
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


}
