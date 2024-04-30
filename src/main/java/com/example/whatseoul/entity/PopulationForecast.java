package com.example.whatseoul.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	private int forecastId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POPULATION_ID")
	private Population population;

	// 인구 혼잡도 예측 시점
	@Column(name = "FCST_TIME")
	private String forecastTime;

	// 인구 혼잡도
	@Column(name = "FCST_CONGEST_LVL")
	private String forecastCongestionLevel;
}
