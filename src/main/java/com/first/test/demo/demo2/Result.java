package com.first.test.demo.demo2;

import com.first.test.demo.demo.Robot;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;

import static com.sun.tools.javac.util.Constants.format;

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


        Result result = new Result(1, 500);
        new Thread(result).start();
        Result result1 = new Result(501, 1000);
        new Thread(result1).start();
        Result result2 = new Result(1001, 1500);
        new Thread(result2).start();
        Result result3 = new Result(1501, 2000);
        new Thread(result3).start();
        Result result4 = new Result(2001, 2500);
        new Thread(result4).start();
        Result result5 = new Result(2501, 3000);
        new Thread(result5).start();
        Result result6 = new Result(3001, 3500);
        new Thread(result6).start();
        Result result7 = new Result(3501, 4000);
        new Thread(result7).start();
        Result result8 = new Result(4001, 4500);
        new Thread(result8).start();
        Result result9 = new Result(4501, 5000);
        new Thread(result9).start();
        Result result10 = new Result(5001, 5105);
        new Thread(result10).start();

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