package com.example.whatseoul.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AreaResponseDto {
	private String areaCode;
	private String areaName;
	private Double areaLatitude;
	private Double areaLongitude;
}
