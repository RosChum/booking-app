package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.dto.user.UserShortDto;
import com.example.bookingapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = ZonedDateTime.class, uses = BookingMapper.class)
public interface UserMapper {

    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "createAt", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "password", ignore = true)
    User convertToEntity(UserDto userDto);

    @Mapping(target = "booking", ignore = true)
    UserDto convertToDto(User user);

    UserShortDto convertToShortDto(User user);

}
