package com.validation.nonregistereduserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
public class NonRegisteredUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NonRegisteredUserServiceApplication.class, args);
    }
    
}

