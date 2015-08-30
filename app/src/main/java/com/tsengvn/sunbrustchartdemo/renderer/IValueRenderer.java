package com.tsengvn.sunbrustchartdemo.renderer;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.tsengvn.sunbrustchartdemo.Slide;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/29/15
 */
public interface IValueRenderer {
    void drawValue(Canvas canvas, PointF center, Slide slide, float radius);
}
