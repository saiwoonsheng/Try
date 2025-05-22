package com.woonsheng.chatgpt_teach_me_02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {}) // allow CORS
                .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // 👈 ALLOW ALL under /auth/
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors(Customizer.withDefaults())
//                .csrf(Customizer.withDefaults())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/auth/**", "/oauth2/**").permitAll()
//                .anyRequest().authenticated()
//            )
//            .formLogin(form -> form
//                .loginPage("/auth/login")
//                .permitAll()
//            )
//            .oauth2Login(oauth2 -> oauth2
//                .loginPage("/auth/login")
//            )
//                .httpBasic(httpBasic -> httpBasic.disable());
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}