package com.example.springsecurityfirsthw.service;

import com.example.springsecurityfirsthw.dto.UserDto;
import com.example.springsecurityfirsthw.model.User;
import com.example.springsecurityfirsthw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserInfo(Long id) {
        return getUserDto(id, userRepository);
    }

    static UserDto getUserDto(Long id, UserRepository userRepository) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    @Override
    public boolean addUser(UserDto userDto) {
        User userFromRepository = userRepository.findByUsername(userDto.getUsername()).orElse(null);
        if (userFromRepository != null) {
            return false;
        }
        User user = new User();
        user.setUsername(user.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean editUser(Long id, UserDto userDto) {
        User userFromRepository = userRepository.findById(id).orElse(null);
        if (userFromRepository != null) {
            return false;
        }
        userFromRepository.setUsername(userDto.getUsername());
        userFromRepository.setEmail(userDto.getEmail());
        userFromRepository.setPassword(userDto.getPassword());
        userRepository.save(userFromRepository);
        return true;
    }

    @Override
    public boolean deleteUser(Long id) {
        User userFromRepository = userRepository.findById(id).orElse(null);
        if (userFromRepository != null) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
