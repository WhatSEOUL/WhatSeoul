package com.example.whatseoul.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LatLongResponseDto {
	private Long areaId;
	private String areaCode;
	private String areaName;
	private Double updatedLat;
	private Double updatedLong;
}
