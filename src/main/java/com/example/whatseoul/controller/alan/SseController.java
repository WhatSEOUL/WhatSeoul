package com.example.whatseoul.controller.alan;


import com.example.whatseoul.dto.response.AlanResponseDto;
import com.example.whatseoul.service.WebClientService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class SseController {

    private final WebClientService webClientService;

    @GetMapping("/alan")
    public Flux<ServerSentEvent<AlanResponseDto>> getAlanResponse(HttpServletResponse response, @RequestParam String content){
        response.setContentType("text/event-stream");
        return webClientService.getResponse(content)
                .doOnNext(data -> {
                    log.info("Received data: {}", data.getData().getContent());
                })
                .map(data -> ServerSentEvent.<AlanResponseDto>builder()
                        .data(data)
                        .build())
                .delayElements(Duration.ofMillis(50));
    }
}
