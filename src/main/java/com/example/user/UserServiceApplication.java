package com.example.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("admin123"));

        BCryptPasswordEncoder   encoder=new BCryptPasswordEncoder();
        boolean matches =

        encoder.matches("admin123", "$2a$10$mIkx3KFyBo/W5BEnObAzKeiv0cUNH5R20iw32O9wCJnWfKRmFXu1e");


            System.out.println(" matches password :"+matches);
    }
}
