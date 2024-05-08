package com.example.whatseoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//추가
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class WhatSeoulApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatSeoulApplication.class, args);
    }

}
