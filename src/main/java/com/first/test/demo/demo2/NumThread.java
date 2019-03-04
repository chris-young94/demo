//package com.first.test.demo.demo2;
//
//import java.io.IOException;
//import java.math.BigInteger;
//public class NumThread implements Runnable {
//
//    GetAllBets getAllBets = new GetAllBets();
//    int j = 1;
//    int y = 5015;
//    int i = 1;
//    boolean flag = true;
//
//    @Override
//    public void run() {
//        if (i>y) {
//            flag = false;
//            synchronized (getAllBets) {
//                while (flag) {
//                    BigInteger I = BigInteger.valueOf(i);
//                    BigInteger J = BigInteger.valueOf(j);
//                    int a = 0;
//                    try {
//                        a = getAllBets.getPlayerRoundBets(I, J);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    int b = 0;
//                    try {
//                        b = getAllBets.allStaticProfits(I, J);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    i = i + 1;
//                    if (a >= b && a != 0) {
//                         Counter.addOne();
//                    }
//
//                }
//            }
//        }
//    }
//}
