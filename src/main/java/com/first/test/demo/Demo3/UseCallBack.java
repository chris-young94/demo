package com.first.test.demo.Demo3;

public class UseCallBack implements CallBack2 {

    //回调函数
    public void act1(SomeThing arg1, MyCallBack arg2) {
        arg2.doSomeThing(arg1, "something", this);
    }

    public String result1() {
        return "ok";
    }

    public String result2() {
        return "not ok";
    }
}
