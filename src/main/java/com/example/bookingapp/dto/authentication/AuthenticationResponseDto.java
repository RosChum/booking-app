package com.example.bookingapp.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "ДТО для ответа авторизации")
public class AuthenticationResponseDto {

    private Long userId;

    private String accessToken;

    private String refreshToken;


}
