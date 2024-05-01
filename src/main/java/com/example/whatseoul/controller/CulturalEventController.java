//package com.example.whatseoul.controller;
//
//import com.example.whatseoul.service.CulturalEventService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@Controller
//public class CulturalEventController {
//
//    @Autowired
//    private CulturalEventService culturalEventService;
//
//    @GetMapping("/area/{areaCode}")
//    public String fetchAndSaveCulturalEvents(@PathVariable String areaCode) {
//        culturalEventService.fetchAndSaveCulturalEvents(areaCode);
//        return "redirect:/";
//    }
//
//    @GetMapping("/area")
//    public String getAllCulturalEvents(Model model) {
//        model.addAttribute("culturalEvents", culturalEventService.getAllCulturalEvents());
//        return "events";
//    }
//}
