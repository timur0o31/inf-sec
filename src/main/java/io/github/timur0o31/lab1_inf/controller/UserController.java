package io.github.timur0o31.lab1_inf.controller;


import io.github.timur0o31.lab1_inf.dto.UserResponseDto;
import io.github.timur0o31.lab1_inf.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/data")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

}
