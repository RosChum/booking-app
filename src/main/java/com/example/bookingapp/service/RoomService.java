package com.example.bookingapp.service;

import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.dto.room.RoomSearchDto;
import com.example.bookingapp.entity.Room;
import com.example.bookingapp.mapper.RoomMapper;
import com.example.bookingapp.repository.HotelRepository;
import com.example.bookingapp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<RoomDto> findAll(RoomSearchDto dto, Pageable pageable) {

    }

    @Override
    public RoomDto findById(Long id) {
        return null;
    }

    @Override
    public RoomDto create(RoomDto dto) {
        Room room = roomMapper.convertToEntity(dto);
        room.setHotel(hotelRepository.findById(dto.getHotel().getId()).orElseThrow());
        return roomMapper.convertToDto(roomRepository.save(room));
    }

    @Override
    public RoomDto update(Long id, RoomDto dto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    public List<RoomDto> createAll(List<RoomDto> roomDtos) {
        return roomDtos.stream().map(this::create).collect(Collectors.toList());
    }

}
