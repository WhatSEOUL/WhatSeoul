//package com.example.whatseoul.config;
//
//import javax.sql.DataSource;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//@Configuration
//public class WebSecurityConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(
//                authorizeRequests -> authorizeRequests.anyRequest().authenticated())
//            .formLogin(formLogin -> formLogin
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/", true));
//
//        configureAuthentication(http);
//        return http.build();
//    }
//
//    private void configureAuthentication(HttpSecurity http) throws Exception {
//        http.getSharedObject(AuthenticationManagerBuilder.class)
//            .jdbcAuthentication()
//            .dataSource(dataSource)
//            .usersByUsernameQuery("SELECT USER_EMAIL, USER_PASSWORD, true FROM USER_TB WHERE USER_EMAIL = ?")
//            .authoritiesByUsernameQuery("SELECT USER_EMAIL, 'ROLE_USER' FROM USER_TB WHERE USER_EMAIL = ?")
//            .passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        String encodedPassword = passwordEncoder().encode("1234");
//        manager.createUser(User.withUsername("user1").password(encodedPassword).roles("USER").build());
//        return manager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
