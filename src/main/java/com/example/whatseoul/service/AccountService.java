//package com.example.whatseoul.service;
//
//import com.example.whatseoul.entity.User;
//import com.example.whatseoul.respository.user.UserRepository;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class AccountService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder encoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User account = userRepository.findByUserName(username)
//            .orElseThrow(
//                () -> new UsernameNotFoundException("User not found with username: " + username));
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        // Example: Adding a dummy authority, real implementations might fetch this from the database or configuration
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        return new org.springframework.security.core.userdetails.User(account.getUserEmail(),
//            account.getUserPassword(), authorities);
//    }
//
//    @Transactional
//    public boolean join(String userEmail, String userPwd, String userName) {
//        if (userRepository.findByUserEmail(userEmail).isPresent()) {
//            return false;
//        }
//        User newUser = new User();
//        newUser.setUserEmail(userEmail);
//        newUser.setUserPassword(encoder.encode(userPwd));
//        newUser.setUserName(userName);
//        userRepository.save(newUser);
//        return true;
//    }
//}
