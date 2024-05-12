package com.example.whatseoul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

	@Transactional
	@Scheduled(cron = "0 52 13/24 * * *")
	public void fetchAlanAreaResponse() throws JsonProcessingException {
		long startTime = System.currentTimeMillis();
		List<String> names = areaRepository.findAllAreaNames();
		// 하루 100회까지만 가능 - 사실상 분리해서 질의해야 함
		for (int i = 10; i < 40; i++) {
			log.info("area {} start", names.get(i));

			String content = names.get(i) + "의 도로명 주소를 한줄로 알려줘."
				+ "\n반드시 그 어떤 다른 안내문구도 없이 도로명 주소만을 알려줘.";

			String uri = UriComponentsBuilder
				.fromHttpUrl(BASE_URL)
				.queryParam("content", content)
				.queryParam("client_id", CLIENT_ID)
				.toUriString();

			ResponseEntity<String> response =  restTemplate.getForEntity(uri, String.class);
			String responseBody = response.getBody();
			AlanBasicResponseDto jsonResponse = parseJsonResponse(responseBody);
			String areaLocationInfo = jsonResponse.getContent();
			log.info("areaLocationInfo = {}", areaLocationInfo);
			areaRepository.updateAreaLocationInfoByAreaName(names.get(i), areaLocationInfo);
			log.info("area {} end", names.get(i));
		}
		long endTime = System.currentTimeMillis();
		long totalTime = (endTime-startTime)/1000;
		log.info("소요 시간 = " + totalTime);
	}

	private AlanBasicResponseDto parseJsonResponse(String responseBody) throws JsonProcessingException {
		return objectMapper.readValue(responseBody, AlanBasicResponseDto.class);
	}
}
