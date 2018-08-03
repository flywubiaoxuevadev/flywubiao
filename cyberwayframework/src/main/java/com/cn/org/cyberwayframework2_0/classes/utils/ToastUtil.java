package com.cn.org.cyberwayframework2_0.classes.utils;


import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.cn.org.cyberwayframework2_0.classes.finals.PromptFinal;


/**
 * Tog帮助类
 * @author Smile
 */
public class ToastUtil {
	static String netWorkFailure = PromptFinal.NET_ERR;
	static Toast mToast;
	
	public static void show(Context mContext, int strRes) {
		mhandler.removeCallbacks(r);
		if (mToast == null) {
			mToast = Toast.makeText(mContext, strRes, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(strRes);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mhandler.postDelayed(r, 5000);
		mToast.show();
	}

	public static void showNetWorkFailure(Context mContext) {
		show(mContext, netWorkFailure);
	}


	public static void show(Context mContext, String text) {
		mhandler.removeCallbacks(r);
		if (mToast == null) {
			mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mhandler.postDelayed(r, 5000);
		mToast.show();
	}

	public static void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}
	private static Handler mhandler = new Handler();
	private static Runnable r = new Runnable(){
		public void run() {
			mToast.cancel();
		};
	};
	
}
