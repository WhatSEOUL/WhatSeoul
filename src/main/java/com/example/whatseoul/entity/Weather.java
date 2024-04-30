package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WEATHER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WEATHER_ID")
    private Long weatherId;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_CODE")
    private Area area;
}
