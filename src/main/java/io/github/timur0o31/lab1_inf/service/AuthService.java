package io.github.timur0o31.lab1_inf.service;

import io.github.timur0o31.lab1_inf.dto.JwtResponse;
import io.github.timur0o31.lab1_inf.dto.UserRequestDto;
import io.github.timur0o31.lab1_inf.entity.User;
import io.github.timur0o31.lab1_inf.repository.UserRepository;
import io.github.timur0o31.lab1_inf.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(UserRequestDto userRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequestDto.username(),
                        userRequestDto.password()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtResponse(jwtUtils.generateToken(userDetails));
    }

    public JwtResponse signUp(UserRequestDto userRequestDto){
        String username = userRequestDto.username();
        String password = userRequestDto.password();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            User newUser = new User();
            newUser.setUsername(username);
            password = passwordEncoder.encode(password);
            newUser.setPassword(password);
            userRepository.save(newUser);
            return login(userRequestDto);
        }
        throw new RuntimeException("user with this username already exists");
    }
}
