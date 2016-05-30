package com.github.mikephil.charting.data;

import java.util.List;

/**
 * Created by guillaume on 30/05/16.
 */
public class LineFilledDataSet extends LineDataSet {
    public LineFilledDataSet(List<Entry> yVals, String label) {
        super(yVals, label);
        this.setDrawFilled(true);
    }
}
