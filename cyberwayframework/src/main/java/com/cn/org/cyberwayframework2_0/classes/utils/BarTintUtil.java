package com.cn.org.cyberwayframework2_0.classes.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.cn.org.cyberwayframework2_0.classes.manager.SystemBarTintManager;


/**
 * 设置性侵式状态栏
 */
public class BarTintUtil {
    private Context context;

    private BarTintUtil (Context context){
        this.context = context;
    }

    public static BarTintUtil getInstance(Context context){
        return new BarTintUtil(context);
    }

    /**
     * 设置状态栏的颜色
     * @param pixelInsetTopTag
     * @param colorResource
     */
    public  int setMatchactionbar(boolean pixelInsetTopTag,int colorResource){
        return initMatchActionBar(pixelInsetTopTag,colorResource);
    }

    private int initMatchActionBar(boolean pixelTag,int colorResource) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager((Activity) context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorResource);//通知栏所需颜色
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        return config.getPixelInsetTop(pixelTag);
    }

    private void setTranslucentStatus(boolean translucentStatus) {
        Window window = ((Activity)context).getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (translucentStatus) {
            attributes.flags |= bits;
        } else {
            attributes.flags &= ~bits;
        }
        window.setAttributes(attributes);
    }
}
