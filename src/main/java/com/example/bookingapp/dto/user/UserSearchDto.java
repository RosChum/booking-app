package com.example.bookingapp.dto.user;

import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.entity.Role;
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
public class UserSearchDto extends BaseSearchDto {

    private String name;

    private String password;

    private String email;

    private ZonedDateTime createAt;

    private List<BookingDto> booking = new ArrayList<>();

    private Set<Role> roles = new HashSet<>();
}
