package com.example.whatseoul.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CulturalEventDto {
    private String name; // 행사 이름
    private String period; // 행사 기간
    private String location; // 행사 장소
    private String detailUrl; // 행사 상세 URL

}
