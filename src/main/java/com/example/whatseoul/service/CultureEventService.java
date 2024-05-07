package com.example.whatseoul.service;

import com.example.whatseoul.dto.response.CultureEventDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.CultureEvent;
import com.example.whatseoul.respository.cityData.AreaRepository;
import com.example.whatseoul.respository.cityData.CulturalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class CultureEventService {
    private final CulturalEventRepository culturalEventRepository;
    private final AreaRepository areaRepository;

    public List<CultureEventDto> getCultureEventData(String areaName) {
        Area area = areaRepository.findAreaByAreaName(areaName)
                .orElseThrow(() -> new NoSuchElementException("Area not Found"));

        List<CultureEvent> cultureEvents = culturalEventRepository.findCultureEventsByArea(area);
        if (cultureEvents.isEmpty()) {
            throw new NoSuchElementException("Culture events not found for the provided area code");
        }

        List<CultureEventDto> cultureEventDtos = new ArrayList<>();
        for (CultureEvent cultureEvent : cultureEvents) {
            cultureEventDtos.add(
                CultureEventDto.builder()
                    .culturalEventName(cultureEvent.getCulturalEventName())
                    .culturalEventPeriod(cultureEvent.getCulturalEventPeriod())
                    .culturalEventPlace(cultureEvent.getCulturalEventPlace())
                    .culturalEventUrl(cultureEvent.getCulturalEventUrl())
                    .areaName(cultureEvent.getArea().getAreaName())
                    .build()
            );
        }

        return cultureEventDtos;
    }
}

