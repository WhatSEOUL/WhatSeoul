package com.example.whatseoul.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlanService {
	private static final String BASE_URL = "https://kdt-api-function.azurewebsites.net/api/v1/question";

	@Value("${alan.api.client_id}")
	private String CLIENT_ID;
	private static final String CONTENT = "오늘 놀러갈만한 서울 명소를 추천해줄래?";
	private final RestTemplate restTemplate;

	public AlanService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String fetchAlanResponse() {
		String uri = UriComponentsBuilder
			.fromHttpUrl(BASE_URL)
			.queryParam("content", CONTENT)
			.queryParam("client_id", CLIENT_ID)
			.toUriString();

		log.info("uri: {}", uri);
		return restTemplate.getForObject(uri, String.class);
	}
}
