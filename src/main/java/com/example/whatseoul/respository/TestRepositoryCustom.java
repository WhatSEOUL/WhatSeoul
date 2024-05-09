package com.example.whatseoul.respository;


import org.springframework.stereotype.Repository;

@Repository
public interface TestRepositoryCustom {

    int updateTitleUnsafe(String title);
}
