package project_5headers.com.team_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project_5headers.com.team_project.dto.UserRequestDto;
import project_5headers.com.team_project.dto.UserResponseDto;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto signup(UserRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(requestDto.getEmail());

        userRepository.save(user);

        return new UserResponseDto(user.getUserId(), user.getUsername(), user.getEmail());
    }
}