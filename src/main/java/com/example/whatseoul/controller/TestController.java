package com.example.whatseoul.controller;

import com.example.whatseoul.repository.TestRepositoryCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

    private final TestRepository testRepository;
    private final TestRepositoryCustom testRepositoryCustom;

    @PostMapping("/api/test")
    public String test(@Param("title") String title) {
        int result = testRepositoryCustom.updateTitleUnsafe(title);
        return "test";
    }
}




