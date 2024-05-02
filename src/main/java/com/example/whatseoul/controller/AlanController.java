package com.example.whatseoul.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.whatseoul.service.AlanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AlanController {
	private final AlanService alanService;

	@GetMapping("/alan")
	public ResponseEntity<String> getAlanResponse() {
		String response = alanService.fetchAlanResponse();
		log.info("response: {}", response);

		return ResponseEntity.ok(response);
	}
}
