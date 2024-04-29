package com.example.whatseoul.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    // 기온
    @Column(name = "TEMP")
    private String temperature;

    // 최고 기온
    @Column(name = "MAX_TEMP")
    private String maxTemperature;

    // 최저 기온
    @Column(name = "MIN_TEMP")
    private String minTemperature;

    // 초미세먼지 지표
    @Column(name = "PM25_INDEX")
    private String pm25Index;

    // 미세먼지 지표
    @Column(name = "PM10_INDEX")
    private String pm10Index;

    // 강수 관련 메시지
    @Column(name = "PCP_MSG")
    private String pcpMsg;

    // 날씨 데이터 업데이트 시간
    @Column(name = "WEATHER_TIME")
    private String weatherTime;

    @Column(name = "EVENT_NAME")
    private String culturalEventName;

    @Column(name = "EVENT_PERIOD")
    private String culturalEventPeriod;

    @Column(name = "EVENT_PLACE")
    private String culturalEventPlace;

    @Column(name = "CULTURAL_EVENT_URL")
    private String culturalEventUrl;
}