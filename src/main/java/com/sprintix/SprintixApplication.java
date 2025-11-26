package com.sprintix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprintixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SprintixApplication.class, args);
        System.out.println("🚀 API Spring Boot corriendo en http://localhost:8080/api");
    }
}