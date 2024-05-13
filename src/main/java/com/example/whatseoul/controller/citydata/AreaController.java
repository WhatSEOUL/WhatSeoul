package com.example.whatseoul.controller.citydata;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	// 구 단위 지역정보 조회
	@GetMapping("/area")
	public ResponseEntity<List<AreaResponseDto>> getAreasByAreaNames(@RequestParam("areaName") List<String> areaNames ) {
		List<AreaResponseDto> areaResponseDtos = areaService.findAreasByAreaNames(areaNames);
		return ResponseEntity.ok(areaResponseDtos);
	}

	// 지역정보 개별 조회
	@GetMapping("/area/{areaName}")
	public ResponseEntity<AreaResponseDto> getAreaByAreaName(@PathVariable String areaName) {
		AreaResponseDto areaResponseDto = areaService.findAreaByAreaName(areaName);
		return ResponseEntity.ok(areaResponseDto);
	}
}
