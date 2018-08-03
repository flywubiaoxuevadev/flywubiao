package com.cn.org.cyberwayframework2_0.classes.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * 软键盘监听是否弹出
 *
 * PS:在onPase()中调用
 * fix.destroyAndroidAdjustResizeBugFix();// 注销软键盘监听器
 */
public class AndroidAdjustResizeBugFixUtil {
	private View mChildOfContent;
	private int usableHeightPrevious;
	private int statusBarHeight;
	private FrameLayout.LayoutParams frameLayoutParams;
	private Activity mActivity;

	private AndroidAdjustResizeBugFixUtil(Activity activity) {
		mActivity = activity;
		FrameLayout content = (FrameLayout) activity
				.findViewById(android.R.id.content);
		mChildOfContent = content.getChildAt(0);
		statusBarHeight = getStatusBarHeight();
		mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(
				myOnGlobalLayoutListener);
		frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent
				.getLayoutParams();
	}

	ViewTreeObserver.OnGlobalLayoutListener myOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
		@Override
		public void onGlobalLayout() {
			possiblyResizeChildOfContent();
		}
	};

	public static AndroidAdjustResizeBugFixUtil assistActivity(Activity activity) {
		return new AndroidAdjustResizeBugFixUtil(activity);
	}

	public void setOnAndroidAdjustResizeBugFixListener(
			OnAndroidAdjustResizeBugFixListener listenr) {
		this.listenr = listenr;
	}

	public OnAndroidAdjustResizeBugFixListener listenr;

	public interface OnAndroidAdjustResizeBugFixListener {
		public void onAndroidAdjustResizeBugFixListenerHit();

		public void onAndroidAdjustResizeBugFixListenerShow();
	}

	@SuppressWarnings("deprecation")
	public void destroyAndroidAdjustResizeBugFix() {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
			mChildOfContent.getViewTreeObserver().removeGlobalOnLayoutListener(
					myOnGlobalLayoutListener);
		} else {
			mChildOfContent.getViewTreeObserver().removeOnGlobalLayoutListener(
					myOnGlobalLayoutListener);
		}
	}

	private void possiblyResizeChildOfContent() {
		int usableHeightNow = computeUsableHeight();
		if (usableHeightNow != usableHeightPrevious) {
			int usableHeightSansKeyboard = mChildOfContent.getRootView()
					.getHeight();
			int heightDifference = usableHeightSansKeyboard - usableHeightNow;
			if (heightDifference > (usableHeightSansKeyboard / 4)) {
				// keyboard probably just became visible
				// 如果有高度变化，mChildOfContent.requestLayout()之后界面才会重新测量
				// 这里就随便让原来的高度减去了1
				frameLayoutParams.height = usableHeightSansKeyboard - 1;
				if (listenr != null) {
					listenr.onAndroidAdjustResizeBugFixListenerShow();
				}
			} else {
				frameLayoutParams.height = usableHeightSansKeyboard;
				if (listenr != null) {
					listenr.onAndroidAdjustResizeBugFixListenerHit();
				}
			}
			mChildOfContent.requestLayout();
			usableHeightPrevious = usableHeightNow;
		}
	}

	private int computeUsableHeight() {
		Rect r = new Rect();
		mChildOfContent.getWindowVisibleDisplayFrame(r);
		return r.bottom - r.top + statusBarHeight;
	}

	private int getStatusBarHeight() {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			int dimensionPixelSize = mActivity.getResources()
					.getDimensionPixelSize(x);
			return dimensionPixelSize;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}