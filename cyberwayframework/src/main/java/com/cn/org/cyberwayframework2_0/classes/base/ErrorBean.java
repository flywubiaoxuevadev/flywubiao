package com.cn.org.cyberwayframework2_0.classes.base;

/**
 * 此对象的内容包括
 * ErrorCode
 * ErrorMessage
 * HttpCode
 */

public class ErrorBean {
    private int errorCode;
    private String errorMessage;
    private int httpCode;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
}
