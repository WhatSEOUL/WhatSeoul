package com.example.whatseoul.repository.citydata;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.whatseoul.entity.CityData;

public interface CityDataRepository extends JpaRepository<CityData, Integer> {
	CityData findByAreaName(String areaName);
}
