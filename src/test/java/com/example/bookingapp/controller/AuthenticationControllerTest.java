package com.example.bookingapp.controller;

import com.example.bookingapp.AbstractBookingAppIntegrationTests;
import com.example.bookingapp.dto.authentication.AuthDto;
import com.example.bookingapp.dto.authentication.AuthenticationResponseDto;
import com.example.bookingapp.dto.authentication.RefreshTokenRequest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationControllerTest extends AbstractBookingAppIntegrationTests {

    private static String token;
    private static String refreshToken;

    @Test
    @Order(1)
    public void whenAuthentication_returnAuthenticationResponseDto() throws Exception {
        AuthDto authDto = new AuthDto();
        authDto.setEmail("admin@mail.ru");
        authDto.setPassword("123456");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BaseUrl.BASE_URL + "auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDto))).andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();
        String json = result.getResponse().getContentAsString();
        AuthenticationResponseDto responseDto = objectMapper.readValue(json, AuthenticationResponseDto.class);
        token = responseDto.getAccessToken();
        refreshToken = responseDto.getRefreshToken();
        Assertions.assertNotNull(responseDto);
    }

    @Test
    @Order(2)
    public void whenRefreshToken_returnAuthenticationResponseDto() throws Exception {

        RefreshTokenRequest request = new RefreshTokenRequest(refreshToken);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BaseUrl.BASE_URL + "auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();

        String json = result.getResponse().getContentAsString();
        AuthenticationResponseDto responseDto = objectMapper.readValue(json, AuthenticationResponseDto.class);
        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(token.length(),responseDto.getAccessToken().length());
    }

}
