package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.booking.BookingShortDto;
import com.example.bookingapp.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, UserMapper.class})
public interface BookingMapper {

    BookingDto convertToDto(Booking booking);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "isDeleted", defaultValue = "false")
    Booking convertToEntity(BookingDto dto);

    BookingShortDto convertToShortDto(Booking booking);
    List<BookingShortDto> convertListToListShortDto(List<Booking> booking);
}
