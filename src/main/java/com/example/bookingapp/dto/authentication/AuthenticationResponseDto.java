package com.example.bookingapp.dto.authentication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDto {

    private Long userId;

    private String accessToken;

    private String refreshToken;


}
