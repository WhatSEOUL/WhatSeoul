package com.example.whatseoul.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PopulationDataDto {
	private String areaCongestionLevel;
	private String areaCongestionMessage;
	private String pplUpdateTime;
	List<PopulationForecastDataDto> populationForecasts;
	private String areaName;
}
