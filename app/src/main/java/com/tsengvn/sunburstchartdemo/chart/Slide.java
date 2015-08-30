package com.tsengvn.sunburstchartdemo.chart;

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
    private float mWeight;
    private List<Slide> mChilds;

    public Slide(int mColor, double mValue) {
        this.mColor = mColor;
        this.mValue = mValue;
        this.mChilds = new ArrayList<>();
        this.mWeight = 1;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float weight) {
        mWeight = weight;
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

    @Override
    public String toString() {
        return "Slide{" +
                "mColor=" + mColor +
                ", mValue=" + mValue +
                ", mChilds=" + mChilds +
                '}';
    }
}
