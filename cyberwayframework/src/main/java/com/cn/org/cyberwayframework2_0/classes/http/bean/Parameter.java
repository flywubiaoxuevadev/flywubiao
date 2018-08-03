package com.cn.org.cyberwayframework2_0.classes.http.bean;

import com.cn.org.cyberwayframework2_0.classes.annotations.Filter;
import com.cn.org.cyberwayframework2_0.classes.annotations.HttpRespon;
import com.cn.org.cyberwayframework2_0.classes.annotations.Id;
import com.cn.org.cyberwayframework2_0.classes.annotations.Method;
import com.cn.org.cyberwayframework2_0.classes.config.AppBaseConfig;
import com.cn.org.cyberwayframework2_0.classes.filter.JSONFilter;
import com.cn.org.cyberwayframework2_0.classes.filter.RequestFilter;
import com.cn.org.cyberwayframework2_0.classes.http.abstracts.INetWorkAbstract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Smile
 */
public class Parameter {
	private List<HeadParameter> headParameter;
	private List<BodyParameter> bodyParameter;
	private int id = -1;
	private String url;
	private long saveTime;
	private AppBaseConfig.Method method = AppBaseConfig.Method.GET;

	private INetWorkAbstract netWorkAbstract;

	private String content;

	private RequestFilter requestFilter;
	private JSONFilter jsonFilter;

	private Class<?> clas = String.class;		//用户请求的bean

	private boolean netWorkTag;   //网络状态


	public String getContent() {
		return content;
	}

	public void addContent(String content) {
		this.content = content;
	}

	public void setNetWorkTag(boolean netWorkTag) {
		this.netWorkTag = netWorkTag;
	}

	public INetWorkAbstract getNetWorkAbstract() {
		return netWorkAbstract;
	}

	public boolean getnetWorkTag() {
		return this.netWorkTag;
	}

	public void setMethod(AppBaseConfig.Method method) {
		this.method = method;
	}

	public AppBaseConfig.Method getMethod() {
		return method;
	}

	public void setUrl(String url){
		this.url = url;
	}
	public Parameter addBodyParameter(String key, String value) {
		if (bodyParameter == null) {
			bodyParameter = new ArrayList<BodyParameter>();
		}
		BodyParameter param = new BodyParameter();
		param.key = key;
		param.value = value;
		bodyParameter.add(param);
		return this;
	}

	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}

	public Parameter addHeadParameter(String key, String value) {
		if (headParameter == null) {
			headParameter = new ArrayList<HeadParameter>();
		}
		HeadParameter param = new HeadParameter();
		param.key = key;
		param.value = value;
		headParameter.add(param);
		return this;
	}

	public Parameter setSaveTime(long i) {
		this.saveTime = i;
		return this;
	}
	public List<HeadParameter> getHeadParameter() {
		if (headParameter == null){
			headParameter = new ArrayList<HeadParameter>();
		}
		return headParameter;
	}
	public void setHeadParameter(List<HeadParameter> headParameter) {
		this.headParameter = headParameter;
	}
	public List<BodyParameter> getBodyParameter() {
		if (bodyParameter == null){
			bodyParameter = new ArrayList<BodyParameter>();
		}
		return bodyParameter;
	}
	public void setBodyParameter(List<BodyParameter> bodyParameter) {
		this.bodyParameter = bodyParameter;
	}
	public String getUrl() {
		return url;
	}
	public long getSaveTime() {
		return saveTime;
	}
	@Override
	public String toString() {
		return "Parameter [headParameter=" + headParameter + ", bodyParameter="
				+ bodyParameter + "]";
	}

	public void setParameter(int id, INetWorkAbstract netWorkAbstract) {
		if (netWorkAbstract == null){
			throw new RuntimeException("method:\"execute()\"参数异常");
		}
		this.netWorkAbstract = netWorkAbstract;
		initRuestData(netWorkAbstract,id);
	}

	private void initRuestData(INetWorkAbstract netWorkAbstract,int id) {
		Class<? extends INetWorkAbstract> aClass = netWorkAbstract.getClass();
		Field[] declaredFields = aClass.getDeclaredFields();
		for (Field field:declaredFields){
			if (field.isAnnotationPresent(HttpRespon.class)) {
				field.setAccessible(true);
				try {
					initData(field,Id.class);
					if(id == this.id){
						initData(field,Method.class);
						initData(field,Filter.class);
						clas = field.getAnnotation(HttpRespon.class).value();
						this.url = field.get(netWorkAbstract).toString();
						netWorkTag = true;
						break;
					}
					netWorkTag = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	 public String handlerUrl() {
		String url = this.getUrl() ;
		List<BodyParameter> bodyParameters = this.getBodyParameter();
		if(bodyParameters == null) return url;
		if(url.contains("?")){
			for(BodyParameter meter:bodyParameters){
				if(url.lastIndexOf("?") == url.length()-1){
					url += meter.key + "=" + meter.value;
				}else{
					url +="&" + meter.key + "=" + meter.value;
				}
			}
		}else{
			for(int i=0;i<bodyParameters.size();i++){
				BodyParameter bodyParameter = bodyParameters.get(i);
				if(i==0){
					url += "?" +  bodyParameter.key + "=" + bodyParameter.value;
				}else{
					url += "&" + bodyParameter.key + "=" +bodyParameter.value;
				}
			}
		}
		return url;
	}


	private void initData(Field field,Class<? extends Annotation> annotationType){
		if (field.isAnnotationPresent(annotationType)) {
			try {
				Annotation annotation = field.getAnnotation(annotationType);
				if (annotation instanceof Id){
					this.id = field.getAnnotation(Id.class).value();
				}else if (annotation instanceof Method){
					this.method = field.getAnnotation(Method.class).value();
				}else if (annotation instanceof Filter){
					for (Class<?> filter:field.getAnnotation(Filter.class).value()){
						Object o = filter.newInstance();
						if (o instanceof RequestFilter){
							requestFilter = (RequestFilter) o;
						} else if(o instanceof  JSONFilter){
							jsonFilter = (JSONFilter) o;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public RequestFilter getParamFilter(){
		return requestFilter;
	}

	public JSONFilter getJSONFilter(){
		return jsonFilter;
	}

	public Class<?> getBeanClass(){
		return clas;
	}
}
