package com.woonsheng.chatgpt_ouath2_login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//@Controller
@RestController
public class HomeController {

//    @GetMapping("/")
//    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
//        if (principal != null) {
//            model.addAttribute("name", principal.getAttribute("name"));
//        }
//        return "home";
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Home Page";
    }

    //Principal have the details of login user
    //thymeleaf use to get user details to display in login page
    @RequestMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
