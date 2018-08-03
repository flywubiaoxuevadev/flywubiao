package com.cn.org.cyberwayframework2_0.classes.http.bean;


public class ResponseBean {
	
	private String action;
	private Object obj;
	private ErrData err;
	private int code;
	
	public void setCode(int code){
		this.code = code;
	}
	
	public int getCode (){
		return code;
	}
	
	public static class ErrData{
		private String errMessage;
		private String err_code;
		public String getErrMessage() {
			return errMessage;
		}
		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}
		public String getErr_code(){return err_code;}
		public void setErr_code(String err_code){this.err_code = err_code;}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public ErrData getErr() {
		return err;
	}

	public void setErrd(ErrData errd) {
		this.err = errd;
	}
}
