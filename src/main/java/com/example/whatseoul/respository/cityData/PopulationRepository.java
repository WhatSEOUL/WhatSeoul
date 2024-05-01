package com.example.whatseoul.respository.cityData;

import com.example.whatseoul.entity.Population;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopulationRepository extends JpaRepository<Population, Long> {
}
