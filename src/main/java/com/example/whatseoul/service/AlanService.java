package com.example.whatseoul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.whatseoul.dto.response.AlanBasicResponseDto;
import com.example.whatseoul.repository.cityData.AreaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlanService {
	private final AreaRepository areaRepository;
	private static final String BASE_URL = "https://kdt-api-function.azurewebsites.net/api/v1/question";

	@Value("${alan.key}")
	private String CLIENT_ID;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public AlanService(AreaRepository areaRepository, RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
		this.areaRepository = areaRepository;
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

	public void fetchAlanAreaResponse(List<String> areaNames) throws JsonProcessingException {
		for (String areaName : areaNames) {
			String content = areaName + "은(는) 는 어느 지역에 위치해 있는지 5줄 이내로 알려주세요."
				+ "\n 도로명 주소를 알려줄 수 있다면 굵은 글씨로 표기해주세요.";

			String uri = UriComponentsBuilder
				.fromHttpUrl(BASE_URL)
				.queryParam("content", content)
				.queryParam("client_id", CLIENT_ID)
				.toUriString();

			ResponseEntity<String> response =  restTemplate.getForEntity(uri, String.class);
			String responseBody = response.getBody();
			AlanBasicResponseDto jsonResponse = parseJsonResponse(responseBody);
			String areaLocationInfo = jsonResponse.getContent();
			areaRepository.updateAreaLocationInfoByAreaName(areaName, areaLocationInfo);
		}
	}

	private AlanBasicResponseDto parseJsonResponse(String responseBody) throws JsonProcessingException {
		return objectMapper.readValue(responseBody, AlanBasicResponseDto.class);
	}
}
