package com.example.whatseoul.service;

import com.example.whatseoul.dto.response.AlanResponseDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
@Slf4j
public class WebClientService {

    @Value("${alan.key}")
    private String alanId;
    private final WebClient webClient = WebClient.builder().build();
    private final ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    public Flux<AlanResponseDto> getResponse(){
        return webClient.get()
                .uri("https://kdt-api-function.azurewebsites.net/api/v1/question/sse-streaming",
                        uriBuilder -> uriBuilder
                        .queryParam("content", "red")
                        .queryParam("client_id", alanId)
                        .build())
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(this::parseJsonToResponseDto);
    }

    private Flux<AlanResponseDto> parseJsonToResponseDto(String jsonString){
        try {
            // Parse JSON string to ResponseDto object
            AlanResponseDto responseDto = this.objectMapper.readValue(jsonString, AlanResponseDto.class);
            return Flux.just(responseDto);
        } catch (JsonProcessingException e) {
            // Handle parsing exception
            return Flux.error(e);
        }
    }
}
