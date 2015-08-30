package com.tsengvn.sunbrustchartdemo;

import android.content.Context;
import android.util.TypedValue;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 8/30/15
 */
public class ChartUtils {

    public static float convertDp2Pixel(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
