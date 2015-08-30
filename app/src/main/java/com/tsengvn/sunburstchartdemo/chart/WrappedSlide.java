package com.tsengvn.sunburstchartdemo.chart;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/30/15
 */
class WrappedSlide {
    private Slide mSlide;
    private int mLevel;
    private float mStartAngle;
    private float mSweepAngle;

    public WrappedSlide(Slide slide) {
        mSlide = slide;
    }

    public WrappedSlide(Slide slide, int level) {
        mSlide = slide;
        mLevel = level;
    }

    public Slide getSlide() {
        return mSlide;
    }

    public void setSlide(Slide slide) {
        mSlide = slide;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float startAngle) {
        mStartAngle = startAngle;
    }

    public float getSweepAngle() {
        return mSweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        mSweepAngle = sweepAngle;
    }
}
