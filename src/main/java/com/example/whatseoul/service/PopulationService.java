package com.example.whatseoul.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.whatseoul.dto.response.PopulationDataDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Population;
import com.example.whatseoul.respository.cityData.AreaRepository;
import com.example.whatseoul.respository.cityData.PopulationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PopulationService {
	private final AreaRepository areaRepository;
	private final PopulationRepository populationRepository;

	public PopulationDataDto getPopulationData(String areaCode) {
		Area area = areaRepository.findAreaByAreaCode(areaCode)
			.orElseThrow(() -> new NoSuchElementException("Area Not Found"));
		Population population = populationRepository.findPopulationByArea(area)
			.orElseThrow(() -> new NoSuchElementException("Weather Not Found"));

		return PopulationDataDto.builder()
			.areaName(area.getAreaName())
			.areaCongestionLevel(population.getAreaCongestionLevel())
			.areaCongestionMessage(population.getAreaCongestionMessage())
			.pplUpdateTime(population.getPplUpdateTime())
			.build();
	}

}
