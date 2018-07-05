package com.example.waveloaddemo.util;

import android.content.Context;

/**
 * Created by 花花不花花 on 2017/7/14.
 */

public class DensityUtil {

    public static int dipToPx(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxToDp(Context context,float pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
