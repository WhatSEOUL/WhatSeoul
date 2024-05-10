package com.example.whatseoul.controller;

import com.example.whatseoul.entity.Test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TestRepository extends JpaRepository<Test, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE test SET title = :title WHERE id = 1", nativeQuery = true)
    int updateTitle(@Param("title") String title);
}