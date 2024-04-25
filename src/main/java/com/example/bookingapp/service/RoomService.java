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
import org.springframework.transaction.annotation.Transactional;

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
        if (roomDto.getIsDeleted()) {
            throw new ContentNotFoundException("Room not found");
        }
        return roomDto;
    }

    @Transactional
    @Override
    public RoomDto create(RoomDto dto) {
        Room room = roomMapper.convertToEntity(dto);
        room.setHotel(hotelRepository.findById(dto.getHotel().getId()).orElseThrow(() -> new ContentNotFoundException("Hotel not found!")));
        return roomMapper.convertToDto(roomRepository.save(room));
    }

    @Override
    public RoomDto update(Long id, RoomDto dto) {
        Room existRoom = roomRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("Room not found"));
        if (existRoom.getIsDeleted()) {
            throw new ContentNotFoundException("Room not found");
        }
        existRoom.setRoom(dto.getRoom());
        existRoom.setRoomCapacity(dto.getRoomCapacity());
        existRoom.setName(dto.getName());
        existRoom.setPrice(dto.getPrice());
        existRoom.setDescription(dto.getDescription());
        return roomMapper.convertToDto(roomRepository.save(existRoom));
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public List<RoomDto> createAll(List<RoomDto> roomDtos) {
        return roomDtos.stream().map(this::create).collect(Collectors.toList());
    }

}
