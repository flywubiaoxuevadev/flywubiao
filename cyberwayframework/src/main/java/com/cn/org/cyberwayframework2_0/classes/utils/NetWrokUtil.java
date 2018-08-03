package com.cn.org.cyberwayframework2_0.classes.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cn.org.cyberwayframework2_0.classes.finals.PromptFinal;


/**
 * 网络连接的工具类
 * @author Smile
 */
public class NetWrokUtil {
	private NetWrokUtil(){};
	
	private static NetWrokUtil netWrokUtils;
	public static NetWrokUtil getInstance(){
		if(netWrokUtils == null){
			netWrokUtils = new NetWrokUtil();
		}
		return netWrokUtils;
	}
	/*
	* 检验网络连接 并toast提示  
	* @return  
	*/  
	public boolean note_Intent(Context context) {
	    ConnectivityManager con = (ConnectivityManager) context
	        .getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkinfo = con.getActiveNetworkInfo();
	    if (networkinfo == null || !networkinfo.isAvailable()) {  
	        return false;  
	    }  
	    boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
	    if (!wifi) { // 提示使用wifi  
	    	ToastUtil.show(context.getApplicationContext(), PromptFinal.WIFI_ERR);
	    }  
	    return true;  
	}
}
