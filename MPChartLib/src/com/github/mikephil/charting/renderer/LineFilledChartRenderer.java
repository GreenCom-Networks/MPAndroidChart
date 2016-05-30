package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by guillaume on 30/05/16.
 */
public class LineFilledChartRenderer extends LineChartRenderer {
    public LineFilledChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        mLineData = chart.getLineFilledData();
    }
}
