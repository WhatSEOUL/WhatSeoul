package com.example.whatseoul.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.whatseoul.dto.response.AreaResponseDto;
import com.example.whatseoul.dto.response.KakaoResponseDto;
import com.example.whatseoul.dto.response.LatLongResponseDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.repository.cityData.AreaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AreaService {
	private final AreaRepository areaRepository;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public List<AreaResponseDto> findAreasByAreaNames(List<String> areaNames) {
		List<AreaResponseDto> areaResponseDtos = new ArrayList<>();
		for (String areaName : areaNames) {
			Area area = areaRepository.findAreaByAreaName(areaName)
				.orElseThrow(() -> new RuntimeException("해당 지역명에 대한 정보를 찾을 수 없습니다. " + areaName));
			AreaResponseDto areaResponseDto = buildAreaResponseDto(area);
			areaResponseDtos.add(areaResponseDto);
		}
		return areaResponseDtos;
	}

	public AreaResponseDto findAreaByAreaName(String areaName) {
		Area area = areaRepository.findAreaByAreaName(areaName)
			.orElseThrow(() -> new RuntimeException("해당 지역명에 대한 정보를 찾을 수 없습니다. " + areaName));
		return buildAreaResponseDto(area);
	}

	private AreaResponseDto buildAreaResponseDto(Area area) {
		return AreaResponseDto.builder()
			.areaCode(area.getAreaCode())
			.areaName(area.getAreaName())
			.areaLatitude(area.getAreaLatitude())
			.areaLongitude(area.getAreaLongitude())
			.areaLocationInfo(area.getAreaLocationInfo())
			.areaAttractionInfo(area.getAreaAttractionInfo())
			.build();
	}

	@Value("${kakao.api.key}")
	private String KAKAO_KEY;

	// @Transactional
	// @Scheduled(cron = "0 57 00/24 * * *")
	public List<LatLongResponseDto> getLatLngByAreaName(List<String> areaNames) throws JsonProcessingException {
		ArrayList<LatLongResponseDto> updatedLatLongResponses = new ArrayList<>();
		// 컨트롤러 없이 메소드 스케줄링만 할 때의 코드(메소드 인자 삭제 필요)
		// List<String> areaNames = areaRepository.findAreaNamesWhereAddressNotNull();
		for (String areaName : areaNames) {
			Optional<Area> optionalArea = areaRepository.findAreaByAreaName(areaName);
			if (optionalArea.isPresent()) {
				Area area = optionalArea.get();
				log.info("areaId: {}, area : {}", area.getAreaId(), area.getAreaName());
				String address = area.getAreaAddress();

				// 카카오 로컬 api url, header 설정
				String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address + "&page=45&size=30";
				log.info("apiUrl: {}", apiUrl);
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization", "KakaoAK " + KAKAO_KEY);
				headers.setContentType(MediaType.APPLICATION_JSON);

				// HttpEntity 생성 및 헤더 적용
				HttpEntity<?> entity = new HttpEntity<>(headers);

				// API 호출
				ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

				String responseBody = responseEntity.getBody();
				log.info("responseEntity.getBody: {}", responseBody);

				KakaoResponseDto kakaoResponseDto = parseJsonResponse(responseBody);
				log.info("parsed response: {}", kakaoResponseDto);

				String x = kakaoResponseDto.getX(); // x좌표 - 경도
				String y = kakaoResponseDto.getY(); // y좌표 - 위도
				log.info("x = {}, y = {}", x, y);

				// API 호출 결과로 얻은 위도, 경도를 해당 지역에 업데이트
				areaRepository.updateLongitudeLatitudeByAreaName(areaName, Double.parseDouble(y), Double.parseDouble(x));

				LatLongResponseDto updatedArea = LatLongResponseDto.builder()
					.areaId(area.getAreaId())
					.areaCode(area.getAreaCode())
					.areaName(area.getAreaName())
					.updatedLat(area.getAreaLatitude())
					.updatedLong(area.getAreaLongitude())
					.build();

				updatedLatLongResponses.add(updatedArea);
			}

		}


		// 응답 반환
		return updatedLatLongResponses;
	}

	private KakaoResponseDto parseJsonResponse(String responseBody) throws JsonProcessingException {
		// 응답 문자열을 JsonNode로 변환
		JsonNode rootNode = objectMapper.readTree(responseBody);

		// JsonNode에서 좌표 정보 추출
		JsonNode documentsNode = rootNode.get("documents").get(0); // 첫 번째 결과를 사용
		String x = documentsNode.get("x").asText();
		String y = documentsNode.get("y").asText();

		// KakaoResponseDto 객체 생성 및 반환
		return KakaoResponseDto.builder()
			.x(x)
			.y(y)
			.build();
	}


}
