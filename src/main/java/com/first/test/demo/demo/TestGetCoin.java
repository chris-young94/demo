package com.first.test.demo.demo;


/**
 * @author chris
 */
class TestGetCoin {
    private final static int THREAD_NUM = 5;
    public static void main(String[] args)  {
        for(int c= 1;c<=THREAD_NUM;c=c+1){
            new Thread(new RequestTask("https://faucet.metamask.io/")).start();
        }
    }
}
