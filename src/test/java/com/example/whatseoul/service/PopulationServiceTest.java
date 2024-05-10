package com.example.whatseoul.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.whatseoul.dto.response.PopulationDataDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Population;
import com.example.whatseoul.entity.PopulationForecast;
import com.example.whatseoul.respository.cityData.AreaRepository;
import com.example.whatseoul.respository.cityData.PopulationForecastRepository;
import com.example.whatseoul.respository.cityData.PopulationRepository;

@ExtendWith(MockitoExtension.class)
public class PopulationServiceTest {
	@InjectMocks
	PopulationService populationService;

	@Mock
	AreaRepository areaRepository;

	@Mock
	PopulationRepository populationRepository;

	@Mock
	PopulationForecastRepository populationForecastRepository;

	private final String areaCode = "POI001";

	@DisplayName("인구데이터 조회 테스트")
	@Test
	public void testGetPopulationData() {
		// given

		Area area = new Area();
		Population population = new Population();
		PopulationForecast populationForecast = PopulationForecast.builder()
			.forecastCongestionLevel("여유")
			.forecastPopulationMin("3000")
			.forecastPopulationMax("3500")
			.forecastTime("2024-05-10")
			.build();
		when(areaRepository.findAreaByAreaCode(areaCode)).thenReturn(Optional.of(area));
		when(populationRepository.findPopulationByArea(area)).thenReturn(Optional.of(population));
		when(populationForecastRepository.findPopulationForecastsByPopulation(population)).thenReturn(
			List.of(populationForecast));


		// when
		PopulationDataDto result = populationService.getPopulationData(areaCode);

		// then
		assertNotNull(result);
		assertEquals(area.getAreaName(), result.getAreaName());
		assertEquals(population.getAreaCongestionLevel(), result.getAreaCongestionMessage());
		assertEquals(population.getAreaCongestionMessage(), result.getAreaCongestionMessage());
		assertEquals(population.getPplUpdateTime(), result.getPplUpdateTime());
		assertEquals(1, result.getPopulationForecasts().size());
		assertEquals(populationForecast.getForecastCongestionLevel(),
			result.getPopulationForecasts().get(0).getForecastCongestionLevel());
		assertEquals(populationForecast.getForecastPopulationMax(),
			result.getPopulationForecasts().get(0).getForecastPopulationMax());
		assertEquals(populationForecast.getForecastPopulationMin(),
			result.getPopulationForecasts().get(0).getForecastPopulationMin());
	}
}
