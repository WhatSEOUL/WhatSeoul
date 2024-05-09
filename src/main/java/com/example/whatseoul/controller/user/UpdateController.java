package com.example.whatseoul.controller.user;

import com.example.whatseoul.entity.User;
import com.example.whatseoul.repository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UpdateController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UpdateController(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/api/user/update")
    public String getMyPage(Model model) {
        // 기존 로직 ...
        return"mypage/update";
    }

//    @GetMapping("/api/user/update")
//    public String getMyUpdatePage(Model model, Authentication authentication) {
//
//        return "update";
//    }


    @PostMapping("/api/user/update")
    public String updateUserInfo(@RequestParam("password") String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setUserPassword(encoder.encode(password));        // 실제 애플리케이션에서는 패스워드를 암호화해야 합니다.
        userRepository.save(user);

        return "redirect:/api/user/me";
    }

    /*@PostMapping("/api/user/deactivate")
    public String deactivateAccount(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setIsActive(false);  // 계정을 비활성화
        userRepository.save(user);
        return "redirect:/login";  // 로그인 페이지로 리디렉션
    }*/

}
