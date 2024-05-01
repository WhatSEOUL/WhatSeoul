package com.example.whatseoul.respository;

import com.example.whatseoul.entity.CultureEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalEventRepository extends JpaRepository<CultureEvent, Long> {
}