package com.tsengvn.sunbrustchartdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/27/15
 */
public class SunburstChartData {
    private ArrayList<Slide> mSlides = new ArrayList<>();
    private double mSumValue = 0;

    public ArrayList<Slide> getSlides() {
        return mSlides;
    }

    public void addSlide(Slide slide) {
        mSumValue += slide.getValue();
        mSlides.add(slide);
    }

    public int getLevel() {
        return 3;
    }

    public void calculate() {
        calculateAngle(mSlides, mSumValue, 0, 360);
    }

    private void calculateAngle(List<Slide> slides, double sumValue, float startAngle, float totalSweepAngle) {
        double factor = totalSweepAngle/sumValue;
        for (Slide slide : slides) {
            float sweepAngle = (float) (slide.getValue() * factor);
            slide.setAngle(startAngle, sweepAngle);

            if (!slide.getChilds().isEmpty()) {
                calculateAngle(slide.getChilds(), slide.getValue(), startAngle, sweepAngle);
            }

            startAngle += sweepAngle;
        }
    }
}
