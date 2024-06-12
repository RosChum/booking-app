package com.example.bookingapp.controller;

import com.example.bookingapp.AbstractBookingAppIntegrationTests;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.entity.Room;
import com.example.bookingapp.repository.RoomRepository;
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

@DisplayName("Тест RoomController")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomControllerTest extends AbstractBookingAppIntegrationTests {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @Order(1)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenCreate_returnRoomDto() throws Exception {
        setUp();
        RoomDto requestDto = new RoomDto();
        ShortHotelDto shortHotelDto = new ShortHotelDto();
        shortHotelDto.setId(hotelId);
        requestDto.setName("new test room");
        requestDto.setRoom(103);
        requestDto.setPrice(BigDecimal.valueOf(105.15));
        requestDto.setHotel(shortHotelDto);
        requestDto.setRoomCapacity(3);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BaseUrl.BASE_URL + "room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        RoomDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), RoomDto.class);

        Assertions.assertNotNull(responseDto.getId());
        roomId = responseDto.getId();
        Assertions.assertEquals(requestDto.getName(), responseDto.getName());
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenFindById_returnRoomDTO() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BaseUrl.BASE_URL + "room/{id}", roomId))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        RoomDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), RoomDto.class);

        Assertions.assertEquals(roomId, responseDto.getId());
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenFindAll_returnListRoomDTO() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BaseUrl.BASE_URL + "room"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode extractEntity = node.get("content");
        String testResult = extractEntity.toString();
        Collection<RoomDto> resultDtos = objectMapper.readValue(testResult, new TypeReference<>() {
        });
        Assertions.assertNotNull(resultDtos);

    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenUpdate_ReturnUpdatedRoomDto() throws Exception {

        RoomDto requestDto = new RoomDto();
        requestDto.setName("update new test room");


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(BaseUrl.BASE_URL + "room/update/{id}", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        RoomDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), RoomDto.class);

        Assertions.assertEquals(requestDto.getName(), responseDto.getName());
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenDelete_thenStatusIsDeleteEqualsTrue() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(BaseUrl.BASE_URL + "room/{id}", roomId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Room room = roomRepository.findById(roomId).orElseThrow();
        Assertions.assertTrue(room.getIsDeleted());
    }


}
