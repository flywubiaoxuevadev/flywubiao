package com.cn.org.cyberwayframework2_0.classes.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * 当前版本的判断
 */
public class VersionUtil {
    private Context context;

    private static VersionUtil versionUtil;

    private VersionUtil(Context context){
        this.context = context;
    }

    public VersionUtil getInstance(Context context){
        if (versionUtil == null)
            versionUtil = new VersionUtil(context);
        return versionUtil;
    }

    /**
     * 判断当前手机版本是不是最新版本,通常会与本地存取的版本作比较
     * 主要比较的是versionName
     * ps: 当版本不一致的时候, 会出现app启动三张导航图
     * @return  true 一致 , false 不一致
     */
    public boolean isCurrentVersion(){
        String currentVerion = obtainCurrentVersion()[0];
        String hidtoryVersion = obtainHistoryVersion();
        if(currentVerion.equalsIgnoreCase(hidtoryVersion)){
            return true;
        }else{  //只有历史版本跟当前版本不一致的时候, 才把历史版本给替换掉
            saveCurrentVersion(currentVerion); //保存当前最新版本
            return false;
        }
    }

    /**
     *获取本地版本的最近的版本号
     * @return
     */
    public String obtainHistoryVersion(){
        return  SharedPreferencesUtil.getPrefString(context,"appVersion","");
    }


    /**
     *获取当前版本号
     * @return  version[0] versionName
     *          version[1] versionCode
     */
    public String[] obtainCurrentVersion(){
        String[] vserion = new String[2];
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            vserion[0] = pi.versionName;
            vserion[1] = pi.versionCode+"";
            if (vserion[0] == null || vserion[0].length() <= 0) {
                vserion[0] = "";
            }

        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return vserion;
    }

    /**
     * 保存当前版本
     * @param version
     */
    public void saveCurrentVersion(String version){
        if(!StringUtil.getInstance().isEmpty(version)){
            SharedPreferencesUtil.setPrefString(context,"appVersion",version);
        }
    }
}
