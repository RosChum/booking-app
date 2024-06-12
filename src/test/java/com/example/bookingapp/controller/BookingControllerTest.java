package com.example.bookingapp.controller;

import com.example.bookingapp.AbstractBookingAppIntegrationTests;
import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.room.RoomShortDto;
import com.example.bookingapp.dto.user.UserShortDto;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.ZonedDateTime;

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
        requestDto.setDepartureDate( ZonedDateTime.now().plusDays(15));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BaseUrl.BASE_URL + "booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        BookingDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), BookingDto.class);
        Assertions.assertNotNull(responseDto.getId());
    }



}
