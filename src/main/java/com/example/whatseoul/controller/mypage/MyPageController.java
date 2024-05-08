package com.example.whatseoul.controller.mypage;

import com.example.whatseoul.entity.User;
import com.example.whatseoul.respository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Optional;

@Controller
public class MyPageController {

    private final UserRepository userRepository;

    public MyPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/user/me")
    public String getMyPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername(); // 여기서는 사용자의 이메일이 사용자명으로 사용됩니다.
            Optional<User> user = userRepository.findByUserEmail(username);

            if (user.isPresent()) {
                model.addAttribute("userName", user.get().getUserName());
                model.addAttribute("userEmail", user.get().getUserEmail());
            } else {
                // 적절한 예외 처리 또는 로깅
                return "error"; // 예를 들어 사용자 정보가 없는 경우 에러 페이지로 리디렉트
            }
        } else {
            // 사용자가 인증되지 않은 경우
            return "redirect:/login";
        }

        return "mypage/me"; // 마이 페이지 뷰 반환
    }
}
