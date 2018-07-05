package com.example.waveloaddemo;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.waveloaddemo.custom_view.WaveProgressView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private WaveProgressView mWaveView;
    private TextView mValueText;
    private float mLastValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAnim();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mWaveView = findViewById(R.id.waveView);
        mWaveView.setFirstColorList(0x99FF2727, 0x99FF8C20, 0x99FBFF27, 0x998BE93A, 0x9932D132);
        mWaveView.setSecondColorList(0x66FF2727, 0x66FF8C20, 0x66FBFF27, 0x668BE93A, 0x6632D132);

        mValueText = findViewById(R.id.valueText);
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 0.9f);
        valueAnimator.addUpdateListener((v) -> {
                    float value = (float) v.getAnimatedValue();
                    mWaveView.setPercent(value);//设置值

                    value *= 100;
                    if (value == 100 || value - mLastValue >= 0.8f) {//加上这句话是避免数据更新太快，不然闪瞎狗眼😂
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMaximumFractionDigits(2);//小数位最多两位
                        nf.setMinimumFractionDigits(2);//小数位最低两位 保证后面保留两位小数 亲们可以自行更改
                        String text = nf.format(value);
                        mValueText.setText(text + "%");
                        mLastValue = value;
                    }
                }
        );

        valueAnimator.setDuration(5000);//3000秒
        valueAnimator.start();//开启动画
    }

}
