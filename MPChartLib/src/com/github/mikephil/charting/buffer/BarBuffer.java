
package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class BarBuffer extends AbstractBuffer<IBarDataSet> {

    protected float mBarSpace = 0f;
    protected float mGroupSpace = 0f;
    protected int mDataSetIndex = 0;
    protected int mDataSetCount = 1;
    protected boolean mContainsStacks = false;
    protected boolean mInverted = false;
    protected boolean mAllowSuperposition = false;

    public BarBuffer(int size, float groupspace, int dataSetCount, boolean containsStacks, boolean allowSuperposition) {
        super(size);
        this.mGroupSpace = groupspace;
        this.mDataSetCount = dataSetCount;
        this.mContainsStacks = containsStacks;
        this.mAllowSuperposition = allowSuperposition;
    }

    public void setBarSpace(float barspace) {
        this.mBarSpace = barspace;
    }

    public void setDataSet(int index) {
        this.mDataSetIndex = index;
    }
    
    public void setInverted(boolean inverted) {
        this.mInverted = inverted;
    }

    protected void addBar(float left, float top, float right, float bottom) {

        buffer[index++] = left;
        buffer[index++] = top;
        buffer[index++] = right;
        buffer[index++] = bottom;
    }

    @Override
    public void feed(IBarDataSet data) {

        float size = data.getEntryCount() * phaseX;

        int dataSetOffset = mAllowSuperposition ? 0 : (mDataSetCount - 1);
        float barSpaceHalf = mBarSpace / 2f;
        float groupSpaceHalf = mGroupSpace / 2f;
        float barWidth = 0.5f;

        for (int i = 0; i < size; i++) {

            BarEntry e = data.getEntryForIndex(i);

            float x;
            if(mAllowSuperposition) {
                x = e.getXIndex();
            } else {
                // calculate the x-position, depending on datasetcount
                x = e.getXIndex() + e.getXIndex() * dataSetOffset + mDataSetIndex
                        + mGroupSpace * e.getXIndex() + groupSpaceHalf;
            }

            float y = e.getVal();
            float [] vals = e.getVals();
                
            if (!mContainsStacks || vals == null) {

                float left = x - barWidth + barSpaceHalf;
                float right = x + barWidth - barSpaceHalf;
                float bottom, top;
                if (mInverted) {
                    bottom = y >= 0 ? y : 0;
                    top = y <= 0 ? y : 0;
                } else {
                    top = y >= 0 ? y : 0;
                    bottom = y <= 0 ? y : 0;
                }

                // multiply the height of the rect with the phase
                if (top > 0)
                    top *= phaseY;
                else
                    bottom *= phaseY;

                addBar(left, top, right, bottom);

            } else {

                float posY = 0f;
                float negY = -e.getNegativeSum();
                float yStart = 0f;

                // fill the stack
                for (int k = 0; k < vals.length; k++) {

                    float value = vals[k];

                    if(value >= 0f) {
                        y = posY;
                        yStart = posY + value;
                        posY = yStart;
                    } else {
                        y = negY;
                        yStart = negY + Math.abs(value);
                        negY += Math.abs(value);
                    }

                    float left = x - barWidth + barSpaceHalf;
                    float right = x + barWidth - barSpaceHalf;
                    float bottom, top;
                    if (mInverted) {
                        bottom = y >= yStart ? y : yStart;
                        top = y <= yStart ? y : yStart;
                    } else {
                        top = y >= yStart ? y : yStart;
                        bottom = y <= yStart ? y : yStart;
                    }

                    // multiply the height of the rect with the phase
                    top *= phaseY;
                    bottom *= phaseY;

                    addBar(left, top, right, bottom);
                }
            }
        }

        reset();
    }
}
