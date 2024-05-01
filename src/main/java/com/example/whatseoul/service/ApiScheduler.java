package com.example.whatseoul.service;

import com.example.whatseoul.dto.CityData;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.entity.CultureEvent;
import com.example.whatseoul.entity.Population;
import com.example.whatseoul.entity.Weather;
import com.example.whatseoul.respository.cityData.AreaRepository;
import com.example.whatseoul.respository.cityData.CulturalEventRepository;
import com.example.whatseoul.respository.cityData.PopulationRepository;
import com.example.whatseoul.respository.cityData.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApiScheduler {

    private final AreaRepository areaRepository;
    private final WeatherRepository weatherRepository;
    private final PopulationRepository populationRepository;
    private final CulturalEventRepository culturalEventRepository;

    @Value("${seoul.open.api.url}")
    private String url;


    @Transactional
    @Scheduled(cron = "0 01/5 * * * *")
    public void call() {
        long startTime = System.currentTimeMillis();
        List<Area> areas = areaRepository.findAll();

        List<CompletableFuture<CityData>> allFutures = areas.stream()
                .map(this::fetchDataAsync)
                .toList();

        // CompletableFuture<WeatherAndPopulation>의 결과를 추출하여 Weather만 가져와서 리스트로 변환
        List<Weather> weatherList = allFutures.stream()
                .map(CompletableFuture::join) // CompletableFuture<WeatherAndPopulation>을 blocking하게 변환
                .map(CityData::getWeather) // WeatherAndPopulation에서 Weather를 추출
                .collect(Collectors.toList());

        // CompletableFuture<WeatherAndPopulation>의 결과를 추출하여 Population만 가져와서 리스트로 변환
        List<Population> populationList = allFutures.stream()
                .map(CompletableFuture::join) // CompletableFuture<WeatherAndPopulation>을 blocking하게 변환
                .map(CityData::getPopulation) // WeatherAndPopulation에서 Population을 추출
                .toList();

        List<CultureEvent> cultureEventList = allFutures.stream()
                .map(CompletableFuture::join)
                .map(CityData::getCultureEvent)
                .toList();

        weatherRepository.deleteAllInBatch();
        weatherRepository.saveAll(weatherList);

        populationRepository.deleteAllInBatch();
        populationRepository.saveAll(populationList);

        culturalEventRepository.deleteAllInBatch();
        culturalEventRepository.saveAll(cultureEventList);

        long endTime = System.currentTimeMillis();
        long totalTime = (endTime-startTime)/1000;
        log.info("소요 시간 = " + totalTime);
    }

    private CompletableFuture<CityData> fetchDataAsync(Area area) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("areaId: {} areaName: {}", area.getAreaId(), area.getAreaName());
                String apiUrl = url + ("/" + area.getAreaCode());
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(apiUrl);
                Weather weather= parseWeatherData(document, area);
                Population population = parsePopulationData(document, area);
                CultureEvent cultureEvent = parseCultureEventData(document, area);

                return new CityData(weather, population, cultureEvent);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                log.error("error fetching citydata for areaname {}", area.getAreaName(), e);
                return null;
            }
        });
    }

    /*private CompletableFuture<Population> fetchPopulationDataAsync(Area area) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("areaId: {} areaName: {}", area.getAreaId(), area.getAreaName());
                String apiUrl = url + ("/" + area.getAreaCode());
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(apiUrl);
                return parsePopulationData(document, area);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                log.error("error fetching citydata for areaname {}", area.getAreaName(), e);
                return null;
            }
        });
    }*/

    public Population parsePopulationData(Document document, Area area) {
        return Population.builder()
                .area(area)
                .areaCongestionLevel(getElement(document, "AREA_CONGEST_LVL")) // 장소 혼잡도 지표
                .areaCongestionMessage(getElement(document, "AREA_CONGEST_MSG")) // 장소 혼잡도 지표 관련 메시지
                .pplUpdateTime(getElement(document, "PPLTN_TIME")) // 실시간 인구 데이터 업데이트 시간
                .build();
    }

    public Weather parseWeatherData(Document document, Area area){
        return Weather.builder()
                .temperature(getElement(document, "TEMP"))
                .maxTemperature(getElement(document, "MAX_TEMP"))
                .minTemperature(getElement(document, "MIN_TEMP"))
                .pm25Index(getElement(document, "PM25_INDEX"))
                .pm10Index(getElement(document, "PM10_INDEX"))
                .pcpMsg(getElement(document, "PCP_MSG"))
                .weatherTime(getElement(document, "WEATHER_TIME"))
                .area(area)
                .build();
    }

    public CultureEvent parseCultureEventData(Document document, Area area){
        return CultureEvent.builder()
                .culturalEventName(getElement(document,"EVENT_NM"))
                .culturalEventPeriod(getElement(document, "EVENT_PERIOD"))
                .culturalEventPlace(getElement(document, "EVENT_PLACE"))
                .culturalEventUrl(getElement(document, "URL"))
                .area(area)
                .build();
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