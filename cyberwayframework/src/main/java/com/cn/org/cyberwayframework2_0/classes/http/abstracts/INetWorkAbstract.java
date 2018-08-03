package com.cn.org.cyberwayframework2_0.classes.http.abstracts;

import com.cn.org.cyberwayframework2_0.classes.base.ErrorBean;
import com.cn.org.cyberwayframework2_0.classes.base.ResponseBean;
import com.cn.org.cyberwayframework2_0.classes.utils.AlertDialogUtil;
import com.cn.org.cyberwayframework2_0.classes.utils.StringUtil;

/**
 * Created by Smile on 16/11/30.
 */

public abstract class INetWorkAbstract {

    /**
     * 在接口请求之前
     *
     * @param action
     */
    public void onBefore(int action) {

    }

    /**
     * 在接口请求中
     *
     * @param progress
     * @param total
     * @param action
     */
    public void inProgress(float progress, long total, int action) {

    }

    /**
     * 在接口请求之后
     *
     * @param action
     */
    public void onAfter(int action) {



    }

    /**
     * 请求失败调用的方法
     */
    public void onFalue(int action, ErrorBean errorBean) {
        if(errorBean!=null) {
            int errorCode = errorBean.getErrorCode();
            if (errorCode == 0) {
                //success
                onStateSuccess(action);
//            } else if (errorCode >= 1 && errorCode <= 3) {
//                //统一处理
//                if(!StringUtil.getInstance().isEmpty(errorBean.getErrorMessage())) {
//                    showErrorMsg(action, errorBean);
//                }
//            } else if (errorCode == 4) {
//                //未登陆或超时
//                loginTimeOut(action);
//            } else if (errorCode == 5) {
//                //暂无数据,显示空试图
//                showEmptyView(action);
//            } else if (errorCode >= 6 && errorCode <= 99) {
                //业务错误
            }else if(!StringUtil.getInstance().isEmpty(errorBean.getErrorMessage())) {
                    showErrorMsg(action, errorBean);
                }
            }

    }

    public <T> void onCall(ResponseBean<T> onCall) {
        AlertDialogUtil.cancelDlg();

    }

    public void onStateSuccess(int action) {

    }

    public abstract void showErrorMsg(int action, ErrorBean errorBean);


    public abstract void showEmptyView(int action);


    public void loginTimeOut(int action) {



    }


}
