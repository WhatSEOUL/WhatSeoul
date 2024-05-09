package com.example.whatseoul.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.whatseoul.dto.response.PopulationDataDto;
import com.example.whatseoul.dto.response.PopulationForecastDataDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Population;
import com.example.whatseoul.entity.PopulationForecast;
import com.example.whatseoul.repository.cityData.AreaRepository;
import com.example.whatseoul.repository.cityData.PopulationForecastRepository;
import com.example.whatseoul.repository.cityData.PopulationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PopulationService {
	private final AreaRepository areaRepository;
	private final PopulationRepository populationRepository;
	private final PopulationForecastRepository populationForecastRepository;

	public PopulationDataDto getPopulationData(String areaCode) {
		Area area = areaRepository.findAreaByAreaCode(areaCode)
			.orElseThrow(() -> new NoSuchElementException("Area Not Found"));
		Population population = populationRepository.findPopulationByArea(area)
			.orElseThrow(() -> new NoSuchElementException("Population Not Found"));
		List<PopulationForecast> forecasts = populationForecastRepository.findPopulationForecastsByPopulation(population);

		List<PopulationForecastDataDto> forecastDataDtoList = new ArrayList<>();
		for (PopulationForecast forecast : forecasts) {
			PopulationForecastDataDto forecastDataDto = PopulationForecastDataDto.builder()
				.forecastTime(forecast.getForecastTime())
				.forecastCongestionLevel(forecast.getForecastCongestionLevel())
				.forecastPopulationMin(forecast.getForecastPopulationMin())
				.forecastPopulationMax(forecast.getForecastPopulationMax())
				.build();
			forecastDataDtoList.add(forecastDataDto);
		}

		return PopulationDataDto.builder()
			.areaName(area.getAreaName())
			.areaCongestionLevel(population.getAreaCongestionLevel())
			.areaCongestionMessage(population.getAreaCongestionMessage())
			.pplUpdateTime(population.getPplUpdateTime())
			.populationForecasts(forecastDataDtoList)
			.build();
	}

}
