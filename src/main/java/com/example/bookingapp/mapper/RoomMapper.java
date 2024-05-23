package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = BookingMapper.class)
public interface RoomMapper {

    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "isDeleted", defaultValue = "false")
    Room convertToEntity(RoomDto roomDto);

    @Mapping(target = "booking", ignore = true)
    RoomDto convertToDto(Room room);

    List<Room> convertListDtoToListEntity(List<RoomDto> roomDtos);

    List<RoomDto> convertListEntityToListDto(List<Room> rooms);
}
