package com.example.whatseoul.service;

import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.Weather;
import com.example.whatseoul.respository.AreaRepository;
import com.example.whatseoul.respository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApiScheduler {

    private final AreaRepository areaRepository;
    private final WeatherRepository weatherRepository;

    @Value("${seoul.open.api.url}")
    private String xmlUrl;

    @Transactional
    @Scheduled(cron = "0 47/5 * * * *")
    public void call() throws Exception {

        List<Area> areas = areaRepository.findAll();
        List<Weather> weatherList = new ArrayList<>();
        long beforeTime= System.currentTimeMillis();
        for (Area area : areas) {

            String cityUrl = xmlUrl + ("/" + area.getAreaCode());
            Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(cityUrl);
            documentInfo.getDocumentElement().normalize();

            Weather weather = Weather.builder()
                    .temperature(getElement(documentInfo, "TEMP"))
                    .maxTemperature(getElement(documentInfo, "MAX_TEMP"))
                    .minTemperature(getElement(documentInfo, "MIN_TEMP"))
                    .pm25Index(getElement(documentInfo, "PM25_INDEX"))
                    .pm10Index(getElement(documentInfo, "PM10_INDEX"))
                    .pcpMsg(getElement(documentInfo, "PCP_MSG"))
                    .weatherTime(getElement(documentInfo, "WEATHER_TIME"))
                    .build();
            weatherList.add(weather);
            weatherRepository.saveAll(weatherList);
        }
        long afterTime= System.currentTimeMillis();
        long totalTime = (afterTime-beforeTime)/1000;
        log.info("소요시간 =" + totalTime);

    }

    private String getElement(Document document, String tag) {
        // Document 객체에서 태그 이름에 해당하는 요소 가져오기
        NodeList nodeList = document.getElementsByTagName(tag);

        // 가져온 요소가 비어 있는지 확인
        if (nodeList.getLength() > 0) {
            // 첫 번째로 발견된 요소의 텍스트 내용 반환
            return nodeList.item(0).getTextContent();
        } else return "No Tag";
    }
}
