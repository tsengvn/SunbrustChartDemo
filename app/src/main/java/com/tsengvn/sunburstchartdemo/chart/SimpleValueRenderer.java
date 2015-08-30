package com.tsengvn.sunburstchartdemo.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

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
        mPaint.setTextSize(ChartUtils.convertDp2Pixel(context, 12f));
    }

    @Override
    public void drawValue(Canvas canvas, PointF center, Slide slide, float startAngle, float sweepAngle, float radius) {
        String value = String.valueOf(slide.getValue());
        PointF pointF = ArcUtils.pointFromAngleDegrees(center, radius, startAngle + sweepAngle / 2);
        mPaint.getTextBounds(value, 0, value.length(), mRect);

        canvas.drawText(String.valueOf(value), pointF.x - mRect.width()/2, pointF.y + mRect.height()/2, mPaint);
    }

}
