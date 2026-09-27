package com.pulseapi.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.pulseapi")
public class PulseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PulseApiApplication.class, args);
    }
}