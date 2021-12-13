package com.dongyuwuye.component_net;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by：mc on 2020/7/20 14:38
 * email:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseProcess {
    String typeName() default "";

    String typeValue() default "";
}
