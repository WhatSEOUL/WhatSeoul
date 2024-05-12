package com.example.whatseoul.controller.citydata;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.whatseoul.dto.request.AreaRequestDto;
import com.example.whatseoul.dto.response.AreaResponseDto;
import com.example.whatseoul.service.AreaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class AreaController {
	private final AreaService areaService;

	@PostMapping("/area")
	public ResponseEntity<List<AreaResponseDto>> getAreasByAreaNames(@RequestBody AreaRequestDto request) {
		List<String> areaNames = request.getAreaNames();
		List<AreaResponseDto> areaResponseDtos = areaService.findAreasByAreaNames(areaNames);
		return ResponseEntity.ok(areaResponseDtos);
	}

	// 지역정보 개별 조회
	@GetMapping("/area")
	public ResponseEntity<AreaResponseDto> getAreaByAreaName(@RequestParam String areaName) {
		AreaResponseDto areaResponseDto = areaService.findAreaByAreaName(areaName);
		return ResponseEntity.ok(areaResponseDto);
	}
}
