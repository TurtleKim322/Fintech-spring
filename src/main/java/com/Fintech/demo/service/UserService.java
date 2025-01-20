package com.Fintech.demo.service;

import com.Fintech.demo.entity.Role;
import com.Fintech.demo.entity.User;
import com.Fintech.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // BCryptPasswordEncoder가 자동 주입됨

    public User registerUser(String email, String password, String name) {
        String encodedPassword = passwordEncoder.encode(password); // 암호화 적용
        User user = new User(email, encodedPassword, name, Role.ROLE_USER);
        return userRepository.save(user);
    }
}
