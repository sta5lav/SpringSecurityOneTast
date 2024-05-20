package com.example.springsecurityfirsthw.service;

import com.example.springsecurityfirsthw.dto.UserDto;
import com.example.springsecurityfirsthw.model.User;

public interface UserService {

    UserDto getUserInfo(Long id);

    boolean addUser(UserDto userDto);

    boolean editUser(Long id, UserDto userDto);

    boolean deleteUser(Long id);

}
