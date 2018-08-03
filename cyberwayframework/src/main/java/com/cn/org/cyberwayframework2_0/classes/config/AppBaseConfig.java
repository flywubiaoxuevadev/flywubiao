package com.cn.org.cyberwayframework2_0.classes.config;


/**
 * app相关的简单配置
 */
public class AppBaseConfig {
    protected enum AppCacheMode{
        APP_CONFIG_SQL, //数据库存取
        APP_CONFIG_NATIVE,  //本地存取
        APP_CONFIG_NETWORK; //网络存取
    }

    public enum Method{
        POST,
        GET,
        DOWNLOAD,
        UPLOAD,
    }

}
