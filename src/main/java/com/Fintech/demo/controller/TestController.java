package com.Fintech.demo.controller;

import com.Fintech.demo.entity.User;
import com.Fintech.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    // 데이터 추가
    @PostMapping("/add-user")
    public String addUser(@RequestParam String email, @RequestParam String name) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        userRepository.save(user);
        return "User added successfully!";
    }

    // 데이터 조회
    @GetMapping("/get-users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
}
