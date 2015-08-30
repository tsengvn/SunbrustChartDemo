package com.tsengvn.sunburstchartdemo.chart;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/30/15
 */
public interface IAnimationRenderer {
    void onAnimated(float fraction, WrappedSlide sourceSlide, WrappedSlide animatedSlide);
}
