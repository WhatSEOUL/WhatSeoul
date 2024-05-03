package com.example.whatseoul.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POPULATION_FORECAST")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopulationForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORECAST_ID")
    private Long forecastId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POPULATION_ID", referencedColumnName = "POPULATION_ID")
    private Population population;

    // 인구 혼잡도 예측 시점
    @Column(name = "FCST_TIME")
    private String forecastTime;

    // 장소 예측 혼잡도 지표
    @Column(name = "FCST_CONGEST_LVL")
    private String forecastCongestionLevel;

    // 예측 실시간 인구 지표 최소값
    @Column(name = "FCST_PPLTN_MIN")
    private String forecastPopulationMin;

    // 예측 실시간 인구 지표 최대값
    @Column(name = "FCST_PPLTN_MAX")
    private String forecastPopulationMax;
}
