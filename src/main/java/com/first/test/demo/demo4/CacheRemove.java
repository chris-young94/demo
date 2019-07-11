package com.first.test.demo.demo4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemove {

    // 需要删除的大类，
    String value() default "";


    // 需要制定删除的几个key的缓存
    String[] key() default "";
}
