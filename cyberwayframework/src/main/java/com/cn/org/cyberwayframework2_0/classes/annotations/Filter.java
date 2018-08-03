package com.cn.org.cyberwayframework2_0.classes.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Smile on 16/11/30.
 */

@Target(ElementType.FIELD)//表示用在字段上
@Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时
public  @interface  Filter {

    Class<?>[] value() default Object.class;

}
