package com.example.whatseoul.repository;


import org.springframework.stereotype.Repository;

@Repository
public interface TestRepositoryCustom {

    int updateTitleUnsafe(String title);
}
