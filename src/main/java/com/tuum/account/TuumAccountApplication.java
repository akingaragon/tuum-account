package com.tuum.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TuumAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuumAccountApplication.class, args);
    }
}
