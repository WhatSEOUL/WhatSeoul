package com.example.whatseoul.controller;


import com.example.whatseoul.dto.response.AlanResponseDto;
import com.example.whatseoul.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@Slf4j
public class SseController {

    private final WebClientService webClientService;

    @Value("${alan.key}")
    private String alanId;

    @GetMapping("/alan")
    public Flux<ServerSentEvent<AlanResponseDto>> getAlanResponse(){
        return webClientService.getResponse()
                .map(data -> ServerSentEvent.<AlanResponseDto>builder()
                        .id("eventId")
                        .event("eventType")
                        .data(data)
                        .build());
    }
}
