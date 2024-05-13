package com.example.whatseoul.service;

import com.example.whatseoul.dto.CityData;
import com.example.whatseoul.entity.*;
import com.example.whatseoul.repository.cityData.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
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
    private final PopulationForecastRepository populationForecastRepository;
    private final CulturalEventRepository culturalEventRepository;

    @Value("${seoul.open.api.url}")
    private String url;


    @Transactional
//    @Scheduled(cron = "0 18/5 * * * *")
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

        List<PopulationForecast> pplForecastList = allFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(cityData -> cityData.getPplForecast().stream())
                .collect(Collectors.toList());

        List<CultureEvent> cultureEventList = allFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(cityData -> cityData.getCultureEvent().stream())
                .collect(Collectors.toList());

        weatherRepository.deleteAllInBatch();
        weatherRepository.saveAll(weatherList);

        populationForecastRepository.deleteAllInBatch(); // 인구예측데이터를 먼저 삭제한 이후 인구 데이터 삭제
        populationRepository.deleteAllInBatch();
        populationRepository.saveAll(populationList); // 인구데이터를 먼저 저장한 후 인구 예측 데이터 저장
        populationForecastRepository.saveAll(pplForecastList);

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
                List<PopulationForecast> pplForecast = parsePopulationForecastData(document, population);
                List<CultureEvent> cultureEvent = parseCultureEventData(document, area);

                return new CityData(weather, population, cultureEvent, pplForecast);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                log.error("error fetching citydata for areaname {}", area.getAreaName(), e);
                return null;
            }
        });
    }

    public Population parsePopulationData(Document document, Area area) {
        return Population.builder()
                .area(area)
                .areaCongestionLevel(getElement(document, "AREA_CONGEST_LVL")) // 장소 혼잡도 지표
                .areaCongestionMessage(getElement(document, "AREA_CONGEST_MSG")) // 장소 혼잡도 지표 관련 메시지
                .pplUpdateTime(getElement(document, "PPLTN_TIME")) // 실시간 인구 데이터 업데이트 시간
                .build();
    }

    public List<PopulationForecast> parsePopulationForecastData(Document document, Population population) {
        List<PopulationForecast> pplForecastList = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("FCST_PPLTN"); // 13개 노드가 담긴 리스트 반환, 0번 노드는 부모 노드
        for (int i = 1; i < nodeList.getLength(); i++) {
            Node fcstPpltnNode = nodeList.item(i);
            NodeList childNodes = fcstPpltnNode.getChildNodes();

            String forecastTime = "No Tag";
            String forecastCongestionLevel = "No Tag";
            String forecastPopulationMin = "No Tag";
            String forecastPopulationMax = "No Tag";

            for (int j = 0; j < childNodes.getLength(); j++) {
                Node childNode = childNodes.item(j);
                if (childNode.getNodeName().equals("FCST_TIME")) {
                    forecastTime = childNode.getTextContent();
                } else if (childNode.getNodeName().equals("FCST_CONGEST_LVL")) {
                    forecastCongestionLevel = childNode.getTextContent();
                } else if (childNode.getNodeName().equals("FCST_PPLTN_MIN")) {
                    forecastPopulationMin = childNode.getTextContent();
                } else if (childNode.getNodeName().equals("FCST_PPLTN_MAX")) {
                    forecastPopulationMax = childNode.getTextContent();
                }
            }
            PopulationForecast pplForecast = PopulationForecast.builder()
                .population(population)
                .forecastTime(forecastTime)
                .forecastCongestionLevel(forecastCongestionLevel)
                .forecastPopulationMin(forecastPopulationMin)
                .forecastPopulationMax(forecastPopulationMax)
                .build();

            pplForecastList.add(pplForecast);

        }
        return pplForecastList;
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


        public List<CultureEvent> parseCultureEventData(Document document, Area area) {
        List<CultureEvent> cultureEvents = new ArrayList<>();
        NodeList eventNodes = document.getElementsByTagName("EVENT_STTS");

        for (int i = 1; i < eventNodes.getLength(); i++) {
            Node eventNode = eventNodes.item(i);
            if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eventElement = (Element) eventNode;
                String eventName = getElement(eventElement, "EVENT_NM");
                String eventPeriod = getElement(eventElement, "EVENT_PERIOD");
                String eventPlace = getElement(eventElement, "EVENT_PLACE");
                String eventUrl = getElement(eventElement, "URL");

                CultureEvent cultureEvent = CultureEvent.builder()
                        .culturalEventName(eventName)
                        .culturalEventPeriod(eventPeriod)
                        .culturalEventPlace(eventPlace)
                        .culturalEventUrl(eventUrl)
                        .area(area)
                        .build();

                cultureEvents.add(cultureEvent);
            }
        }

        return cultureEvents;
    }

        private String getElement(Element element, String tag) {
        NodeList nodeList = element.getElementsByTagName(tag);

        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else return "No Tag";
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

    // 향후 사용할 일이 없어보이면 삭제하겠습니다!
    // private String getPplForecastText(Document document, String tag) {
    //     NodeList nodeList = document.getElementsByTagName("FCST_PPLTN"); // 13개 노드가 담긴 리스트 반환, 0번 노드는 부모 노드
    //     for (int i = 1; i < nodeList.getLength(); i++) {
    //         Node fcstPpltnNode = nodeList.item(i);
    //         NodeList childNodes = fcstPpltnNode.getChildNodes();
    //
    //         for (int j = 0; j < childNodes.getLength(); j++) {
    //             Node childNode = childNodes.item(j);
    //             if (childNode.getNodeName().equals(tag)) {
    //                 return childNode.getTextContent();
    //             }
    //         }
    //         // if (tag.equals("FCST_TIME")) {
    //         //     return fcstPpltnNode.getFirstChild().getFirstChild().getTextContent();
    //         // } else if (tag.equals("FCST_CONGEST_LVL")) {
    //         //     return fcstPpltnNode.getFirstChild().getFirstChild().getNextSibling().getTextContent();
    //         // }
    //     }
    //     return "No Tag";
    // }
}