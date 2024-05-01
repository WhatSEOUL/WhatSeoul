//package com.example.whatseoul.service;
//
//import com.example.whatseoul.dto.CulturalEventDto;
//import com.example.whatseoul.entity.CultureEvent;
//import com.example.whatseoul.respository.CulturalEventRepository;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CulturalEventService {
//
//    private final String apiUrl = "http://openapi.seoul.go.kr:8088/6a4a4b586d30336836344451464d55/xml/citydata/1/5/";
//
//    private CulturalEventRepository culturalEventRepository;
//
//    public void fetchAndSaveCulturalEvents(String areaCode) {
//        // API 호출 및 데이터 파싱
//        String apiResponse = new RestTemplate().getForObject(apiUrl + areaCode, String.class);
//
//        // API 응답 파싱
//        List<CulturalEventDto> culturalEventsDto = parseAPIResponse(apiResponse);
//
//        // DTO를 CulturalEvent 엔티티로 변환하여 저장
//        for (CulturalEventDto eventDTO : culturalEventsDto) {
//            CultureEvent cultureEvent = new CultureEvent();
//            cultureEvent.setCulturalEventName(eventDTO.getName());
//            cultureEvent.setCulturalEventPeriod(eventDTO.getPeriod());
//            cultureEvent.setCulturalEventPlace(eventDTO.getLocation());
//            cultureEvent.setCulturalEventUrl(eventDTO.getDetailUrl());
//            culturalEventRepository.save(cultureEvent);
//        }
//    }
//
//    public List<CulturalEventDto> getAllCulturalEvents() {
//        // 저장된 모든 CulturalEvent 데이터를 DTO로 변환하여 반환
//        List<CultureEvent> culturalEvents = culturalEventRepository.findAll();
//        List<CulturalEventDto> culturalEventsDto = new ArrayList<>();
//
//        for (CultureEvent event : culturalEvents) {
//            CulturalEventDto eventDTO = new CulturalEventDto();
//            eventDTO.setName(event.getCulturalEventName());
//            eventDTO.setPeriod(event.getCulturalEventPeriod());
//            eventDTO.setLocation(event.getCulturalEventPlace());
//            eventDTO.setDetailUrl(event.getCulturalEventUrl());
//            culturalEventsDto.add(eventDTO);
//        }
//
//        return culturalEventsDto;
//    }
//
//    // API 응답을 파싱하여 CulturalEventDTO 객체로 반환하는 메소드
//    private List<CulturalEventDto> parseAPIResponse(String apiResponse) {
//        try {
//            XmlMapper xmlMapper = new XmlMapper();
//            xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//
//            JsonNode jsonNode = xmlMapper.readTree(apiResponse.getBytes(StandardCharsets.UTF_8));
//
//            List<CulturalEventDto> culturalEventsDTO = new ArrayList<>();
//            JsonNode eventsNode = jsonNode.get("EVENT_STTS"); //
//
//            if (eventsNode != null && eventsNode.isArray()) {
//                for (JsonNode eventNode : eventsNode) {
//                    CulturalEventDto eventDTO = new CulturalEventDto();
//                    eventDTO.setName(eventNode.get("EVENT_NM").asText());
//                    eventDTO.setPeriod(eventNode.get("EVENT_PERIOD").asText());
//                    eventDTO.setLocation(eventNode.get("EVENT_PLACE").asText());
//                    eventDTO.setDetailUrl(eventNode.get("URL").asText());
//
//                    culturalEventsDTO.add(eventDTO);
//                }
//            }
//
//            return culturalEventsDTO;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//}
