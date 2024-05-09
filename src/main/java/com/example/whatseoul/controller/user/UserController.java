package com.example.whatseoul.controller.user;

import com.example.whatseoul.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final AccountService accountService;

    @GetMapping("login")
    public String login() {

        return "login";  // 로그인 페이지를 반환
    }

    @GetMapping("register")
    public String register() {
        return "/user/register";  // 회원가입 페이지를 반환
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
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @PostMapping("update")
    public String updateUserInfo(String password) {
        accountService.updatePw(password);
        return "redirect:/mypage/me";
    }
}
