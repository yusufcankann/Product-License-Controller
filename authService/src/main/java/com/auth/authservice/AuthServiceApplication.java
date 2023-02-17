package com.auth.authservice;

import com.auth.domain.Role;
import com.auth.domain.SystemUser;
import com.auth.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com")
@EnableJpaRepositories(basePackages = "com.auth.repository")
@EntityScan(basePackages = "com.auth.domain")
@EnableEurekaClient
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {

            SystemUser userJohn = new SystemUser("john", "1234");
            userService.saveUser(userJohn);

            SystemUser userWill = new SystemUser("will", "1234");
            userService.saveUser(userWill);

            SystemUser userJim =new SystemUser("jim", "1234");
            userService.saveUser(userJim);

            SystemUser userAdmin =new SystemUser("admin", "admin");
            userService.saveUser(userAdmin);



            Role johnRole = new Role(null,"ROLE_COMPANY",userJohn);
            userService.saveRole(johnRole);

            Role willRole = new Role(null,"ROLE_USER",userWill);
            userService.saveRole(willRole);

            Role jimRole = new Role(null,"ROLE_COMPANY",userJim);
            userService.saveRole(jimRole);

            Role adminRole1 = new Role(null,"ROLE_USER",userAdmin);
            userService.saveRole(adminRole1);

            Role adminRole2 = new Role(null,"ROLE_COMPANY",userAdmin);
            userService.saveRole(adminRole2);


        };
    }
}
