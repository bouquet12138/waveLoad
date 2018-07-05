package com.example.waveloaddemo.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.example.waveloaddemo.bean.WaveProperty;

import java.util.List;

public class WaveProgressView extends View {

    private static final String TAG = "WaveProgressView";

    private Paint mWavePaint;//绘制水波的画笔
    private Paint mSecondPaint;//第二条画笔
    private Paint mCirclePaint;//绘制背景圆的画笔

    private Path mWavePath;//波浪路径

    private int mWaveNum;//波浪数

    private int mViewWidth, mViewHeight;//view宽高

    private float mPercent = 0;//这个progress的百分比
    private float mWaveMoveDistance = 0;//移动的距离

    private WaveProperty mWavePro = new WaveProperty();
    private WaveProgressAnim mWaveAnim = new WaveProgressAnim();//新建一个动画

    private Bitmap mBitmap;//缓存bitmap
    private Canvas mBitmapCanvas;

    public WaveProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mWavePro.getAttrs(context, attrs);//初始化一下

        mWavePath = new Path();//波浪路径

        mWavePaint = new Paint();
        mWavePaint.setColor(mWavePro.getFirstWaveColor());//第一条波浪的颜色
        mWavePaint.setAntiAlias(true);//抗锯齿
        mWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));//根据绘制顺序的不同选择相应的模式即可

        mSecondPaint = new Paint();
        mSecondPaint.setColor(mWavePro.getSecondWaveColor());//第二条波浪的颜色带点透明度
        mSecondPaint.setAntiAlias(true);//抗拒齿
        mSecondPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mWavePro.getCirclePaintColor());//背景圆的颜色
        mCirclePaint.setAntiAlias(true);//设为抗锯齿

        mWaveAnim.setRepeatCount(Animation.INFINITE);//无限循环
        mWaveAnim.setDuration(mWavePro.getAnimPeriod());//设置动画周期
        mWaveAnim.setInterpolator(new LinearInterpolator());//是线性的
        startAnimation(mWaveAnim);//启动动画
    }


    //波浪平移动画
    public class WaveProgressAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mWaveMoveDistance = interpolatedTime * mWaveNum * mWavePro.getWaveWidth() * 2;
            postInvalidate();//重绘
        }
    }

    /**
     * 思路就是这个思路算是个模板吧
     * 测量View的宽高
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 获取高-测量规则的模式和大小
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 设置wrap_content的默认宽 / 高值 // 默认宽/高的设定并无固定依据,根据需要灵活设置 // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = (int) mWavePro.getViewSize();
        int mHeight = (int) mWavePro.getViewSize();
        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight); // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mViewWidth = w;//view的宽
        mViewHeight = h;//view的高
        int min = Math.min(mViewHeight, mViewWidth);
        mWavePro.setViewSize(Math.min(min, mWavePro.getViewSize()));//得到小的
        mWaveNum = (int) Math.ceil(Double.parseDouble(String.valueOf((mWavePro.getViewSize() / mWavePro.getWaveWidth() / 2))));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float viewSize = mWavePro.getViewSize();
        mBitmap = Bitmap.createBitmap((int) viewSize, (int) viewSize, Bitmap.Config.ARGB_8888);
        mBitmapCanvas = new Canvas(mBitmap);
        mBitmapCanvas.drawCircle(viewSize / 2, viewSize / 2, viewSize / 2, mCirclePaint);
        //绘制一下
        mBitmapCanvas.drawPath(getWavePath(), mWavePaint);
        mBitmapCanvas.drawPath(getSecondWavePath(), mSecondPaint);//绘制第三条边
        canvas.drawBitmap(mBitmap, (mViewWidth - viewSize) / 2,
                (mViewHeight - viewSize) / 2, null);
    }

    /**
     * 得到第一条波浪
     *
     * @return
     */
    public Path getWavePath() {

        float viewSize = mWavePro.getViewSize();
        float waveHeight = (mWavePro.isChangeHeight()) ? mWavePro.getWaveHeight() * (1 - mPercent) : mWavePro.getWaveHeight();//是否改变宽高

        mWavePath.reset();//重置

        mWavePath.moveTo(viewSize, (1 - mPercent) * viewSize);//右上角
        mWavePath.lineTo(viewSize, viewSize);//右下
        mWavePath.lineTo(0, viewSize);//左下角
        mWavePath.lineTo(-mWaveMoveDistance, (1 - mPercent) * viewSize);//左上角

        for (int i = 0; i < mWaveNum * 2; i++) {
            mWavePath.rQuadTo(mWavePro.getWaveWidth() / 2, waveHeight, mWavePro.getWaveWidth(), 0);
            mWavePath.rQuadTo(mWavePro.getWaveWidth() / 2, -waveHeight, mWavePro.getWaveWidth(), 0);
        }
        mWavePath.close();//关闭曲线
        return mWavePath;
    }

    /**
     * 得到第二条波浪
     *
     * @return
     */
    public Path getSecondWavePath() {
        float viewSize = mWavePro.getViewSize();
        float waveHeight = (mWavePro.isChangeHeight()) ? mWavePro.getWaveHeight() * (1 - mPercent) : mWavePro.getWaveHeight();//是否改变宽高
        mWavePath.reset();
        mWavePath.moveTo(0, (1 - mPercent) * viewSize); //移动到左上方，也就是p3点
        mWavePath.lineTo(0, viewSize); //移动到左下方，也就是p2点
        mWavePath.lineTo(viewSize, viewSize);//移动到右下方，也就是p1点
        mWavePath.lineTo(viewSize + mWaveMoveDistance, (1 - mPercent) * viewSize); //移动到右上方，也就是p0点
        //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值）
        for (int i = 0; i < mWaveNum * 2; i++) {
            mWavePath.rQuadTo(-mWavePro.getWaveWidth() / 2, waveHeight, -mWavePro.getWaveWidth(), 0);
            mWavePath.rQuadTo(-mWavePro.getWaveWidth() / 2, -waveHeight, -mWavePro.getWaveWidth(), 0);
        } //将path封闭起来
        mWavePath.close();
        return mWavePath;
    }

    /**
     * 设置百分比
     * [0, 1]
     */
    public void setPercent(float percent) {

        int firstWaveColor = mWavePro.getCurrentFirstColor(percent);
        int secondWaveColor = mWavePro.getCurrentSecondColor(percent);
        if (mWavePaint.getColor() != firstWaveColor)
            mWavePaint.setColor(firstWaveColor);//设置第一支笔的颜色
        if (mSecondPaint.getColor() != secondWaveColor)
            mSecondPaint.setColor(secondWaveColor);//设置第二支笔的颜色

        mPercent = percent;//设置百分比
        if (mPercent == 1) {
            this.clearAnimation();//停止动画
        }
        postInvalidate();//重绘
    }

    /**
     * 设置第一个颜色列表
     */
    public void setFirstColorList(int... colorList) {
        mWavePro.setFirstColorList(colorList);
    }

    /**
     * 设置第二个颜色列表
     *
     * @param colorList
     */
    public void setSecondColorList(int... colorList) {
        mWavePro.setSecondColorList(colorList);
    }

}
