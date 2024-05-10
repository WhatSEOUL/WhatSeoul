package com.example.whatseoul.controller.account;

import com.example.whatseoul.dto.response.UserResponseDto;
import com.example.whatseoul.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/join")
    public ResponseEntity<UserResponseDto> join(String email, String password, String name) {
        return ResponseEntity.ok(accountService.join(email, password, name));
    }

    @GetMapping("/api/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @GetMapping("update")
    public String update() {
        return "/user/update";
    }

    @PostMapping("/api/update")
    public String updateUserInfo(String password) {
        accountService.updatePw(password);
        return "redirect:/";
    }

    @PostMapping("withdraw")
    public String withDraw(){
        return "/user/withdraw";
    }

    @GetMapping("/api/check/username")
    public ResponseEntity<String> checkDuplicateUserName(@RequestParam("name") String userName) {
        if (accountService.existsByUserName(userName)) {
            return ResponseEntity.badRequest().body("이미 사용중인 유저네임입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 유저네임입니다.");
        }
    }

    @GetMapping("/api/check/email")
    public ResponseEntity<String> checkDuplicatUserEmail(@RequestParam("email") String userEmail) {
        if (accountService.existsByUserEmail(userEmail)) {
            return ResponseEntity.badRequest().body("이미 사용중인 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }
}
