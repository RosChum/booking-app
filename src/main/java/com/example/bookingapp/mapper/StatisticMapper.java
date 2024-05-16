package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.kafkaEvent.BookingRoomEvent;
import com.example.bookingapp.dto.kafkaEvent.RegistrationUserEvent;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.entity.mongodb.BookingRoomInformation;
import com.example.bookingapp.entity.mongodb.RegistrationUserInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatisticMapper {

    @Mapping(target = "userId", source = "id")
    RegistrationUserEvent convertToRegistrationUserEvent(UserDto userDto);

    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "hotelId", expression = "java(bookingDto.getRoom().getHotel().getId())")
    @Mapping(target = "userId", expression = "java(bookingDto.getUser().getId())")
    BookingRoomEvent convertToBookingRoomEvent(BookingDto bookingDto);

    RegistrationUserInformation fromUserEventToRegistrationUserInf(RegistrationUserEvent registrationUserEvent);

    BookingRoomInformation fromRoomEventToBookingRoomInformation(BookingRoomEvent bookingRoomEvent);
}
