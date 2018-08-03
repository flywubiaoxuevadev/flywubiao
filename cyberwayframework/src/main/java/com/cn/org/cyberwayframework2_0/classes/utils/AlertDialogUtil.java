package com.cn.org.cyberwayframework2_0.classes.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.org.cyberwayframework2_0.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class AlertDialogUtil {
	private static Dialog loadingDialog;
	// 自定义dialog
	/**
	 * 显示dlg
	 *
	 * @param context
	 * @param isCanceledOnTouchOutsideFlag
	 *            取消设置true
	 */
	public static Dialog showProgressDlg(final Context context, int resourceId, boolean isProgressBarFlag,
										 String message, boolean isCanceledOnTouchOutsideFlag) {
		if(context!=null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.dialog_wait, null);// 得到加载view
			LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
			//ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
			TextView tv = (TextView) v.findViewById(R.id.tv);
			tv.setText("加载中");
			/** 创建自定义样式dialog **/
			loadingDialog = new Dialog(context, R.style.loading_dialog);
			/*** 外部点击可以取消dialog */
			loadingDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutsideFlag);
			loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.FILL_PARENT));
			Activity activity = (Activity) context;
			if ((activity!=null)&&(!activity.isFinishing())&&(!loadingDialog.isShowing())) {
				loadingDialog.show();
			}
		}
		return loadingDialog;
	}
	public static void cancelDlg() {
		 {
			 if (null != loadingDialog) {
					loadingDialog.dismiss();
					loadingDialog = null;
			 }
		 }

	}
	public static void cancelDlg(Context context) {
		if(context!=null) {
			Activity activity = (Activity) context;
			if ((activity!=null)&&(!activity.isFinishing())) {
				if (null != loadingDialog) {
					loadingDialog.dismiss();
					loadingDialog = null;
				}
			}
		}
	}

	public static void setOnItemdilogLick(OnItemdilogLick lick){
		licks = lick;
	}

	private static OnItemdilogLick licks;

	public interface  OnItemdilogLick{
		public void onItemDilogLick(String name, String key);
	}

	public static void showDialog(Context context, CharSequence title, CharSequence msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title == null || title.equals("")) {
			builder.setTitle("温馨提示");
		} else {
			builder.setTitle(title);
		}

		builder.setMessage(msg);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
//	/**
//	 * 创建底部的对话框
//	 * @param context
//	 * @param hm
//	 * @return
//	 */
//	public static  Dialog createDialog(Context context, final HashMap<String,String> hm, int headView) {
//		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View dialogView =  inflater.inflate(R.layout.layout_dialog, null);
//		final Dialog customDialog = new Dialog(context, R.style.bottom_custom_dialog);
//		WindowManager.LayoutParams localLayoutParams = customDialog.getWindow().getAttributes();
//		localLayoutParams.gravity = Gravity.BOTTOM;
//		int screenWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
//		dialogView.setMinimumWidth(screenWidth);
//		customDialog.onWindowAttributesChanged(localLayoutParams);
//		customDialog.getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
//						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//		customDialog.setCanceledOnTouchOutside(false);
//		customDialog.setCancelable(true);
//		customDialog.setCanceledOnTouchOutside(true);
//		ListView dialog_lv = (ListView) dialogView.findViewById(R.id.dialog_lv);
//		List<String> list  = handlerList(hm);
//		Window win = customDialog.getWindow();
//		win.getDecorView().setPadding(0, 0, 0, 0);
//		WindowManager.LayoutParams lp = win.getAttributes();
//		lp.width = WindowManager.LayoutParams.FILL_PARENT;
//		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//		win.setAttributes(lp);
//		customDialog.setContentView(dialogView);
//		View footer = inflater.inflate(R.layout.dialog_footer_listview,null);
//		View  dialgo_clear = footer.findViewById(R.id.dialgo_clear);
//		dialgo_clear.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				customDialog.dismiss();
//			}
//		});
//		dialog_lv.addFooterView(footer);
//		if(headView != 0){
//			View heand = inflater.inflate(headView,null);
//			dialog_lv.addHeaderView(heand);
//		}
//		if(licks != null){
//			dialog_lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//					TextView tv =(TextView) view;
//					String value = tv.getText().toString();
//					customDialog.dismiss();
//					licks.onItemDilogLick(tv.getText().toString(),hm.get(tv.getText().toString()));
//				}
//			});
//		}
//		dialog_lv.setAdapter(new DialogAdapter(context,list));
//		Utility.setListViewHeightBasedOnChildren(dialog_lv);
//		if (context instanceof Activity) {
//			Activity activity = (Activity) context;
//			if (!activity.isFinishing()) {
//				customDialog.show();
//			}
//		}
//		return customDialog;
//	}
	private static List<String> handlerList(HashMap<String, String> hm) {
		Iterator iter = hm.entrySet().iterator();
		List<String> list = new ArrayList<String>();
		while (iter.hasNext()) {
			Map.Entry<String,String> entry = (Map.Entry) iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			list.add(key);
		}
		return list;
	}

}
