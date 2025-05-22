package com.woonsheng.chatgpt_ouath2_login;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
public class ViewController {

    @GetMapping("/profile")
    public String userProfile(Model model, @AuthenticationPrincipal OAuth2User principal) {
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("email", principal.getAttribute("email"));
        model.addAttribute("photo", principal.getAttribute("picture"));
        System.out.println("Photo URL: " + principal.getAttribute("picture"));
        return "user-profile";
    }

    @GetMapping("/login")
    public String loign() {
        return "custom-login";
    }

    @GetMapping("/user-photo")
    @ResponseBody
    public ResponseEntity<byte[]> getUserPhoto(@AuthenticationPrincipal OAuth2User principal) {
        String imageUrl = principal.getAttribute("picture");
        try {
            byte[] imageBytes = new RestTemplate().getForObject(imageUrl, byte[].class);
            System.out.println("Image URL: " + imageUrl + " \n Image Bytes : " + imageBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // or detect type dynamically
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
