package me.practice.springbootproject2.service;

import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.domain.User;
import me.practice.springbootproject2.dto.*;
import me.practice.springbootproject2.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest request) {
        return userRepository.save(User
                .builder()
                .username(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build()).getId();
    }

    public LoginTokenResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾지 못했습니다."));

        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = tokenService.createAccessToken(user);
        String refreshToken = tokenService.createRefreshToken(user);

        return new LoginTokenResponse(accessToken, refreshToken);
    }

    public AccessTokenResponse refresh(RefreshRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return new AccessTokenResponse(newAccessToken);
    }

}
