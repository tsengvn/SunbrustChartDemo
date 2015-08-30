package com.tsengvn.sunburstchartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
    private SunburstChart mSunburstChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSunburstChart = (SunburstChart) findViewById(R.id.chart);
        prepareData();
    }

    public void show(View view) {
        mSunburstChart.show(false);
    }

    public void showWithAni(View view) {
        mSunburstChart.show(true);
    }

    private void prepareData() {
        SunburstChartData data = new SunburstChartData();

        Slide facebookSlide = new Slide(Color.parseColor("#3c5a96"), 15);
        data.addSlide(facebookSlide);

        Slide fbClick = new Slide(Color.CYAN, 15);
        data.addSlide(fbClick, facebookSlide);

        Slide fbClickMoney = new Slide(Color.parseColor("#ffc627"), 3);
        Slide fbClickHour = new Slide(Color.parseColor("#ffc627"), 5);
        Slide fbClickGood = new Slide(Color.parseColor("#ffc627"), 7);
        data.addSlide(fbClickGood, fbClick);
        data.addSlide(fbClickHour, fbClick);
        data.addSlide(fbClickMoney, fbClick);

        Slide twitterSlide = new Slide(Color.parseColor("#00aced"), 7);
        data.addSlide(twitterSlide);

        Slide twClick = new Slide(Color.WHITE, 12);
        data.addSlide(twClick, twitterSlide);

        Slide twClickMoney = new Slide(Color.parseColor("#ffc627"), 1);
        data.addSlide(twClickMoney, twClick);

        Slide twClickHour = new Slide(Color.parseColor("#ffc627"), 10);
        data.addSlide(twClickHour, twClick);

        Slide twClickGood = new Slide(Color.parseColor("#ffc627"), 2);
        data.addSlide(twClickGood, twClick);


        Slide linkedInSlide = new Slide(Color.parseColor("#117cb4"), 4);
        data.addSlide(linkedInSlide);

        data.setUseValueForDrawing(true);

        mSunburstChart.setChartData(data);

    }
}
