package com.example.waveloaddemo.util;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by xiaohan on 2018/6/19.
 */

public class ColorUtil {

    /**
     * 工具类构造器
     */
    private void ColorUtil() {
    }

    /**
     * 根据fraction值来计算当前的颜色。
     */
    public static int getCurrentColor(float fraction, int... colors) {
        int currentIndex;
        float eachFraction;
        float currentFraction;
        int redCurrent;
        int blueCurrent;
        int greenCurrent;
        int alphaCurrent;

        if (colors.length == 1)
            return colors[0];
        else {
            currentIndex = (int) ((colors.length - 1) * fraction);

            if (fraction == 1)//当fraction为1时直接返回最后一个
                return colors[colors.length - 1];

            eachFraction = 1 / (float)(colors.length - 1);//每一段 占多少
            currentFraction = (fraction - currentIndex * eachFraction) / eachFraction;
        }

        int startColor = colors[currentIndex];
        int endColor = colors[currentIndex + 1];

        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        redCurrent = (int) (redStart + currentFraction * redDifference);
        blueCurrent = (int) (blueStart + currentFraction * blueDifference);
        greenCurrent = (int) (greenStart + currentFraction * greenDifference);
        alphaCurrent = (int) (alphaStart + currentFraction * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }

}
