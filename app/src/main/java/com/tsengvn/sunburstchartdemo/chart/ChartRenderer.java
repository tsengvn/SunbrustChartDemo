package com.tsengvn.sunburstchartdemo.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/28/15
 */
public class ChartRenderer {
    private static final String TAG = "ChartRenderer";

    private Paint mFilledPaint;
    private Paint mStrokePaint;

    private IValueRenderer mValueRenderer;
    private List<WrappedSlide> mWrappedSlides = null;

    private PointF mCenterPoint = null;
    private int mViewWidth;
    private int mViewHeight;
    private int mRadius;
    private WrappedSlide mSelectedSlide = null;
    private Context mContext;

    public ChartRenderer(Context context) {
        mContext = context;

        mFilledPaint = new Paint();
        mFilledPaint.setAntiAlias(true);
        mFilledPaint.setStyle(Paint.Style.FILL);
        mFilledPaint.setDither(true);

        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(Color.WHITE);
        mStrokePaint.setStrokeWidth(5);
        mStrokePaint.setDither(true);

        mValueRenderer = new SimpleValueRenderer(mContext);
        mWrappedSlides = new ArrayList<>();
    }

    public void setupView(int viewWidth, int viewHeight, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        mCenterPoint = new PointF(viewWidth/2, viewHeight/2);
        mViewWidth = viewWidth - paddingLeft - paddingRight;
        mViewHeight = viewHeight - paddingBottom - paddingTop;
        calculateRadius();
    }

    public void setupData(List<WrappedSlide> wrappedSlides) {
        mWrappedSlides.clear();
        mWrappedSlides.addAll(wrappedSlides);
        calculateRadius();
    }

    public void setValueRenderer(IValueRenderer valueRenderer) {
        mValueRenderer = valueRenderer;
    }

    public synchronized boolean handleClick(float x, float y){
        int distance = (int) Math.abs(Math.sqrt(Math.pow((x-mCenterPoint.x), 2) + Math.pow((y - mCenterPoint.y), 2)));
        float angle = ArcUtils.angleFromPoint(mCenterPoint, x, y);
        Log.v(TAG, "handleClick:" + "distance " + distance + " angle " + angle);

        mSelectedSlide = findClickedSlide(distance, angle);;
        return mSelectedSlide != null;
    }

    public void render(Canvas canvas) {
        if (!mWrappedSlides.isEmpty() && mCenterPoint != null) {
            for (WrappedSlide wrappedSlide : mWrappedSlides) {
                if (mSelectedSlide != null) {
                    if (mSelectedSlide == wrappedSlide) {
                        mFilledPaint.setColor(wrappedSlide.getSlide().getColor());
                    } else {
                        mFilledPaint.setColor(lighter(wrappedSlide.getSlide().getColor(), 0.8f));
                    }
                } else {
                    mFilledPaint.setColor(wrappedSlide.getSlide().getColor());
                }
                renderChart(canvas, mCenterPoint, wrappedSlide.getStartAngle(), wrappedSlide.getSweepAngle(), mRadius * wrappedSlide.getLevel(), mRadius * (wrappedSlide.getLevel() + 1), mFilledPaint, mStrokePaint);
                renderValue(canvas, mCenterPoint, wrappedSlide.getSlide(), wrappedSlide.getStartAngle(), wrappedSlide.getSweepAngle(), wrappedSlide.getLevel());
            }
        }
    }

    private WrappedSlide findClickedSlide(int distance, float angle) {
        for (WrappedSlide wrappedSlide : mWrappedSlides) {
            if (distance > mRadius*wrappedSlide.getLevel()
                    && distance < mRadius*(wrappedSlide.getLevel()+1)
                    && angle > wrappedSlide.getStartAngle()
                    && angle < (wrappedSlide.getStartAngle()+wrappedSlide.getSweepAngle())){
                return wrappedSlide;
            }
        }
        return null;
    }

    private void renderChart(Canvas canvas, PointF center, float startAngle, float sweepAngle,
                             int innerRadius, int outRadius, Paint filledPaint, Paint strokePaint) {
        //draw outer arc
        Path path = ArcUtils.createBezierArcDegrees(center, outRadius, startAngle, sweepAngle, 8, false, null);
        PointF pointF = ArcUtils.pointFromAngleDegrees(center, innerRadius, startAngle + sweepAngle);

        //line to start point of inner arc
        path.lineTo(pointF.x, pointF.y);

        //draw inner arc, anti-clockwise
        ArcUtils.createBezierArcDegrees(center, innerRadius, startAngle + sweepAngle, -sweepAngle, 8, false, path);
        pointF = ArcUtils.pointFromAngleDegrees(center, outRadius, startAngle);

        //lint to start point of outer arc
        path.lineTo(pointF.x, pointF.y);

        //draw whole arc and stroke line
        canvas.drawPath(path, filledPaint);
        canvas.drawPath(path, strokePaint);
    }

    private void renderValue(Canvas canvas, PointF center, Slide slide, float startAngle, float sweepAngle, int level) {
        //draw value
        mValueRenderer.drawValue(canvas, center, slide, startAngle, sweepAngle, level);
    }

    private void calculateRadius() {
        int maxLevel = 1;
        for (WrappedSlide wrappedSlide : mWrappedSlides) {
            if (maxLevel < wrappedSlide.getLevel()) maxLevel = wrappedSlide.getLevel();
        }
        int level = maxLevel + 1;
        mRadius = mViewWidth > mViewHeight ? mViewHeight / level/2 : mViewWidth / level/2;
    }

    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }


}
