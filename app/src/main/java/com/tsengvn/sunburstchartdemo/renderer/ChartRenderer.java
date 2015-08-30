package com.tsengvn.sunburstchartdemo.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import com.tsengvn.sunburstchartdemo.ArcUtils;
import com.tsengvn.sunburstchartdemo.Slide;
import com.tsengvn.sunburstchartdemo.SunburstChartData;

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
    private SunburstChartData mChartData = null;

    private PointF mCenterPoint = null;
    private int mViewWidth;
    private int mViewHeight;
    private int mRadius;
    private Slide mSelectedSlide = null;
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
    }

    public void setupView(int viewWidth, int viewHeight, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        mCenterPoint = new PointF(viewWidth/2, viewHeight/2);
        mViewWidth = viewWidth - paddingLeft - paddingRight;
        mViewHeight = viewHeight - paddingBottom - paddingTop;
        calculateRadius();
    }

    public void setupData(SunburstChartData data) {
        mChartData = data;
        calculateRadius();
    }

    public void setValueRenderer(IValueRenderer valueRenderer) {
        mValueRenderer = valueRenderer;
    }

    public synchronized boolean handleClick(float x, float y){
        int distance = (int) Math.abs(Math.sqrt(Math.pow((x-mCenterPoint.x), 2) + Math.pow((y - mCenterPoint.y), 2)));
        float angle = ArcUtils.angleFromPoint(mCenterPoint, x, y);
        Log.v(TAG, "handleClick:" + "distance " + distance + " angle " + angle);

        mSelectedSlide = null;
        findClickedSlide(mChartData.getSlides(), 1, distance, angle);

        return mSelectedSlide != null;
    }

    public void render(Canvas canvas) {
        if (mChartData != null && mCenterPoint != null) {
            render(canvas, mChartData.getSlides(), 1);
        }
    }

    private void findClickedSlide(List<Slide> slides, int level, int distance, float angle) {
        for (Slide slide : slides) {
            if (distance > mRadius*level
                    && distance < mRadius*(level+1)
                    && angle > slide.getStartAngle()
                    && angle < (slide.getStartAngle()+slide.getSweepAngle())){
                mSelectedSlide = slide;
            }

            if (mSelectedSlide != null) {
                return;
            } else if (slide.getChilds().size() > 0) {
                findClickedSlide(slide.getChilds(), level+1, distance, angle);
            }
        }
    }

    private void render(Canvas canvas, List<Slide> slides, int level) {
        for (Slide slide : slides) {
            if (mSelectedSlide != null) {
                if (mSelectedSlide == slide) {
                    mFilledPaint.setColor(slide.getColor());
                } else {
                    mFilledPaint.setColor(lighter(slide.getColor(), 0.8f));
                }
            } else {
                mFilledPaint.setColor(slide.getColor());
            }
            renderChart(canvas, mCenterPoint, slide.getStartAngle(), slide.getSweepAngle(), mRadius * level, mRadius * (level + 1), mFilledPaint, mStrokePaint);
            renderValue(canvas, mCenterPoint, slide, mRadius*level+mRadius/2);

            if (slide.getChilds().size() > 0) {
                render(canvas, slide.getChilds(), level + 1);
            }
        }
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

    private void renderValue(Canvas canvas, PointF center, Slide slide, int level) {
        //draw value
        mValueRenderer.drawValue(canvas, center, slide, level);
    }

    private void calculateRadius() {
        int level = mChartData.getLevel() + 1;
        mRadius = mViewWidth > mViewHeight ? mViewHeight / level/2 : mViewWidth / level/2;
    }

    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }


}
