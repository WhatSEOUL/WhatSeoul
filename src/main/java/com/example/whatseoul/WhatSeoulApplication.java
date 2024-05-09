package com.example.whatseoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
@EnableScheduling
public class WhatSeoulApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatSeoulApplication.class, args);
    }

}
