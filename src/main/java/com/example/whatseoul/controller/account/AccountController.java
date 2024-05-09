package com.example.whatseoul.controller.account;

import com.example.whatseoul.service.account.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("login")
    public String login() {

        return "login";  // 로그인 페이지를 반환
    }

    @GetMapping("register")
    public String register() {
        return "register";  // 회원가입 페이지를 반환
    }

    @PostMapping("join")
    public String join(String email, String password, String name) {
        // 회원가입 로직 처리
        // 성공하면 로그인 페이지로 리디렉트

        boolean join = accountService.join(email, password, name);
        return "redirect:/login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
            SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @GetMapping("check/username")
    public ResponseEntity<String> checkDuplicateUserName(@RequestParam("name") String userName) {
        if (accountService.existsByUserName(userName)) {
            return ResponseEntity.badRequest().body("이미 사용중인 유저네임입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 유저네임입니다.");
        }
    }

    @GetMapping("check/email")
    public ResponseEntity<String> checkDuplicatUserEmail(@RequestParam("email") String userEmail) {
        if (accountService.existsByUserEmail(userEmail)) {
            return ResponseEntity.badRequest().body("이미 사용중인 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }
}
