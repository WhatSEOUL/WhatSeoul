package com.example.whatseoul.respository.cityData;

import com.example.whatseoul.entity.CultureEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CulturalEventRepository extends JpaRepository<CultureEvent, Long> {
}