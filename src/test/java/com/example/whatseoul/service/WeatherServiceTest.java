package com.example.whatseoul.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.whatseoul.dto.response.WeatherDataDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Weather;
import com.example.whatseoul.repository.cityData.AreaRepository;
import com.example.whatseoul.repository.cityData.WeatherRepository;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

	@InjectMocks
	WeatherService weatherService;

	@Mock
	AreaRepository areaRepository;

	@Mock
	WeatherRepository weatherRepository;

	@DisplayName("날씨데이터 조회 테스트")
	@Test
	public void testGetWeatherData() {

		// given
		Area area = new Area();
		String areaCode = "POI001";
		Weather weather = Weather.builder()
			.temperature("22")
			.maxTemperature("30")
			.minTemperature("15")
			.pm25Index("좋음")
			.pm10Index("보통")
			.pcpMsg("비가 오지 않습니다.")
			.weatherTime("2024-05-16 09:00")
			.area(area)
			.build();

		when(areaRepository.findAreaByAreaCode(areaCode)).thenReturn(Optional.of(area));
		when(weatherRepository.findWeatherByArea(area)).thenReturn(Optional.of(weather));

		// when
		WeatherDataDto weatherDataDto = weatherService.getWeatherData(areaCode);

		// then
		assertNotNull(weatherDataDto);
		assertEquals("22", weatherDataDto.getTemperature());
		assertEquals("30", weatherDataDto.getMaxTemp());
		assertEquals("15", weatherDataDto.getMinTemp());
		assertEquals("좋음", weatherDataDto.getPm25Idx());
		assertEquals("보통", weatherDataDto.getPm10Idx());
		assertEquals("비가 오지 않습니다.", weatherDataDto.getPcpMsg());
		assertEquals("2024-05-16 09:00", weatherDataDto.getUpdateTime());

	}
}
