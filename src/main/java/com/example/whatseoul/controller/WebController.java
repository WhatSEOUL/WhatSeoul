//package com.example.whatseoul.controller;
//
//import com.example.whatseoul.service.AccountService;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/")
//@RequiredArgsConstructor
//public class WebController {
//
//    private final AccountService accountService;
//
//    @GetMapping("login")
//    public String login() {
//
//        return "login";  // 로그인 페이지를 반환
//    }
//
//    @GetMapping("register")
//    public String register() {
//        return "register";  // 회원가입 페이지를 반환
//    }
//
//    @PostMapping("join")
//    public String join(String email, String password, String name) {
//        // 회원가입 로직 처리
//        // 성공하면 로그인 페이지로 리디렉트
//
//        boolean join = accountService.join(email, password, name);
//        return "redirect:/login";
//    }
//}
