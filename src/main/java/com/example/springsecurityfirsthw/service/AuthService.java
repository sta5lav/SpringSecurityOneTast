package com.example.springsecurityfirsthw.service;

import com.example.springsecurityfirsthw.dto.Register;

public interface AuthService {

    boolean login(String username, String password);

    boolean register(Register register);
}
