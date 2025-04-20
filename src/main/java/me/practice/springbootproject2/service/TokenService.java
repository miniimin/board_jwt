package me.practice.springbootproject2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.config.jwt.TokenProvider;
import me.practice.springbootproject2.domain.User;
import me.practice.springbootproject2.repository.RefreshTokenRepository;
import me.practice.springbootproject2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected Token");
        }

        Long userId = refreshTokenService
                .findByRefreshToken(refreshToken)
                .getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다."));;

        return tokenProvider.generateToken(user, Duration.ofMinutes(2));
    }

    public String createAccessToken(User user) {
        return tokenProvider.generateToken(user, Duration.ofMinutes(2));
    }

    public String createRefreshToken(User user) {
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(7)); // 7일짜리 RefreshToken
        refreshTokenService.saveOrUpdate(user.getId(), refreshToken); // DB에 저장
        return refreshToken;
    }
}
