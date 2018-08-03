package com.cn.org.cyberwayframework2_0.classes.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.cn.org.cyberwayframework2_0.R;
import com.cn.org.cyberwayframework2_0.classes.annotations.Click;
import com.cn.org.cyberwayframework2_0.classes.annotations.Head;
import com.cn.org.cyberwayframework2_0.classes.annotations.HttpRespon;
import com.cn.org.cyberwayframework2_0.classes.annotations.Id;
import com.cn.org.cyberwayframework2_0.classes.annotations.Layout;
import com.cn.org.cyberwayframework2_0.classes.controller.VaryViewHelperController;
import com.cn.org.cyberwayframework2_0.classes.utils.NetUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AppBaseFragment extends Fragment implements View.OnClickListener{

    private Class<? extends AppBaseFragment> cl; 			// 类加载器
    private Field[] fields;
    private ViewStub vsTitle;
    private ViewStub vsContent;
    private View contain;
    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        if (contain == null) {
            contain = inflater.inflate(R.layout.activity_root, null);
        } else {
            ((ViewGroup) contain.getParent()).removeView(contain);
        }
        findView(contain);
        handleView(contain);
        this.startView();
        this.initView();
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
        checkNetwork();
        return contain;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitActivity(savedInstanceState);
    }

    /**
     * 初始化Activity向外暴露的方法 在setContentView()之前
     * @param savedInstanceState
     */
    public void onInitActivity(Bundle savedInstanceState){

    }

    private void findView(View view) {
        vsTitle = (ViewStub)view. findViewById(R.id.act_root_title);
        vsContent = (ViewStub)view. findViewById(R.id.act_root_content);
        cl = getClass();
    }



    protected void handleView(View view){
        if(cl != null){
            initLayoutView(Layout.class,vsContent);
            initLayoutView(Head.class,vsTitle);
            initFields(view);
        }else{
            throw new RuntimeException("类加载初始化异常 method\"findViewClass()\"");
        }
    }

    private void initFields(View view) {
        fields = cl.getDeclaredFields();
        if(fields != null){
            for (Field field : fields) {
                // 查看这个字段是否有我们自定义的注解类标志的
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class) && !field.isAnnotationPresent(HttpRespon.class)) {
                    try {
                        int id = (Integer) field.getAnnotation(Id.class).value();
                        field.set(this, view.findViewById(id));
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

    public void startView(){};

    public void onLick(View view){};

    /**
     * 初始化Activity的view 在onCreate()内容之后执行
     */
    public abstract void initView();
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
     * get loading target view
     */
    protected   View getLoadingTargetView(){
        return null;
    }
    public  void  checkNetwork()
    {
        if (!NetUtils.isNetworkConnected(getActivity())) {
            toggleNetworkError(true,null);
        }
    }
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
