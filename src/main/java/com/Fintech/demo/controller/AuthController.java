package com.Fintech.demo.controller;

import com.Fintech.demo.entity.User;
import com.Fintech.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String name) {
        User user = userService.registerUser(email, password, name);
        return "User registered successfully: " + user.getEmail(); //이메일 조회 확인
    }
}
