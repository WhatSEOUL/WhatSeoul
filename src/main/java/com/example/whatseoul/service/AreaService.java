package com.example.whatseoul.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.whatseoul.dto.response.AreaResponseDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.repository.cityData.AreaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AreaService {
	private final AreaRepository areaRepository;

	public List<AreaResponseDto> findAreasByAreaNames(List<String> areaNames) {
		List<AreaResponseDto> areaResponseDtos = new ArrayList<>();
		for (String areaName : areaNames) {
			Area area = areaRepository.findAreaByAreaName(areaName)
				.orElseThrow(() -> new RuntimeException("해당 지역명에 대한 정보를 찾을 수 없습니다. " + areaName));
			AreaResponseDto areaResponseDto = AreaResponseDto.builder()
				.areaCode(area.getAreaCode())
				.areaName(area.getAreaName())
				.areaLatitude(area.getAreaLatitude())
				.areaLongitude(area.getAreaLongitude())
				.build();
			areaResponseDtos.add(areaResponseDto);
		}
		return areaResponseDtos;
	}

}
