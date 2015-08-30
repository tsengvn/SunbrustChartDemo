package com.tsengvn.sunburstchartdemo.chart;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/30/15
 */
public class SweepAnimationRenderer implements IAnimationRenderer {
    @Override
    public void onAnimated(float fraction, WrappedSlide sourceSlide, WrappedSlide animatedSlide) {
        animatedSlide.setSweepAngle(sourceSlide.getSweepAngle()*fraction);
    }
}
