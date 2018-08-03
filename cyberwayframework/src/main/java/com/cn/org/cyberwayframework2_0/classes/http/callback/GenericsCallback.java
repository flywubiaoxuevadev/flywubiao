package com.cn.org.cyberwayframework2_0.classes.http.callback;

import com.cn.org.cyberwayframework2_0.classes.base.ErrorBean;
import com.cn.org.cyberwayframework2_0.classes.filter.JSONFilter;
import com.cn.org.cyberwayframework2_0.classes.utils.StringUtil;

import java.io.IOException;

import okhttp3.Response;


public abstract class GenericsCallback<T> extends Callback<T> {
    private JSONFilter jsonFilter;
    private Class<T> cls;
    private ErrorBean errs;

    private IGenericsSerializator mGenericsSerializator;

    public GenericsCallback(Class<T> entityClass,JSONFilter jsonFilter){
        mGenericsSerializator = getGenericsSerializator();
        this.jsonFilter = jsonFilter;
        this.cls = entityClass;
    }


    @Override
    public T parseNetworkResponse(Response response, int id) {
        try {
            String string = response.body().string();
            if (StringUtil.getInstance().isEmpty(string)){
                errs = new ErrorBean();
                errs.setErrorCode(10003);
                return  null;
            }
            if (cls != null){
                T bean = mGenericsSerializator.transform(string, cls,jsonFilter,id);
                return bean;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  abstract IGenericsSerializator getGenericsSerializator();
}
