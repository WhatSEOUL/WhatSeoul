package com.example.whatseoul.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CultureEventDto {
    private String culturalEventName;
    private String culturalEventPeriod;
    private String culturalEventPlace;
    private String culturalEventUrl;
    private String areaName;
}
