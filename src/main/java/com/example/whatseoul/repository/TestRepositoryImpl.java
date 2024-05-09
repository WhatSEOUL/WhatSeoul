package com.example.whatseoul.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TestRepositoryImpl implements TestRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Modifying
    @Transactional
    public int updateTitleUnsafe(String title) {

        String SQL = "UPDATE test SET title = '" + title + "' WHERE id = 1";
        System.out.println("SQL = " + SQL);
        return entityManager.createNativeQuery(SQL).executeUpdate();
    }
}
