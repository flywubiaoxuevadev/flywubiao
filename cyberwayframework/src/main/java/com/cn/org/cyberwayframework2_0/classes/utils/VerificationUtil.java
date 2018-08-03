package com.cn.org.cyberwayframework2_0.classes.utils;

import com.cn.org.cyberwayframework2_0.classes.finals.PatternFinals;

import java.util.regex.Pattern;


/**
 * 验证相关信息
 */
public class VerificationUtil {

    private static VerificationUtil verificationUtil;
    private VerificationUtil(){

    }
    public static VerificationUtil getInstance(){
        if (verificationUtil == null)
            verificationUtil = new VerificationUtil();
        return verificationUtil;
    }

    /**
     * 验证value是否符合pattersnStr正则表达事
     * @param pattersnStr
     * @param value
     * @return
     */
    public boolean isVerification(String pattersnStr, String value){
        StringUtil stringUtil = StringUtil.getInstance();
        if(!stringUtil.isEmpty(pattersnStr)  && !stringUtil.isEmpty(value)){
            Pattern pattern = Pattern.compile(pattersnStr);
            return pattern.matcher(value).matches();
        }
        return false;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public  boolean isEmail(String email) {
        return isVerification(PatternFinals.emailer,email);
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public  boolean isPhone(String phoneNum) {
        return isVerification(PatternFinals.phone,phoneNum);
    }
}
