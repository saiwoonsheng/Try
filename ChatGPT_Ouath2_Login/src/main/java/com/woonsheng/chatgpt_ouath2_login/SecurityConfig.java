package com.woonsheng.chatgpt_ouath2_login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain myOwnFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/", "/login", "/images/**").permitAll()
//                .anyRequest().authenticated()
//            )
//            .oauth2Login(oauth2 -> oauth2
//                .loginPage("/login")
//            );
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers("/","/login").permitAll()
                .anyRequest().authenticated())
                //if login successful directly to Profile page
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login") //create in viewController for this getMapping
                                .successHandler((request, response, authentication) ->
                        response.sendRedirect("/profile")))
                //.oauth2Login(Customizer.withDefaults()) //auto give us localhost:8080/login
                //.formLogin(Customizer.withDefaults())
                //ในกรณีที่รูป profile ไม่แสดง ต้องเพิ่ม code นี้
                .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'; " +
                                "img-src * data:; " +
                                "script-src 'self'; " +
                                "style-src 'self' 'unsafe-inline'")))
                ;

        return http.build();
    }

}
