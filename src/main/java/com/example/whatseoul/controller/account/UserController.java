package com.example.whatseoul.controller.account;

import com.example.whatseoul.dto.ResponseDto;
import com.example.whatseoul.dto.UserDto;
import com.example.whatseoul.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AccountService accountService;

    @GetMapping("login")
    public String login() {

        return "login";  // 로그인 페이지를 반환
    }

    @GetMapping("register")
    public String register() {
        return "user/register";  // 회원가입 페이지를 반환
    }

    @PostMapping("/api/join")
    public ResponseEntity<?> join(String email, String password, String name, Model model) {
        try {
            accountService.join(email, password, name);
            return ResponseEntity.ok("success");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/api/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @GetMapping("update")
    public String update() {
        return "user/update";
    }

    @PostMapping("/api/update")
    @ResponseBody
    public ResponseEntity<?> updateUserInfo(@RequestBody UserDto userDto) {
        try {
            accountService.updatePw(userDto.getUserPassword());
            return ResponseEntity.ok().body(ResponseDto.builder().code(1).message("success").build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDto.builder().code(-1).message(e.getMessage())
                    .errorCode(HttpStatus.BAD_REQUEST.toString()).build());
        }
    }

    @PostMapping("/api/check/email")
    @ResponseBody
    public ResponseEntity<?> checkDuplicateUserEmail(@RequestBody UserDto userDto) {
        if (accountService.existsByUserEmail(userDto.getUserEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseDto.builder().code(-1).message("unavailable").build());
        } else {
            return ResponseEntity.ok(ResponseDto.builder().code(-1).message("available").build());
        }
    }

    @PostMapping("/api/check/username")
    @ResponseBody
    public ResponseEntity<?> checkDuplicateUserName(@RequestBody UserDto userDto) {
        if (accountService.existsByUserName(userDto.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseDto.builder().code(-1).message("unavailable").build());
        } else {
            return ResponseEntity.ok(ResponseDto.builder().code(-1).message("available").build());
        }
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("errorMessage", "아이디 또는 비밀번호를 확인해주세요!");
        return "login";
    }
}

