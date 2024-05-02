package com.example.whatseoul.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PopulationForecastDataDto {
	private String forecastTime;
	private String forecastCongestionLevel;
	private String forecastPopulationMin;
	private String forecastPopulationMax;
}
