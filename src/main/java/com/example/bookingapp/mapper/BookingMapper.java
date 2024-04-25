package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.entity.Booking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, UserMapper.class})
public interface BookingMapper {

    BookingDto convertToDto(Booking booking);

    Booking convertToEntity(BookingDto dto);

//    List<BookingDto> convertListToListDto(List<Booking> booking);

}
