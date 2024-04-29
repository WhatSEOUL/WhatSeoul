package com.example.whatseoul.service.citydata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.CityData;
import com.example.whatseoul.repository.area.AreaRepository;
import com.example.whatseoul.repository.citydata.CityDataRepository;
import com.example.whatseoul.util.TagName;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApiScheduler {

	private final AreaRepository areaRepository;
	private final CityDataRepository cityDataRepository;

	@Value("${seoul.open.api.url}")
	private StringBuilder url;

	//@Transactional
	// @Scheduled(cron = "* * * * * *")
	public void call() throws ParserConfigurationException, IOException, SAXException {
		List<Area> areas = areaRepository.findAll();
		List<CityData> cityDataList = new ArrayList<>();

		for (int i = 0; i < areas.size(); i++) {
			String apiUrl = url + "/" + urlEncoding(areas.get(i).getAreaName());
			Document document;
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(apiUrl);
			CityData cityData = CityData.builder()
				.areaName(getElementText(document, TagName.AREA_NM.name()))
				.areaCongestionLevel(getElementText(document, TagName.AREA_CONGEST_LVL.name()))
				.areaCongestionMessage(getElementText(document, TagName.AREA_CONGEST_MSG.name()))
				.pplUpdateTime(getElementText(document, TagName.PPLTN_TIME.name()))
				.forecastPopulation(getElementText(document, TagName.FCST_PPLTN.name()))
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
				.culturalEventName(getElementText(document, TagName.EVENT_NAME.name()))
				.culturalEventPeriod(getElementText(document, TagName.EVENT_PERIOD.name()))
				.culturalEventPlace(getElementText(document, TagName.EVENT_PLACE.name()))
				.culturalEventUrl(getElementText(document, TagName.CULTURAL_EVENT_URL.name()))
				.build();
			cityDataList.add(cityData);
		}
		cityDataRepository.saveAll(cityDataList);
	}

	private String urlEncoding(String str) throws UnsupportedEncodingException {
		String result = "";
		result = URLEncoder.encode(str, "UTF-8");
		return result;
	}

	private String getElementText(Document document, String tag) {
		Document documentInfo = document;
		String result = documentInfo.getElementsByTagName(tag).item(0).getTextContent();
		return result;
	}
}
