package com.example.bookingapp.security;

import com.example.bookingapp.dto.authentication.AuthDto;
import com.example.bookingapp.dto.authentication.AuthenticationResponseDto;
import com.example.bookingapp.dto.authentication.RefreshTokenRequest;
import com.example.bookingapp.entity.RefreshToken;
import com.example.bookingapp.exception.RefreshTokenException;
import com.example.bookingapp.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationResponseDto authentication(AuthDto authDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtProvider.generateAccessToken(userDetails));
        authenticationResponseDto.setRefreshToken(refreshTokenProvider.createrefreshToken(userDetails.getUserId(), userDetails.getUsername()).getToken());
        authenticationResponseDto.setUserId(userDetails.getUserId());

        return authenticationResponseDto;
    }


    public AuthenticationResponseDto refreshToken(RefreshTokenRequest tokenRequest) {

        RefreshToken existRefreshToken = refreshTokenProvider.getRefreshTokenByToken(tokenRequest.getToken());

        if (existRefreshToken != null && refreshTokenProvider.checkExpireRefreshToken(existRefreshToken)) {
            String newAccessToken = jwtProvider.generateAccessToken((AppUserDetails) userDetailsService.loadUserByUsername(existRefreshToken.getUserEmail()));
            RefreshToken newRefreshToken = refreshTokenProvider.createrefreshToken(existRefreshToken.getUserId(), existRefreshToken.getUserEmail());
            return AuthenticationResponseDto.builder().accessToken(newAccessToken).refreshToken(newRefreshToken.getToken()).userId(newRefreshToken.getUserId()).build();
        }
        throw new RefreshTokenException(MessageFormat.format("Refresh Token: {0}  invalid", tokenRequest.getToken()));

    }

    public void logout() {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        refreshTokenProvider.deleteByUserId(userDetails.getUserId());
    }

    public static Long getAuthenticationUserId() {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUserId();
    }



}
