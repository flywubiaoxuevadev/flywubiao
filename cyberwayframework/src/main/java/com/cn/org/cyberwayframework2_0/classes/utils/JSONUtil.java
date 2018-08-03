package com.cn.org.cyberwayframework2_0.classes.utils;



public class JSONUtil {

    public static String handlerJSON(String string) {
        if ("[".startsWith(string) && "]".endsWith(string)){
            string = "{\"list\":"  + string +"}";
        }
        return string;
    }
}
