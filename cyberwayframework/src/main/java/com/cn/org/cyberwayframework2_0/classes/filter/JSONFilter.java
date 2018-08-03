package com.cn.org.cyberwayframework2_0.classes.filter;

import com.cn.org.cyberwayframework2_0.classes.base.ErrorBean;

import org.json.JSONObject;

/**
 * Created by Smile on 16/11/30.
 */

public interface JSONFilter {

    public String filter(JSONObject jsonObject,int id);

    public ErrorBean filterErr(JSONObject jsonObject);

}
