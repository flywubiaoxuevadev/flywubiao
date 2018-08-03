package com.cn.org.cyberwayframework2_0.classes.manager;

import android.app.Activity;

import com.cn.org.cyberwayframework2_0.classes.base.AppBaseActivity;

import java.util.ArrayList;
import java.util.List;


public class ActivityTask {
	private List<AppBaseActivity> mlist ;;
	
	private ActivityTask(){
		mlist = new ArrayList<AppBaseActivity>();
	};
	
	private static ActivityTask manager;
	
	public static ActivityTask getInstance(){
		if(manager == null){
			manager = new ActivityTask();
		}
		return manager;
	}

	public void addTask(AppBaseActivity baseActivity) {
		mlist.add(baseActivity);
	}

	public void remove(Activity act) {
		mlist.remove(act);
	}

	/**
	 * 退出Apk时，清除（finish）栈中没有关闭的Activity
	 */
	public void exitApk() {
		if (mlist.size() >= 0) {
			for (Activity act : mlist) {
				act.finish();
			}
		}
	}

	// 结束指定的activity
	public  void finishActivity(Class<? extends AppBaseActivity>... classList) {
		if (mlist != null && mlist.size() > 0) {
			for (Activity activity : mlist) {
				if (classList != null && classList.length > 0) {
					for (Class<?extends AppBaseActivity> classObj : classList) {
						if (classObj == activity.getClass()) {
							activity.finish();
						}
					}
				}

			}
		}
	}
	// 结束指定的activity外的Activity
	public  void finishOtherActivity(Class<? extends AppBaseActivity>   currentActivity) {
		if (mlist != null && mlist.size() > 0) {
			for (Activity activity : mlist) {
				if (currentActivity == activity.getClass()) {
					continue;
				}else{
					activity.finish();
				}
			}
		}
	}

	/**
	 * 置顶某项Activity视图
	 * @param clas
	 * @return
	 */
	public <T> boolean  placedAtTheTop(Class<? extends AppBaseActivity> clas){

		return false;
	}
	
	
	/**
	 * 删除某些Activity视图
	 * @param clas
	 * @return
	 */
	public boolean delectActivitys(Class<? extends AppBaseActivity>... clas){
		return false;
	}
	
	/**
	 * 删除某个Activity视图
	 * @param clas
	 * @return
	 */
	public boolean delectActivity(Class<? extends AppBaseActivity> clas){
		return false;
	}
	
}

