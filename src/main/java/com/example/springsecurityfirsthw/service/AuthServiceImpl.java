package com.example.springsecurityfirsthw.service;

import com.example.springsecurityfirsthw.dto.Register;
import com.example.springsecurityfirsthw.model.JwtAuthenticationToken;
import com.example.springsecurityfirsthw.model.User;
import com.example.springsecurityfirsthw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;


    @Override
    public boolean login(String username, String password) {
        if (userRepository.findByUsername(username).orElse(null) == null) {
            return false;
        }
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        return encoder.matches(password, principal.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (userRepository.findByUsername(register.getUsername()).orElse(null) != null) {
            return false;
        }
        User user = new User();
        user.setUsername(register.getUsername());
        user.setPassword(encoder.encode(register.getPassword()));
        userRepository.save(user);
        return true;
    }
}
