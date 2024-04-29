package com.example.whatseoul.service;

import com.example.whatseoul.entity.CityData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//@RequiredArgsConstructor
@Service
@Slf4j
public class ApiScheduler {

    @Value("${seoul.open.api.url}")
    private String xmlUrl;

    //@Transactional
    @Scheduled(cron = "0 36/1 * * * *")
    public void call() throws Exception {

        String cityUrl = xmlUrl + ("/" + encoding("광화문·덕수궁"));
        log.info(xmlUrl);

        Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(cityUrl);

        log.info(documentInfo.getElementsByTagName("AREA_NM").item(0).toString());
        log.info(documentInfo.getDocumentElement().getNodeName());

        CityData cityData = CityData.builder()
                .areaName(getElement(documentInfo, "AREA_NM"))
                .areaCongestionLevel(getElement(documentInfo, "AREA_CONGEST_LVL"))
                .areaCongestionMessage(getElement(documentInfo, "AREA_CONGEST_MSG"))
                .pplUpdateTime(getElement(documentInfo, "PPLTN_TIME"))
                .forecastPopulation(getElement(documentInfo, "FCST_PPLTN"))
                .forecastCongestionLevel(getElement(documentInfo, "FCST_CONGEST_LVL"))
                .temperature(getElement(documentInfo, "TEMP"))
                .maxTemperature(getElement(documentInfo, "MAX_TEMP"))
                .minTemperature(getElement(documentInfo, "MIN_TEMP"))
                .pm25Index(getElement(documentInfo, "PM25_INDEX"))
                .pm10Index(getElement(documentInfo, "PM10_INDEX"))
                .pcpMsg(getElement(documentInfo, "PCP_MSG"))
                .weatherTime(getElement(documentInfo,"WEATHER_TIME"))
                .culturalEventName(getElement(documentInfo, "EVENT_NAME"))
                .culturalEventPeriod(getElement(documentInfo, "EVENT_PERIOD"))
                .culturalEventPlace(getElement(documentInfo, "EVENT_PLACE"))
                .culturalEventUrl(getElement(documentInfo, "CULTURAL_EVENT_URL"))
                .build();

        log.info(cityData.toString());
    }

    private String encoding(String spot) {
        String result = "";
        try {
            result = URLEncoder.encode(spot, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UnsupportedEncodingException");
        }
        return result;
    }
    private String getElement(Document document, String tag) {
        return document.getElementsByTagName(tag).item(0).getTextContent();
    }
}
