package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.ref.WeakReference;

/**
 * Created by guillaume on 30/05/16.
 */
public class LineFilledChartRenderer extends LineChartRenderer {
    public LineFilledChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        mLineData = chart.getLineFilledData();
    }

    @Override
    public void drawData(Canvas c) {
        mLineData =mChart.getLineFilledData();

        int width = (int) mViewPortHandler.getChartWidth();
        int height = (int) mViewPortHandler.getChartHeight();

        if (mDrawBitmap == null
                || (mDrawBitmap.get().getWidth() != width)
                || (mDrawBitmap.get().getHeight() != height)) {

            if (width > 0 && height > 0) {

                mDrawBitmap = new WeakReference<Bitmap>(Bitmap.createBitmap(width, height, mBitmapConfig));
                mBitmapCanvas = new Canvas(mDrawBitmap.get());
            } else
                return;
        }

        mDrawBitmap.get().eraseColor(Color.TRANSPARENT);


        for (ILineDataSet set : mLineData.getDataSets()) {

            if (set.isVisible() && set.getEntryCount() > 0)
                drawDataSet(c, set);
        }


        c.drawBitmap(mDrawBitmap.get(), 0, 0, mRenderPaint);
    }
}
