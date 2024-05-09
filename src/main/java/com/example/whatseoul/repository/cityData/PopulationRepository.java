package com.example.whatseoul.repository.cityData;

import java.util.Optional;

import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Population;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopulationRepository extends JpaRepository<Population, Long> {
	Optional<Population> findPopulationByArea(Area area);
}
