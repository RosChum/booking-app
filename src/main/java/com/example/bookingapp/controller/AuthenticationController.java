package com.example.bookingapp.controller;

import com.example.bookingapp.dto.authentication.AuthDto;
import com.example.bookingapp.dto.authentication.AuthenticationResponseDto;
import com.example.bookingapp.dto.authentication.RefreshTokenRequest;
import com.example.bookingapp.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseUrl.BASE_URL + "auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final SecurityService securityService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> authentication(@RequestBody AuthDto authDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(securityService.authentication(authDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestBody RefreshTokenRequest tokenRequest){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(securityService.refreshToken(tokenRequest));
    }


}
