package com.cn.org.cyberwayframework2_0.classes.controller;

import android.content.Context;
import android.view.View;

/**
 * Created by wubiao on 2017/6/9.
 */

public interface IVaryViewHelper {
    public abstract View getCurrentLayout();

    public abstract void restoreView();

    public abstract void showLayout(View view);

    public abstract View inflate(int layoutId);

    public abstract Context getContext();

    public abstract View getView();

}
