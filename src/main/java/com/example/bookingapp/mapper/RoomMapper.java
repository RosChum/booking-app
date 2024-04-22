package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.entity.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room convertToEntity(RoomDto roomDto);

    RoomDto convertToDto(Room room);


    List<Room> convertListDtoToListEntity(List<RoomDto> roomDtos);

    List<RoomDto> convertListEntityToListDto(List<Room> rooms);
}
