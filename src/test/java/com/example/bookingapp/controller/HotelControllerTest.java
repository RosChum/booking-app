package com.example.bookingapp.controller;

import com.example.bookingapp.AbstractBookingAppIntegrationTests;
import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.RatingDto;
import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.repository.HotelRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collection;

@DisplayName("Тест  HotelController")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HotelControllerTest extends AbstractBookingAppIntegrationTests {


    @Autowired
    private HotelRepository hotelRepository;

    @Test
    @Order(1)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenCreateHotel_returnHotelDTO() throws Exception {
        HotelDto requestDto = new HotelDto();
        requestDto.setName("TestHotel");
        requestDto.setHeadline("some  description");
        requestDto.setCity("Rome");
        requestDto.setAddress("some address");
        requestDto.setDistanceFromCenter(10);
        RoomDto roomDto = new RoomDto();
        roomDto.setName("some Room");
        roomDto.setRoom(101);
        roomDto.setRoomCapacity(2);
        roomDto.setPrice(BigDecimal.valueOf(115.75));
        requestDto.getRoom().add(roomDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BaseUrl.BASE_URL + "hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        HotelDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), HotelDto.class);

        hotelId = responseDto.getId();
        Assertions.assertNotNull(responseDto.getId());
        Assertions.assertEquals(requestDto.getName(), responseDto.getName());

    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenFindById_returnHotelDTO() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BaseUrl.BASE_URL + "hotel/{id}", hotelId))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        HotelDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), HotelDto.class);

        Assertions.assertNotNull(responseDto.getId());
        Assertions.assertEquals("TestHotel", responseDto.getName());

    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenFindAll_returnPageWithHotels() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BaseUrl.BASE_URL + "hotel"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(json);
        JsonNode extractEntity = node.get("content");
        String testResult = extractEntity.toString();
        Collection<HotelDto> resultDtos = objectMapper.readValue(testResult, new TypeReference<>() {
        });
        Assertions.assertNotNull(resultDtos);
    }


    @Test
    @Order(4)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenUpdateById_returnUpdatedDto() throws Exception {
        HotelDto requestDto = new HotelDto();
        requestDto.setName("Update Hotel Name");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(BaseUrl.BASE_URL + "hotel/update/{id}", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        HotelDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), HotelDto.class);

        Assertions.assertEquals(requestDto.getName(), responseDto.getName());
    }

    @Test
    @Order(5)
    @WithMockUser(username = "UpdateUserTest", roles = {"USER"})
    public void whenSetRating_returnHotelDtoWithUpdatedRating() throws Exception {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(5);
        mockMvc.perform(MockMvcRequestBuilders.post(BaseUrl.BASE_URL + "hotel/rating/{hotelId}", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Assertions.assertEquals(Double.valueOf(5), hotel.getRating());

    }


    @Test
    @Order(6)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenDeleteByID_changeStatusEntityIsDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BaseUrl.BASE_URL + "/hotel/{id}", hotelId));

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Assertions.assertTrue(hotel.getIsDeleted());
        hotel.setIsDeleted(false);
        hotelRepository.save(hotel);
    }

}
