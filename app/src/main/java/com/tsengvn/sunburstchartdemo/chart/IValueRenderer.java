package com.tsengvn.sunburstchartdemo.chart;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/29/15
 */
public interface IValueRenderer {
    void drawValue(Canvas canvas, PointF center, Slide slide, float startAngle, float sweepAngle, float radius);
}
