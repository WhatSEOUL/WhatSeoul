package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "CITYDATA")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CITYDATA_ID")
    private Long cityDataId;

    @Column(name = "AREA_NM", nullable = false)
    private String areaName;

    @Column(name = "AREA_CONGEST_LVL", nullable = false)
    private String areaCongestionLevel;

    @Column(name = "AREA_CONGEST_MSG", nullable = false)
    private String areaCongestionMessage;

    @Column(name = "PPLTN_TIME", nullable = false)
    private String pplUpdateTime;

    // 인구 예측값
    @Column(name = "FCST_PPLTN")
    private String forecastPopulation;

    @Column(name = "FCST_CONGEST_LVL")
    private String forecastCongestionLevel;

    @Column(name = "TEMP")
    private String temperature;

    @Column(name = "MAX_TEMP")
    private String maxTemperature;

    @Column(name = "MIN_TEMP")
    private String minTemperature;

    @Column(name = "PM25_INDEX")
    private String pm25Index;

    @Column(name = "PM25")
    private String pm25;

    @Column(name = "PM10_INDEX")
    private String pm10Index;

    @Column(name = "PM10")
    private String pm10;

    @Column(name = "WEATHER_TIME")
    private String weatherTime;

    @Column(name = "SKY_STTS")
    private String skyStatus;

    @Column(name = "EVENT_NAME")
    private String culturalEventName;

    @Column(name = "EVENT_PERIOD")
    private String culturalEventPeriod;

    @Column(name = "EVENT_PLACE")
    private String culturalEventPlace;

    @Column(name = "CULTURAL_EVENT_URL")
    private String culturalEventUrl;
}