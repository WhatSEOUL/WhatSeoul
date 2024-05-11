package com.example.whatseoul.controller.alan;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.whatseoul.dto.response.AlanBasicResponseDto;
import com.example.whatseoul.service.AlanService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AlanController {
	private final AlanService alanService;

	// 앨런 기본 질의
	@GetMapping("/alan/basic")
	public ResponseEntity<AlanBasicResponseDto> getAlanResponse(@RequestParam String content) throws
		JsonProcessingException {
		AlanBasicResponseDto response = alanService.fetchAlanResponse(content);
		return ResponseEntity.ok(response);
	}

	// 지역 위치 및 명소 정보 저장을 위한 앨런 기본 질의
	@GetMapping("/alan/basic/area")
	public ResponseEntity<Void> getAlanAreaResponse(@RequestParam("areaName") List<String> areaNames) throws
		JsonProcessingException {
		alanService.fetchAlanAreaResponse(areaNames);
		return ResponseEntity.ok().build();
	}
}
