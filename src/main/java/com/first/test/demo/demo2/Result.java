package com.first.test.demo.demo2;

import java.io.IOException;
import java.math.BigInteger;


public class Result implements Runnable {

    public Result(int i, int y) {
        this.i = i;
        this.y = y;
    }

    GetAllBets getAllBets = new GetAllBets();
    int j = 1;
    int y;
    int i;

    public static void main(String[] args) {

        Result result10 = new Result(5001, 5105);
        new Thread(result10).start();

        for(int c= 1,b=500;c<=5000;c=c+500,b=b+500){
            new Thread(new Result(c,b)).start();
        }
    }

    @Override
    public void run() {

        while (i <= y) {
            BigInteger I = BigInteger.valueOf(i);
            BigInteger J = BigInteger.valueOf(j);
            try {
                int a = getAllBets.getPlayerRoundBets(I, J);

                BigInteger[] result = getAllBets.allStaticProfits(I, J);
                int b = result[0].intValue();
                double c = result[1].floatValue() / 1000000000 / 1000000000;
                System.out.println("b=" + b + "c=" + c);
                if (a > b && a != 0) {
                    long value3;
                    if (c < 0.9) {
                        value3 = (long) Math.floor(a - b);
                    } else {
                        value3 = (long) Math.floor(a - b - 1);
                    }
                    Counter.add(value3);
                    System.out.println("Counter.counter=" + Counter.counter);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadName=" + Thread.currentThread() + "i=" + i);
            i = i + 1;

        }

    }

}