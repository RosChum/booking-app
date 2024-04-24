package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
    @Mapping(target = "booking", ignore = true)
    Room convertToEntity(RoomDto roomDto);
    @Mapping(target = "booking", ignore = true)
    RoomDto convertToDto(Room room);


    List<Room> convertListDtoToListEntity(List<RoomDto> roomDtos);

    List<RoomDto> convertListEntityToListDto(List<Room> rooms);
}
