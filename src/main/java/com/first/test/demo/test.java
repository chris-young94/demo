package com.first.test.demo;

import java.io.IOException;
import java.math.BigInteger;

public class test {
    public static void main(String[] args) throws IOException {
        BigInteger result2 = new BigInteger("1010000000000000000");
        BigInteger num1 = new BigInteger("1000000000000000000");
        BigInteger[] none = result2.divideAndRemainder(num1);
        int a = none[0].intValue();
        float b = none[1].floatValue();
        System.out.println(a);
        System.out.println(b);

    }
}