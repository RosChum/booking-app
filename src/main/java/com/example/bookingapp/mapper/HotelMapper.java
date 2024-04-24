package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import com.example.bookingapp.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = ZonedDateTime.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    @Mapping(target = "room", ignore = true)
    HotelDto convertToDto(Hotel hotel);
    @Mapping(target = "room", ignore = true)
    Hotel convertToEntity(HotelDto hotelDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "room", ignore = true)
    Hotel createEntityByDto(HotelDto hotelDto);

    ShortHotelDto convertToShortDto(Hotel hotel);
}
