package com.tuling.bank2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.tuling.bank2","org.dromara.hmily"})
public class TccBank2Application {

    public static void main(String[] args) {
        SpringApplication.run(TccBank2Application.class, args);
    }

}
