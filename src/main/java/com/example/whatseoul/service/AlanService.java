package com.example.whatseoul.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.whatseoul.dto.response.AlanResponseDto;
import com.example.whatseoul.dto.response.AlanSseResponseDto;
import com.example.whatseoul.dto.response.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlanService {
	private static final String BASE_URL = "https://kdt-api-function.azurewebsites.net/api/v1/question";

	@Value("${alan.api.client_id}")
	private String CLIENT_ID;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public AlanService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
		this.restTemplate = restTemplateBuilder.build();
		this.objectMapper = objectMapper;
	}

	public AlanResponseDto fetchAlanResponse(String content) throws JsonProcessingException {
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

	private AlanResponseDto parseJsonResponse(String responseBody) throws JsonProcessingException {
		return objectMapper.readValue(responseBody, AlanResponseDto.class);
	}
	public void fetchAlanSseResponse(String content) throws JsonProcessingException {
		String uri = UriComponentsBuilder
			.fromHttpUrl(BASE_URL)
			.path("/sse-streaming")
			.queryParam("content", content)
			.queryParam("client_id", CLIENT_ID)
			.toUriString();

		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		log.info("response: {}", response);
		String responseBody = response.getBody();
		log.info("responseBody: {}", responseBody);

		List<Event> events = parseSseJsonResponse(responseBody);
		for (Event event : events) {
			String json = objectMapper.writeValueAsString(event);
			log.info("json: {}", json);
		}

		// return parseSseJsonResponse(responseBody);
	}
	private List<Event> parseSseJsonResponse(String responseBody) throws JsonProcessingException {
		String[] lines = responseBody.split("\\n\\n");

		List<Event> events = new ArrayList<>();
		for (String line : lines) {
			Event event = new Event();
			String[] fields = line.split("\\n");
			for (String field : fields) {
				String[] keyValue = field.split(": ", 2);
				String key = keyValue[0];
				String value = keyValue[1];
				switch (key) {
					case "id":
						event.setId(value);
						break;
					case "event":
						event.setEvent(value);
						break;
					case "data":
						event.setData(value);
						break;
				}
			}
			events.add(event);
		}
		return events;
		// return objectMapper.readValue(responseBody, AlanSseResponseDto.class);
	}


}
