package com.example.bookingapp.controller;

import com.example.bookingapp.AbstractBookingAppIntegrationTests;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.entity.Role;
import com.example.bookingapp.entity.RoleType;
import com.example.bookingapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

public class UserControllerTest extends AbstractBookingAppIntegrationTests {


    @Autowired
    UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenGetAll_thenReturnAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void whenCreateUser_thenReturnUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("UserTest");
        userDto.setPassword("12345");
        userDto.setEmail("testMail@mail.ru");
        Role role = new Role();
        role.setRoleType(RoleType.ROLE_USER);
        userDto.setRoles(Set.of(role));

        long afterCount = userRepository.count();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        long beforeCount = userRepository.count();
        Assertions.assertTrue(afterCount < beforeCount);

    }

}
