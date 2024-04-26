package com.example.bookingapp.controller;

import com.example.bookingapp.entity.RefreshToken;
import com.example.bookingapp.security.RefreshTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BaseUrl.BASE_URL + "refresh-token")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<RefreshToken> get(){
        return ResponseEntity.ok(tokenProvider.createrefreshToken(1L));
    }

    @GetMapping
    public ResponseEntity<RefreshToken> get(@RequestBody String token){
        return ResponseEntity.ok(tokenProvider.getRefreshTokenByToken(token));
    }


}
