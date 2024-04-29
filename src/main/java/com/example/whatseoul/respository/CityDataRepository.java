package com.example.whatseoul.respository;

import com.example.whatseoul.entity.CityData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDataRepository extends JpaRepository<CityData, Long> {
}
