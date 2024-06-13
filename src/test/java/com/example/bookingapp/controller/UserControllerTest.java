package com.example.bookingapp.controller;

import com.example.bookingapp.AbstractBookingAppIntegrationTests;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.entity.Role;
import com.example.bookingapp.entity.RoleType;
import com.example.bookingapp.entity.User;
import com.example.bookingapp.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.Set;

@DisplayName("Тест UserController")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest extends AbstractBookingAppIntegrationTests {


    @Autowired
    UserRepository userRepository;


    @Test
    @Order(2)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenGetAll_thenReturnAllUsers() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(json);
        JsonNode extractEntity = node.get("content");
        String testResult = extractEntity.toString();
        Collection<UserDto> resultDtos = objectMapper.readValue(testResult, new TypeReference<>() {
        });
        Assertions.assertNotNull(resultDtos);
        Assertions.assertEquals(2, resultDtos.size());

    }


    @Test
    @Order(1)
    public void whenCreateUser_thenReturnUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("UserTest");
        userDto.setPassword("12345");
        userDto.setEmail("testMail@mail.ru");
        Role role = new Role();
        role.setRoleType(RoleType.ROLE_USER);
        userDto.setRoles(Set.of(role));
        long beforeCount = userRepository.count();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        UserDto resultDto = objectMapper.readValue(json, UserDto.class);
        super.testUserId = objectMapper.readTree(json).get("id").asLong();
        Assertions.assertNotNull(resultDto);
        Assertions.assertEquals(userDto.getEmail(), resultDto.getEmail());
        long afterCount = userRepository.count();
        Assertions.assertTrue(beforeCount < afterCount);

    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenFindById_ReturnUserDto() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/{id}", testUserId))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        UserDto resultDto = objectMapper.readValue(json, UserDto.class);
        Assertions.assertNotNull(resultDto);
        Assertions.assertEquals("testMail@mail.ru", resultDto.getEmail());
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenUpdateUser_ReturnUpdatedUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("UpdateUserTest");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/update/{id}", testUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        UserDto resultDto = objectMapper.readValue(json, UserDto.class);
        Assertions.assertEquals(userDto.getName(), resultDto.getName());

    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenDeleteById_returnStatusOkAndIsDeleteEqualTrue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/user/{id}", testUserId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        User user = userRepository.findById(testUserId).orElseThrow();
        Assertions.assertTrue(user.getIsDeleted());
        user.setIsDeleted(false);
        userRepository.save(user);

    }


}
