package com.example.whatseoul.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.whatseoul.dto.response.AlanBasicResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlanService {
	private static final String BASE_URL = "https://kdt-api-function.azurewebsites.net/api/v1/question";

	@Value("${alan.key}")
	private String CLIENT_ID;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public AlanService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
		this.restTemplate = restTemplateBuilder.build();
		this.objectMapper = objectMapper;
	}

	public AlanBasicResponseDto fetchAlanResponse(String content) throws JsonProcessingException {
		String uri = UriComponentsBuilder
			.fromHttpUrl(BASE_URL)
			.queryParam("content", content)
			.queryParam("client_id", CLIENT_ID)
			.toUriString();

		ResponseEntity<String> response =  restTemplate.getForEntity(uri, String.class);
		String responseBody = response.getBody();
		log.info("response: {}", response);
		log.info("responseBody: {}", responseBody);
		return parseJsonResponse(responseBody);
	}

	private AlanBasicResponseDto parseJsonResponse(String responseBody) throws JsonProcessingException {
		return objectMapper.readValue(responseBody, AlanBasicResponseDto.class);
	}
}
