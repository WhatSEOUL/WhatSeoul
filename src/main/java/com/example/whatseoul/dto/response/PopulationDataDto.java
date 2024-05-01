package com.example.whatseoul.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
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
	private String areaName;
}
