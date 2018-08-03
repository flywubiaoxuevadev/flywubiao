package com.cn.org.cyberwayframework2_0.classes.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;

import com.cn.org.cyberwayframework2_0.R;
import com.cn.org.cyberwayframework2_0.classes.annotations.Click;
import com.cn.org.cyberwayframework2_0.classes.annotations.Head;
import com.cn.org.cyberwayframework2_0.classes.annotations.HttpRespon;
import com.cn.org.cyberwayframework2_0.classes.annotations.Id;
import com.cn.org.cyberwayframework2_0.classes.annotations.Layout;
import com.cn.org.cyberwayframework2_0.classes.controller.VaryViewHelperController;
import com.cn.org.cyberwayframework2_0.classes.interf.OnActivityLinster;
import com.cn.org.cyberwayframework2_0.classes.manager.ActivityTask;
import com.cn.org.cyberwayframework2_0.classes.receiver.NetStateReceiver;
import com.cn.org.cyberwayframework2_0.classes.utils.BarTintUtil;
import com.cn.org.cyberwayframework2_0.classes.utils.NetChangeObserver;
import com.cn.org.cyberwayframework2_0.classes.utils.NetUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AppBaseActivity extends AppCompatActivity implements View.OnClickListener,OnActivityLinster {
    Class<? extends AppBaseActivity> cl; 			// 类加载器
    private Field[] fields;
    private Context context;
    private ViewStub vsTitle;
    private ViewStub vsContent;
    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;

    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAct();
        onInitActivity(savedInstanceState);
        setContentView(R.layout.activity_root);
        findView();
        handleView();
        this.startView();
        this.initView();
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }
            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };
        NetStateReceiver.registerObserver(mNetChangeObserver);
        checkNetwork();
    }

    /**
     * 初始化Activity向外暴露的方法 在setContentView()之前
     * @param savedInstanceState
     */
    public void onInitActivity(Bundle savedInstanceState){

    }

    public void startView(){};

    private void findView() {
        vsTitle = (ViewStub) findViewById(R.id.act_root_title);
        vsContent = (ViewStub) findViewById(R.id.act_root_content);
        cl = getClass();
    }

    /*
        初始化activity
    */
    public  void initAct(){
        ActivityTask.getInstance().addTask(this);
        this.context = this;
        int resource = statusBarTintResource();
        if(resource!=0){
            BarTintUtil instance = BarTintUtil.getInstance(this);
            instance.setMatchactionbar(true,resource);
        }
    }

    protected void handleView(){
        if(cl != null){
            initLayoutView(Layout.class,vsContent);
            initLayoutView(Head.class,vsTitle);
            initFields();
        }else{
            throw new RuntimeException("类加载初始化异常 method\"findViewClass()\"");
        }
    }

    private void initFields() {
        fields = cl.getDeclaredFields();
        if(fields != null){
            for (Field field : fields) {
                // 查看这个字段是否有我们自定义的注解类标志的
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class) && !field.isAnnotationPresent(HttpRespon.class)) {
                    try {
                        int id = (Integer) field.getAnnotation(Id.class).value();
                        field.set(this, findViewById(id));
                        if (field.isAnnotationPresent(Click.class)) {
                            ((View) field.get(this)).setOnClickListener(this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private <A extends Annotation> void initLayoutView(Class<A> annotationClass, ViewStub vs){
        if(cl.isAnnotationPresent(annotationClass)){
            Annotation an = cl.getAnnotation(annotationClass);
            if(an instanceof Layout){
                Layout la = (Layout) an;
                vsStubInflater(vs, la.value());
            }else if(an instanceof  Head){
                Head la = (Head) an;
                vsStubInflater(vs, la.value());
            }
        }
    }
    private void vsStubInflater(ViewStub vs,int resultId){
        vs.setLayoutResource(resultId) ;
        vs.inflate();
    }
    private void refletMethod(Field field,View v) throws Exception{
        String methodName = field.getAnnotation(Click.class).value();
        Method method = cl.getDeclaredMethod(methodName, new Class[] { View.class });
        method.setAccessible(true);
        method.invoke(this, new Object[] {v});
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(cl!=null){
            if(fields == null){
                fields = cl.getDeclaredFields();
            }
            if (fields != null) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Click.class)) {
                        try {
                            if(field.isAnnotationPresent(Id.class) && field.getAnnotation(Id.class).value() == id){
                                refletMethod(field, view);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void onLick(View view){};
//    /**
//     * toggle show loading
//     *
//     * @param toggle
//     */
//    protected void toggleShowLoading(boolean toggle, String msg) {
//        if (null == mVaryViewHelperController) {
//            throw new IllegalArgumentException("You must return a right target view for loading");
//        }
//
//        if (toggle) {
//            mVaryViewHelperController.showLoading(msg);
//        } else {
//            mVaryViewHelperController.restore();
//        }
//    }

    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * get loading target view
     */
    protected    View getLoadingTargetView(){
        return null;
    }


    /**
     * network connected
     */
    protected  void onNetworkConnected(NetUtils.NetType type){

    }


    /**
     * network disconnected
     */
    protected  void onNetworkDisConnected(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityTask.getInstance().remove(this);
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }
    public  void  checkNetwork()
    {
        if (!NetUtils.isNetworkConnected(this)) {
            toggleNetworkError(true,null);
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            //throw new IllegalArgumentException("You must return a right target view for loading");
            return;
        }else {
            if (toggle) {
                mVaryViewHelperController.showNetworkError(onClickListener);
            } else {
                mVaryViewHelperController.restore();
            }
        }
    }
    /**
     * no data
     *
     * @param toggle
     */
    public void noData(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            //throw new IllegalArgumentException("You must return a right target view for loading");
            return;
        }else {
            if (toggle) {
                mVaryViewHelperController.showNoData(onClickListener);
            }
        }
    }

}
