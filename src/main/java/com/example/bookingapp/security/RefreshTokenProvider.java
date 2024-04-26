package com.example.bookingapp.security;

import com.example.bookingapp.entity.RefreshToken;
import com.example.bookingapp.exception.RefreshTokenException;
import com.example.bookingapp.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider {

    @Value("${app.jwt.expireRefreshToken}")
    private Duration expireRefreshToken;

    public final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createrefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUserId(userId);
        refreshToken.setExpireDate(Instant.now().plusMillis(expireRefreshToken.toMillis()));
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken getRefreshTokenByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RefreshTokenException(MessageFormat.format("Refresh token {0} not found", token)));
    }

    public RefreshToken checkExpireRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.getExpireDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException(MessageFormat.format("Refresh token {0} is expired", refreshToken.getRefreshToken()));
        }
        return refreshToken;
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

}
