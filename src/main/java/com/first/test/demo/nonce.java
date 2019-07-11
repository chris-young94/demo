package com.first.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages = "com.first.test.demo.demo4")
@EnableScheduling
@EnableTransactionManagement
@EnableCaching
public class nonce {

    public static void main(String[] args) {
        SpringApplication.run(nonce.class, args);
    }
}
