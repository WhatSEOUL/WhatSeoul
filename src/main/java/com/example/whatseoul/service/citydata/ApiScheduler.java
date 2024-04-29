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
	// @Scheduled(cron = "* * * * * *")
	public void call() {
		List<Area> areas = areaRepository.findAll();
		List<CompletableFuture<CityData>> futures = areas.stream()
			.map(this::fetchCityDataAsync)
			.collect(Collectors.toList());
		List<CityData> cityDataList = futures.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
		cityDataRepository.deleteAll();
		cityDataRepository.saveAll(cityDataList);

	}

	private CompletableFuture<CityData> fetchCityDataAsync(Area area) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				log.info("areaId: {} areaName: {}", area.getAreaId(), area.getAreaName());
				String apiUrl = url + ("/" + urlEncoding(area.getAreaName()));
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(apiUrl);
				return parseCityData(document);
			} catch (SAXException | IOException | ParserConfigurationException e) {
				log.error("error fetching citydata for areaname {}", area.getAreaName(), e);
				return null;
			}
		});
	}

	private CityData parseCityData(Document document) {
		return CityData.builder()
			.areaName(getElementText(document, TagName.AREA_NM.name()))
			.areaCongestionLevel(getElementText(document, TagName.AREA_CONGEST_LVL.name()))
			.areaCongestionMessage(getElementText(document, TagName.AREA_CONGEST_MSG.name()))
			.pplUpdateTime(getElementText(document, TagName.PPLTN_TIME.name()))
			.forecastCongestionLevel(getElementText(document, TagName.FCST_CONGEST_LVL.name()))
			.temperature(getElementText(document, TagName.TEMP.name()))
			.maxTemperature(getElementText(document, TagName.MAX_TEMP.name()))
			.minTemperature(getElementText(document, TagName.MIN_TEMP.name()))
			.pm25Index(getElementText(document, TagName.PM25_INDEX.name()))
			.pm25(getElementText(document, TagName.PM25.name()))
			.pm10Index(getElementText(document, TagName.PM10_INDEX.name()))
			.pm10(getElementText(document, TagName.PM10.name()))
			.weatherTime(getElementText(document, TagName.WEATHER_TIME.name()))
			.skyStatus(getElementText(document, TagName.SKY_STTS.name()))
			.culturalEventName(getElementText(document, TagName.EVENT_NM.name()))
			.culturalEventPeriod(getElementText(document, TagName.EVENT_PERIOD.name()))
			.culturalEventPlace(getElementText(document, TagName.EVENT_PLACE.name()))
			.culturalEventUrl(getElementText(document, TagName.URL.name()))
			.build();
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
}
