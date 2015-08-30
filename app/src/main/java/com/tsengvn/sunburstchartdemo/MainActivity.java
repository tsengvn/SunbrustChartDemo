package com.tsengvn.sunburstchartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.tsengvn.sunburstchartdemo.chart.Slide;
import com.tsengvn.sunburstchartdemo.chart.SunburstChart;
import com.tsengvn.sunburstchartdemo.chart.SunburstChartData;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/27/15
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Slide facebookSlide = new Slide(Color.BLUE, 15);
        Slide fbClick = new Slide(Color.YELLOW, 15);
        Slide fbClickMoney = new Slide(Color.CYAN, 3);
        Slide fbClickHour = new Slide(Color.CYAN, 5);
        Slide fbClickGood = new Slide(Color.CYAN, 7);
        fbClick.addChild(fbClickMoney);
        fbClick.addChild(fbClickHour);
        fbClick.addChild(fbClickGood);
        facebookSlide.addChild(fbClick);

        Slide twitterSlide = new Slide(Color.GREEN, 7);
        Slide linkedInSlide = new Slide(Color.RED, 4);

        SunburstChartData sunburstChartData = new SunburstChartData();
        sunburstChartData.addSlide(facebookSlide);
        sunburstChartData.addSlide(twitterSlide);
        sunburstChartData.addSlide(linkedInSlide);
        sunburstChartData.setUseValueForDrawing(true);

        SunburstChart chart = new SunburstChart(this);
        chart.setPadding(100, 100, 100, 100);
        chart.setChartData(sunburstChartData);
        setContentView(chart);
    }
}
