package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import com.example.bookingapp.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    HotelDto convertToDto(Hotel hotel);

    Hotel convertToEntity(HotelDto hotelDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "isDeleted", dateFormat = "false")
    Hotel createEntityByDto(HotelDto hotelDto);

    ShortHotelDto convertToShortDto(Hotel hotel);
}
