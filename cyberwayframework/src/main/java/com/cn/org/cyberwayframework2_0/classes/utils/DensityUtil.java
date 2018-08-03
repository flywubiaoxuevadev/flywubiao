package com.cn.org.cyberwayframework2_0.classes.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * 获取屏幕相关信息.
 */
public class DensityUtil {
    private Context context;

    private static DensityUtil densityUtils;

    private DensityUtil(Context context){
        this.context = context;
    }

    public static DensityUtil getInstance(Context context){
        if (densityUtils == null){
            densityUtils = new DensityUtil(context);
        }
        return densityUtils;
    }

    /**
     * 获取屏幕的宽和高
     * @return res[0] 宽  res[1]高
     */
    public int[] getDensity(){
        int[] res = new int[2];
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        res[0] = dm2.widthPixels;
        res[1] = dm2.heightPixels;
        return res;
    }

    /**
     * 获取状态栏的高度
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int dimensionPixelSize = context.getResources()
                    .getDimensionPixelSize(x);
            return dimensionPixelSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
