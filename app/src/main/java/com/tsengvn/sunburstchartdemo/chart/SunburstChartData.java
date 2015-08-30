package com.tsengvn.sunburstchartdemo.chart;

import java.util.ArrayList;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/27/15
 */
public class SunburstChartData {
    private ArrayList<Slide> mSlides = new ArrayList<>();
    private boolean mIsUseValueForDrawing = false;

    public boolean isUseValueForDrawing() {
        return mIsUseValueForDrawing;
    }

    public void setUseValueForDrawing(boolean value) {
        mIsUseValueForDrawing = value;
    }

    public ArrayList<Slide> getSlides() {
        return mSlides;
    }

    public void addSlide(Slide slide) {
        mSlides.add(slide);
    }

    public void addSlide(Slide slide, Slide parent) {
        if (!parent.getChilds().contains(slide)) {
            parent.getChilds().add(slide);
        }
    }

}
