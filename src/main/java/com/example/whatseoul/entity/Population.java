package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POPULATION")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POPULATION_ID")
    private Long populationId;

    @OneToOne
    @JoinColumn(name = "AREA_CODE")
    private Area area;

    // 장소 혼잡도 지표
    @Column(name = "AREA_CONGEST_LVL", nullable = false)
    private String areaCongestionLevel;

    // 장소 혼잡도 지표 관련 메세지
    @Column(name = "AREA_CONGEST_MSG", nullable = false)
    private String areaCongestionMessage;

    // 실시간 인구 데이터 업데이트 시간
    @Column(name = "PPLTN_TIME", nullable = false)
    private String pplUpdateTime;
}