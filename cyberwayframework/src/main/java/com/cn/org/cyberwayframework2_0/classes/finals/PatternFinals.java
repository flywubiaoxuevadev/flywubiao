package com.cn.org.cyberwayframework2_0.classes.finals;

/**
 * 一些正测的规则
 */
public interface PatternFinals {
    //邮箱
    public final static String emailer = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    //手机号码
    public final static String phone = "^((13[0-9])|170|(15[^4,\\D])|(18[0,5-9]))\\d{8}$" ;
}
