package com.woonsheng.chatgpt_ouath2_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableWebSecurity
public class ChatGptOuath2LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatGptOuath2LoginApplication.class, args);
    }

}
