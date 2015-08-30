package com.tsengvn.sunburstchartdemo.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.tsengvn.sunburstchartdemo.ArcUtils;
import com.tsengvn.sunburstchartdemo.ChartUtils;
import com.tsengvn.sunburstchartdemo.Slide;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/29/15
 */
public class SimpleValueRenderer implements IValueRenderer{
    private Paint mPaint;
    private Rect mRect = new Rect();

    public SimpleValueRenderer(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(ChartUtils.convertDp2Pixel(context, 14f));
    }

    @Override
    public void drawValue(Canvas canvas, PointF center, Slide slide, float radius) {
        String value = String.valueOf(slide.getValue());
        PointF pointF = ArcUtils.pointFromAngleDegrees(center, radius, slide.getStartAngle() + slide.getSweepAngle() / 2);
        mPaint.getTextBounds(value, 0, value.length(), mRect);

        canvas.drawText(String.valueOf(value), pointF.x - mRect.width()/2, pointF.y + mRect.height()/2, mPaint);
    }
}
