package com.example.whatseoul.respository.cityData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Population;
import com.example.whatseoul.entity.PopulationForecast;

public interface PopulationForecastRepository extends JpaRepository<PopulationForecast, Long> {
	List<PopulationForecast> findPopulationForecastsByPopulation(Population population);
}
