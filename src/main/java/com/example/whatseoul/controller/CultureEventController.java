package com.example.whatseoul.controller;

import com.example.whatseoul.dto.response.CultureEventDto;
import com.example.whatseoul.entity.Area;
import com.example.whatseoul.respository.cityData.AreaRepository;
import com.example.whatseoul.service.CultureEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class CultureEventController {
//    private final AreaRepository areaRepository;
//    private final CultureEventService cultureEventService;
//
//    @GetMapping("/culture-event/{areaCode}")
//    public ResponseEntity<List<CultureEventDto>> getCultureEventData(@PathVariable String areaCode) {
//        Area area = areaRepository.findAreaByAreaCode(areaCode)
//                .orElseThrow(() -> new NoSuchElementException("Area not Found"));
//        List<CultureEventDto> dataDto = cultureEventService.getCultureEventData(area.getAreaCode());
//        return ResponseEntity.ok(dataDto);
//    }
//}
//

@Controller
@RequestMapping("/culture-events")
@RequiredArgsConstructor
public class CultureEventController {
    private final AreaRepository areaRepository;
    private final CultureEventService cultureEventService;

    @GetMapping("/{areaCode}")
    public String getCultureEventsByArea(@PathVariable String areaCode, Model model) {
        Area area = areaRepository.findAreaByAreaCode(areaCode)
                .orElseThrow(() -> new NoSuchElementException("Area not Found"));
        List<CultureEventDto> cultureEventDtos = cultureEventService.getCultureEventData(area.getAreaCode());
        if (cultureEventDtos.isEmpty()) {
            model.addAttribute("message", "해당 지역에 문화 행사 정보가 없습니다.");
        } else {
            model.addAttribute("cultureEvents", cultureEventDtos);
        }
        return "events";
    }
}

