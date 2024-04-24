package com.example.bookingapp.service;

import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.dto.room.RoomSearchDto;
import com.example.bookingapp.entity.Room;
import com.example.bookingapp.exception.ContentNotFoundException;
import com.example.bookingapp.mapper.RoomMapper;
import com.example.bookingapp.repository.HotelRepository;
import com.example.bookingapp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService implements BaseService<RoomDto, RoomSearchDto> {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;


    @Override
    public RoomDto findById(Long id) {
        RoomDto roomDto = roomMapper.convertToDto(roomRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("Room not found")));

        return roomDto;
    }

    @Override
    public RoomDto create(RoomDto dto) {
        Room room = roomMapper.convertToEntity(dto);
        room.setHotel(hotelRepository.findById(dto.getHotel().getId()).orElseThrow());
        return roomMapper.convertToDto(roomRepository.save(room));
    }

    @Override
    public RoomDto update(Long id, RoomDto dto) {

        Room exsistRoom = roomRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("Room not found"));

        exsistRoom.setRoom(dto.getRoom());
        exsistRoom.setRoomCapacity(dto.getRoomCapacity());
        exsistRoom.setName(dto.getName());
        exsistRoom.setPrice(dto.getPrice());
        exsistRoom.setDescription(dto.getDescription());

        return roomMapper.convertToDto(roomRepository.save(exsistRoom));
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public List<RoomDto> createAll(List<RoomDto> roomDtos) {
        return roomDtos.stream().map(this::create).collect(Collectors.toList());
    }

}
