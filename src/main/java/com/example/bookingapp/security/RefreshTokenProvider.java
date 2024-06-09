package com.example.bookingapp.security;

import com.example.bookingapp.entity.RefreshToken;
import com.example.bookingapp.exception.RefreshTokenException;
import com.example.bookingapp.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenProvider {

    @Value("${app.jwt.expireRefreshToken}")
    private Duration expireRefreshToken;

    public final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createrefreshToken(Long userId, String userEmail) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserId(userId);
        refreshToken.setExpireDate(Instant.now().plusMillis(expireRefreshToken.toMillis()));
        refreshToken.setUserEmail(userEmail);
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken getRefreshTokenByToken(String token) {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenException(MessageFormat.format("Refresh token {0} not found", token)));
    }

    public boolean checkExpireRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.getExpireDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            return false;
        }
        return true;
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

}
