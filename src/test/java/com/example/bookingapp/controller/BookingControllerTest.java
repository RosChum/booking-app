package com.example.bookingapp.controller;

import com.example.bookingapp.AbstractBookingAppIntegrationTests;
import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.room.RoomShortDto;
import com.example.bookingapp.dto.user.UserShortDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.ZonedDateTime;
import java.util.Collection;

@DisplayName("Тест BookingController")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingControllerTest extends AbstractBookingAppIntegrationTests {

    @Test
    @Order(1)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenCreateBooking_returnBookingDto() throws Exception {
        setUp();
        BookingDto requestDto = new BookingDto();
        RoomShortDto roomShortDto = new RoomShortDto();
        roomShortDto.setId(roomId);
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(userId);
        requestDto.setRoom(roomShortDto);
        requestDto.setUser(userShortDto);
        requestDto.setArrivalDate(ZonedDateTime.now());
        requestDto.setDepartureDate(ZonedDateTime.now().plusDays(15));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BaseUrl.BASE_URL + "booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        BookingDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), BookingDto.class);
        Assertions.assertNotNull(responseDto.getId());
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenGetAll_returnListBookingDto() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BaseUrl.BASE_URL + "booking"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode extractEntity = node.get("content");
        String testResult = extractEntity.toString();
        Collection<BookingDto> resultDtos = objectMapper.readValue(testResult, new TypeReference<>() {
        });
        Assertions.assertNotNull(resultDtos);

    }

}
