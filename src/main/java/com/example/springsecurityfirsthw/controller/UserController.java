package com.example.springsecurityfirsthw.controller;

import com.example.springsecurityfirsthw.dto.UserDto;
import com.example.springsecurityfirsthw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo(id));

    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(
                userService.addUser(userDto) ?
                        HttpStatus.CREATED:
                        HttpStatus.BAD_REQUEST
        ).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id,
                                      @RequestBody UserDto userDto) {
        return ResponseEntity.status(
                userService.editUser(id, userDto) ?
                        HttpStatus.OK:
                        HttpStatus.NOT_FOUND
        ).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(
                userService.deleteUser(id) ?
                        HttpStatus.OK:
                        HttpStatus.NOT_FOUND
        ).build();
    }

}
