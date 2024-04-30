package com.example.whatseoul.service.citydata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.CityData;
import com.example.whatseoul.repository.area.AreaRepository;
import com.example.whatseoul.repository.citydata.CityDataRepository;
import com.example.whatseoul.util.TagName;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApiScheduler {

	private final AreaRepository areaRepository;
	private final CityDataRepository cityDataRepository;

	@Value("${seoul.open.api.url}")
	private StringBuilder url;

	//@Transactional
	//@Scheduled(cron = "0 */5 * * * *")
	// @Scheduled(initialDelay = 300000, fixedRate = 300000)
	public void call() {
		List<Area> areas = areaRepository.findAll();
		List<CompletableFuture<CityData>> futures = areas.stream()
			.map(this::fetchCityDataAsync)
			.collect(Collectors.toList());
		List<CityData> cityDataList = futures.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
		cityDataRepository.deleteAll();
		cityDataRepository.saveAll(cityDataList); // 실시간성에 가깝게 조회해보기??
	}

	private CompletableFuture<CityData> fetchCityDataAsync(Area area) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				log.info("areaId: {} areaName: {}", area.getAreaId(), area.getAreaName());
				String apiUrl = url + ("/" + urlEncoding(area.getAreaName()));
				log.info("인코딩 확인 {}", urlEncoding(area.getAreaName()));
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(apiUrl);
				return parseCityData(document);
			} catch (SAXException | IOException | ParserConfigurationException e) {
				log.error("error fetching citydata for areaname {}", area.getAreaName(), e);
				return null;
			}
		});
	}

	private CityData parseCityData(Document document) {
		CityData build = CityData.builder()
			.areaName(getElementText(document, TagName.AREA_NM.name()))
			// 인구
			.areaCongestionLevel(getElementText(document, TagName.AREA_CONGEST_LVL.name())) // 장소 혼잡도 지표
			.areaCongestionMessage(getElementText(document, TagName.AREA_CONGEST_MSG.name())) // 장소 혼잡도 지표 관련 메시지
			.pplUpdateTime(getElementText(document, TagName.PPLTN_TIME.name())) // 실시간 인구 데이터 업데이트 시간
			.forecastTime(getForeCastPopulationText(document, TagName.FCST_TIME.name())) // 인구 예측값 - 인구 예측시점
			.forecastCongestionLevel(getForeCastPopulationText(document, TagName.FCST_CONGEST_LVL.name())) // 인구 예측값 - 장소 예측 혼잡도 지표
			// 기온
			.temperature(getElementText(document, TagName.TEMP.name()))
			.maxTemperature(getElementText(document, TagName.MAX_TEMP.name()))
			.minTemperature(getElementText(document, TagName.MIN_TEMP.name()))
			.pm25Index(getElementText(document, TagName.PM25_INDEX.name()))
			.pm25(getElementText(document, TagName.PM25.name()))
			.pm10Index(getElementText(document, TagName.PM10_INDEX.name()))
			.pm10(getElementText(document, TagName.PM10.name()))
			.weatherTime(getElementText(document, TagName.WEATHER_TIME.name()))
			.skyStatus(getForCastWeatherText(document, TagName.SKY_STTS.name())) // 24시간 예보 - 하늘상태
			// 문화행사정보
			.culturalEventName(getElementText(document, TagName.EVENT_NM.name()))
			.culturalEventPeriod(getElementText(document, TagName.EVENT_PERIOD.name()))
			.culturalEventPlace(getElementText(document, TagName.EVENT_PLACE.name()))
			.culturalEventUrl(getElementText(document, TagName.URL.name()))
			.build();
		return build;
	}

	private String urlEncoding(String str) throws UnsupportedEncodingException {
		return URLEncoder.encode(str, "UTF-8");
	}

	private String getElementText(Document document, String tag) {
		// return document.getElementsByTagName(tag).item(0).getTextContent();
		// Document 객체에서 태그 이름에 해당하는 요소 가져오기
		NodeList nodeList = document.getElementsByTagName(tag);

		// 가져온 요소가 비어 있는지 확인
		if (nodeList.getLength() > 0) {
			// 첫 번째로 발견된 요소의 텍스트 내용을 반환
			return nodeList.item(0).getTextContent();
		} else {
			// 해당 태그가 없을 경우
			return "태그가 존재하지 않습니다.";
		}
	}

	private String getForeCastPopulationText(Document document, String tag) {
		NodeList nodeList = document.getElementsByTagName("FCST_PPLTN");
		Node fcst_ppltn = nodeList.item(0).getFirstChild();

		if(tag.equals("FCST_TIME")) { // 인구 예측값 - 인구 예측시점 노드 접근하기
			log.info("FCST_TIME: {}", fcst_ppltn.getFirstChild().getTextContent());
			return fcst_ppltn.getFirstChild().getTextContent();
		} else if(tag.equals("FCST_CONGEST_LVL")) { // 인구 예측값 - 장소 예측 혼잡도 지표 노드 접근하기
			log.info("FCST_CONGEST_LVL: {}", fcst_ppltn.getFirstChild().getNextSibling().getTextContent());
			return fcst_ppltn.getFirstChild().getNextSibling().getTextContent();
		}
		return "태그가 존재하지 않습니다.";
	}

	private String getForCastWeatherText(Document document, String tag) {
		NodeList nodeList = document.getElementsByTagName("FCST24HOURS");
		Node fcst_24hours = nodeList.item(0).getFirstChild();
		if (tag.equals("SKY_STTS")) {
			return fcst_24hours.getFirstChild().getLastChild().getTextContent();
		}
		return "태그가 존재하지 않습니다.";

	}
}
