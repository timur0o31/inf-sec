package io.github.timur0o31.lab1_inf.controller;

import io.github.timur0o31.lab1_inf.dto.JwtResponse;
import io.github.timur0o31.lab1_inf.dto.UserRequestDto;
import io.github.timur0o31.lab1_inf.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Validated UserRequestDto request){
        return ResponseEntity.ok().body(authService.login(request));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<JwtResponse> register(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok().body(authService.signUp(userRequestDto));
    }
}
