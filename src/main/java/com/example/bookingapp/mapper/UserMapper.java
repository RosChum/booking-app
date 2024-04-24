package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "booking", ignore = true)
    User convertToEntity(UserDto userDto);
    @Mapping(target = "booking", ignore = true)
    UserDto convertToDto(User user);

}
