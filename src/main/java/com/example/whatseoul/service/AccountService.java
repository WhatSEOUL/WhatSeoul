package com.example.whatseoul.service;

import com.example.whatseoul.dto.response.UserResponseDto;
import com.example.whatseoul.entity.User;
import com.example.whatseoul.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User account = userRepository.findByUserName(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(account.getUserEmail(),
            account.getUserPassword(), authorities);
    }

    @Transactional
    public UserResponseDto join(String userEmail, String userPwd, String userName) {
        boolean checkEmail = existsByUserEmail(userEmail);
        boolean checkUserName = existsByUserName(userName);

        if(checkEmail || checkUserName) throw new RuntimeException("이메일 또는 유저네임 중복 발생");

        User newUser = new User();
        newUser.setUserEmail(userEmail);
        newUser.setUserPassword(encoder.encode(userPwd));
        newUser.setUserName(userName);
        newUser.setActive(true);
        userRepository.save(newUser);

        return UserResponseDto.from(newUser);
    }

    @Transactional
    public void updatePw(String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setUserPassword(encoder.encode(password));
        userRepository.save(user);
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByUserEmail(String userEmail) {
        return userRepository.existsByUserEmail(userEmail);
    }

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        return user.getUserId();
    }
}
