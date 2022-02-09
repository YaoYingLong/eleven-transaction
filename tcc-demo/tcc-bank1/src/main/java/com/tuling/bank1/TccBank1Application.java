package com.tuling.bank1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan({"com.tuling.bank1","org.dromara.hmily"})
public class TccBank1Application {

    public static void main(String[] args) {
        SpringApplication.run(TccBank1Application.class, args);
    }

}
