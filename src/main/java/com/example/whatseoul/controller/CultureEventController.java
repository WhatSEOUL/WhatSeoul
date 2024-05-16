package com.example.whatseoul.controller;

import com.example.whatseoul.dto.response.CultureEventDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.repository.cityData.AreaRepository;
import com.example.whatseoul.service.CultureEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CultureEventController {
    private final AreaRepository areaRepository;
    private final CultureEventService cultureEventService;

    @GetMapping("/culture-event/{areaName}")
    public ResponseEntity<List<CultureEventDto>> getCultureEventData(@PathVariable String areaName) {
        Area area = areaRepository.findAreaByAreaName(areaName)
                .orElseThrow(() -> new NoSuchElementException("Area not Found"));
        List<CultureEventDto> dataDto = cultureEventService.getCultureEventData(area.getAreaCode());
        return ResponseEntity.ok(dataDto);
    }
}
