package com.cn.org.cyberwayframework2_0.classes.filter;


import com.cn.org.cyberwayframework2_0.classes.http.bean.BodyParameter;
import com.cn.org.cyberwayframework2_0.classes.http.bean.HeadParameter;

import java.util.List;

/**
 * 请求的过滤器
 * @author Smile
 */
public abstract class RequestFilter {
	public abstract void filter(List<HeadParameter> headParameters, List<BodyParameter> bodyParameters);
}
