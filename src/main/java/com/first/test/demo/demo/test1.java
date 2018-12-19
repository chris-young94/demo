package com.first.test.demo.demo;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

class test1 {
    public static void main(String[] args) throws IOException {


        System.out.println(System.currentTimeMillis());
        int a = (int) (System.currentTimeMillis()/86400/1000) ;
        System.out.println(a);

        Date data = new Date(System.currentTimeMillis()-86400000);
        Long b = new Robot().getStartTime(data);
        Long c = new Robot().getEndTime(data);
        System.out.println(data);
        System.out.println(b);
        System.out.println(new Date(b));
        System.out.println(c);
        System.out.println(new Date(c));
    }
}
