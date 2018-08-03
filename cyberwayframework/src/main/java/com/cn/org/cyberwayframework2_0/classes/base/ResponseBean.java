package com.cn.org.cyberwayframework2_0.classes.base;



public class ResponseBean<T> {
    private T t;
    private int id;

    public T getBean() {
        return t;
    }

    public void setBean(T t) {
        this.t = t;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
