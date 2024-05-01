package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CULTURE_EVENT")
public class CultureEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "EVENT_NAME")
    private String culturalEventName;

    @Column(name = "EVENT_PERIOD")
    private String culturalEventPeriod;

    @Column(name = "EVENT_PLACE")
    private String culturalEventPlace;

    @Column(name = "CULTURAL_EVENT_URL")
    private String culturalEventUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_CODE", referencedColumnName = "AREA_CODE")
    private Area area;
}