package com.tsengvn.sunburstchartdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/27/15
 */
public class Slide {
    private int mColor;
    private double mValue;
    private float mStartAngle;
    private float mSweepAngle;
    private List<Slide> mChilds;

    public Slide(int mColor, double mValue) {
        this.mColor = mColor;
        this.mValue = mValue;
        this.mChilds = new ArrayList<>();
    }

    public void addChild(Slide slide) {
        this.mChilds.add(slide);
    }

    public List<Slide> getChilds() {
        return mChilds;
    }

    public double getValue() {
        return mValue;
    }

    public int getColor() {
        return mColor;
    }

    public void setAngle(float startAngle, float sweepAngle) {
        mStartAngle = startAngle;
        mSweepAngle = sweepAngle;
    }

    public float getStartAngle() {
        return mStartAngle;
    }

    public float getSweepAngle() {
        return mSweepAngle;
    }

    @Override
    public String toString() {
        return "Slide{" +
                "mColor=" + mColor +
                ", mValue=" + mValue +
                ", mStartAngle=" + mStartAngle +
                ", mSweepAngle=" + mSweepAngle +
                ", mChilds=" + mChilds +
                '}';
    }
}
