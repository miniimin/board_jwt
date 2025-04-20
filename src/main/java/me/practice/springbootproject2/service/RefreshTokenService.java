package me.practice.springbootproject2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.domain.RefreshToken;
import me.practice.springbootproject2.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveOrUpdate(Long userId, String refreshToken) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByUserId(userId);

        if (optional.isPresent()) {
            RefreshToken token = optional.get();
            token.update(refreshToken);
            refreshTokenRepository.save(token);
        } else {
            RefreshToken newToken = new RefreshToken(userId, refreshToken);
            refreshTokenRepository.save(newToken);
        }
    }

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected Token"));
    }
}
