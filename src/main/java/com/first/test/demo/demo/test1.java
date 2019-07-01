package com.first.test.demo.demo;

import com.first.test.demo.demo2.Result;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

class test1 {
    public static void main(String[] args) throws IOException {

        for(int c= 1;c<=5;c=c+1){
            new Thread(new RequestTask("https://faucet.metamask.io/")).start();
        }

    }
}
