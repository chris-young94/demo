package com.first.test.demo.Demo3;

public class CallBackOne implements MyCallBack {

    @Override
    public String doSomeThing(SomeThing someThing, String some, CallBack2 callBack2) {
        //todo
        if (true) {
            return callBack2.result1();
        } else {
            return callBack2.result2();
        }
    }

    //异步回调
    public void doSome(SomeThing someThing, UseCallBack useCallBack) {
        new Thread(() -> useCallBack.act1(someThing, CallBackOne.this)).start();

        b_box();
    }

    public void b_box() {

    }
}
