package com.cn.org.cyberwayframework2_0.classes.interf;


/**
 * activity的视图监听
 */
public interface OnActivityLinster {
    /**
     * 状态栏的颜色
     */
    public abstract int statusBarTintResource();



    /**
     * 初始化Activity的view 在onCreate()内容之后执行
     */
    public abstract void initView();



}
