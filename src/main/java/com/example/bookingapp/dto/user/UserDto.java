package com.example.bookingapp.dto.user;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto {

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    @Email(message = "Некорректный Email")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;

    private ZonedDateTime createAt;

    private List<BookingDto> booking = new ArrayList<>();

    private Set<Role> roles = new HashSet<>();
}
