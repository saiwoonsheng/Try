package com.woonsheng.chatgpt_teach_me_02.controller;

import com.woonsheng.chatgpt_teach_me_02.entity.LoginRequest;
import com.woonsheng.chatgpt_teach_me_02.entity.myuser;
import com.woonsheng.chatgpt_teach_me_02.repository.UserRepository;
import com.woonsheng.chatgpt_teach_me_02.service.EmailService;
import com.woonsheng.chatgpt_teach_me_02.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody myuser user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Email already in use");
            System.out.println("Error is : "+ error);
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Set.of("ROLE_USER"));
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<myuser> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            System.out.println("Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        myuser user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        String token = jwtService.generateToken(userOptional.get());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        System.out.println("Token : "+ token + " & Login successful");
        return ResponseEntity.ok(response);

        // Optionally return user details or token
        //return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        System.out.println("forgotPassword is working...");
        Optional<myuser> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            myuser user = optionalUser.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));
            userRepository.save(user);
            emailService.sendResetPasswordEmail(user.getEmail(), token);
            return ResponseEntity.ok("Password reset email sent");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        System.out.println("resetPassword is working...");
        Optional<myuser> optionalUser = userRepository.findByResetToken(token);
        if (optionalUser.isPresent()) {
            myuser user = optionalUser.get();
            if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            user.setTokenExpiry(null);
            userRepository.save(user);
            return ResponseEntity.ok("Password reset successful");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
    }
}