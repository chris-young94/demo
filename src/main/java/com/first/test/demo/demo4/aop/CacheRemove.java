package com.first.test.demo.demo4.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemove {

    /**
     * @des 根据命名规则与cacheable生成key的规则，例如@cacheable(cachename = "Ticker",key = "idtusdt") 则生成key名为Ticker::idtusdt
     * 则想要删除该key 必须制定 value 为Ticker key为idtusdt
     */
    // 需要删除的大类，
    String value() default "";


    // 需要制定删除的几个key的缓存
    String[] key() default "";
}
