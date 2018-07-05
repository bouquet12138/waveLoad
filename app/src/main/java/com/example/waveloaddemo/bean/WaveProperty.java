package com.example.waveloaddemo.bean;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.example.waveloaddemo.R;
import com.example.waveloaddemo.util.ColorUtil;
import com.example.waveloaddemo.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class WaveProperty {


    private int mFirstWaveColor;
    private int mSecondWaveColor;
    private int mCirclePaintColor;
    private float mWaveWidth;
    private float mWaveHeight;
    private boolean mChangeHeight;
    private float mViewSize;
    private int mAnimPeriod;

    private int[] mFirstColorList;
    private int[] mSecondColorList;


    public void getAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveProgressView);

        mFirstWaveColor = typedArray.getColor(R.styleable.WaveProgressView_firstWaveColor, 0xaa09baf0);//第一个水波颜色
        mSecondWaveColor = typedArray.getColor(R.styleable.WaveProgressView_secondWaveColor, 0x7709baf0);//第二个水波颜色
        mCirclePaintColor = typedArray.getColor(R.styleable.WaveProgressView_circlePaintColor, Color.GRAY);
        mWaveWidth = typedArray.getDimension(R.styleable.WaveProgressView_waveWidth, DensityUtil.dipToPx(context, 80));
        mWaveHeight = typedArray.getDimension(R.styleable.
                WaveProgressView_waveHeight, DensityUtil.dipToPx(context, 10));//水波高默认为10dp
        mChangeHeight = typedArray.
                getBoolean(R.styleable.WaveProgressView_changeHeight, false);//默认水波是不会改变宽高的
        mViewSize = typedArray.getDimension(R.styleable.WaveProgressView_viewSize, DensityUtil.dipToPx(context, 200));//viewSize默认是200dp
        mAnimPeriod = typedArray.getInt(R.styleable.WaveProgressView_animPeriod, 3000);//动画周期3000秒

        mFirstColorList = new int[1];
        mSecondColorList = new int[1];

        mFirstColorList[0] = mFirstWaveColor;//设置第一个颜色列表
        mSecondColorList[0] = mSecondWaveColor;//设置第二个颜色列表

        typedArray.recycle();
    }


    public int getCirclePaintColor() {
        return mCirclePaintColor;
    }

    public void setCirclePaintColor(int circlePaintColor) {
        mCirclePaintColor = circlePaintColor;
    }

    public float getWaveWidth() {
        return mWaveWidth;
    }

    public void setWaveWidth(float waveWidth) {
        mWaveWidth = waveWidth;
    }

    public float getWaveHeight() {
        return mWaveHeight;
    }

    public void setWaveHeight(float waveHeight) {
        mWaveHeight = waveHeight;
    }

    public boolean isChangeHeight() {
        return mChangeHeight;
    }

    public void setChangeHeight(boolean changeHeight) {
        mChangeHeight = changeHeight;
    }

    public float getViewSize() {
        return mViewSize;
    }

    public void setViewSize(float viewSize) {
        mViewSize = viewSize;
    }

    public int getAnimPeriod() {
        return mAnimPeriod;
    }

    public void setAnimPeriod(int animPeriod) {
        mAnimPeriod = animPeriod;
    }


    public void setFirstColorList(int... firstColorList) {
        mFirstColorList = firstColorList;
    }


    public void setSecondColorList(int... secondColorList) {
        mSecondColorList = secondColorList;
    }

    /**
     * 得到第一个水波颜色
     *
     * @return
     */
    public int getFirstWaveColor() {
        return mFirstWaveColor;
    }

    /**
     * 得到第二个水波颜色
     *
     * @return
     */
    public int getSecondWaveColor() {
        return mSecondWaveColor;
    }

    /**
     * 得到当前第一个颜色
     *
     * @return
     */
    public int getCurrentFirstColor(float percent) {

        if (mFirstColorList.length == 0)
            return mFirstWaveColor;
        else if (mFirstColorList.length == 1)
            return mFirstColorList[0];//返回第一个
        else {

            return ColorUtil.getCurrentColor(percent, mFirstColorList);
        }

    }

    /**
     * 得到当前第二个颜色
     *
     * @return
     */
    public int getCurrentSecondColor(float percent) {

        if (mSecondColorList.length == 0)
            return mSecondWaveColor;
        else if (mSecondColorList.length == 1)
            return mSecondColorList[0];//返回第一个
        else {
            return ColorUtil.getCurrentColor(percent, mSecondColorList);
        }

    }


}
