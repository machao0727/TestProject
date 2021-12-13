package com.dongyuwuye.compontent_base.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mc on 2018/3/28.
 * activity注解类
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityFeature {
    int layout() default 0;

    String rightTitleTxt() default "none";

    int titleTxt() default -1;

    int toolbarArrow() default -1;//左边箭头
}
