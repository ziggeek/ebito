package com.ebito.reference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReferenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReferenceApplication.class, args);
    }

}
