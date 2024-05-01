package com.example.whatseoul.respository.cityData;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.whatseoul.entity.PopulationForecast;

public interface PopulationForecastRepository extends JpaRepository<PopulationForecast, Long> {
}
