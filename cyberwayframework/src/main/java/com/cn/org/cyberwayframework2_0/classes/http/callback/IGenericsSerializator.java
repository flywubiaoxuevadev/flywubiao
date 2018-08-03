package com.cn.org.cyberwayframework2_0.classes.http.callback;

import com.cn.org.cyberwayframework2_0.classes.filter.JSONFilter;

/**
 * Created by JimGong on 2016/6/23.
 */
public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT, JSONFilter jsonFilter, int id);


}
