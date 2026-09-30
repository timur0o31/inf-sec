package io.github.timur0o31.lab1_inf.service;

import io.github.timur0o31.lab1_inf.dto.UserResponseDto;
import io.github.timur0o31.lab1_inf.entity.User;
import io.github.timur0o31.lab1_inf.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> ans = new ArrayList<>();
        for (User user : users){
            UserResponseDto dto = new UserResponseDto(
                    user.getId(),
                    HtmlUtils.htmlEscape(user.getUsername()));
            ans.add(dto);
        }
        return ans;
    }
}
