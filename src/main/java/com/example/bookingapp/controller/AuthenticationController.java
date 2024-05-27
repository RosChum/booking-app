package com.example.bookingapp.controller;

import com.example.bookingapp.dto.authentication.AuthDto;
import com.example.bookingapp.dto.authentication.AuthenticationResponseDto;
import com.example.bookingapp.dto.authentication.RefreshTokenRequest;
import com.example.bookingapp.security.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseUrl.BASE_URL + "auth")
@RequiredArgsConstructor
@Tag(name = "Контроллер авторизации пользователя", description = "Предоставляет ендпоинты для авторизации и обновлению токена на основании рефреш-токена ")
public class AuthenticationController {

    private final SecurityService securityService;

    @Operation(summary = "Авторизация пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь авторизован"
                    , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(allOf = AuthenticationResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content())
    })
    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> authentication(@RequestBody AuthDto authDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(securityService.authentication(authDto));
    }
    @Operation(summary = "Обновление токена по рефреш-токену", responses = {
            @ApiResponse(responseCode = "200", description = "Токен обновлен"
                    , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(allOf = AuthenticationResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано (истек срок действия рефреш-токена)"
                    , content = @Content)
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestBody RefreshTokenRequest tokenRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(securityService.refreshToken(tokenRequest));
    }


}
