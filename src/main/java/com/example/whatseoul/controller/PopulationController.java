package com.example.whatseoul.controller;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.whatseoul.dto.response.PopulationDataDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.repository.cityData.AreaRepository;
import com.example.whatseoul.service.PopulationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PopulationController {
	private final AreaRepository areaRepository;
	private final PopulationService populationService;

	@GetMapping("/ppltn/{areaName}")
	public ResponseEntity<PopulationDataDto> getPopulationData(@PathVariable String areaName) {
		Area area = areaRepository.findAreaByAreaName(areaName)
			.orElseThrow(() -> new NoSuchElementException("Area not Found"));
		PopulationDataDto dataDto = populationService.getPopulationData(area.getAreaCode());
		return ResponseEntity.ok(dataDto);
	}

}
