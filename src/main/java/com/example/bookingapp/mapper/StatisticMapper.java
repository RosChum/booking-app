package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.entity.mongodb.BookingRoomInformation;
import com.example.bookingapp.entity.mongodb.RegistrationUserInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatisticMapper {

    @Mapping(target = "userId", source = "id")
    RegistrationUserInformation convertToRegistrationUserInf(UserDto userDto);

    @Mapping(target = "bookingId", source = "id")
    BookingRoomInformation convertToBookingRoomInformation(BookingDto bookingDto);

}
