package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import com.example.bookingapp.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = ZonedDateTime.class)
public interface HotelMapper {

    HotelDto convertToDto(Hotel hotel);

    Hotel convertToEntity(HotelDto hotelDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "isDeleted", defaultValue = "false")
    Hotel createEntityByDto(HotelDto hotelDto);

    ShortHotelDto convertToShortDto(Hotel hotel);
}
