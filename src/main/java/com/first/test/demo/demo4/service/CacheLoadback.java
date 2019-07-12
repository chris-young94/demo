package com.first.test.demo.demo4.service;

public interface CacheLoadback<T> {
    /**
     * 重新请求数据
     * @return T
     */
    T loadback();
}
