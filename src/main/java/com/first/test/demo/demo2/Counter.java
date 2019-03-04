package com.first.test.demo.demo2;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {
    public static AtomicLong counter = new AtomicLong(0);
    public static AtomicLong piid = new AtomicLong(1);


    public static long addOne() {
        return counter.incrementAndGet();
    }

    public static long addPone() {
        return piid.incrementAndGet();
    }

    public static long add(long value){
        return counter.addAndGet(value);}
}