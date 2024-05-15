package com.example.bookingapp.service;

import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.dto.room.RoomSearchDto;
import com.example.bookingapp.entity.*;
import com.example.bookingapp.exception.ContentNotFoundException;
import com.example.bookingapp.mapper.BookingMapper;
import com.example.bookingapp.mapper.RoomMapper;
import com.example.bookingapp.repository.HotelRepository;
import com.example.bookingapp.repository.RoomRepository;
import com.example.bookingapp.specification.BaseSpecification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.bookingapp.specification.BaseSpecification.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService implements BaseService<RoomDto, RoomSearchDto> {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;
    private final BookingMapper bookingMapper;

    @Override
    public Page<RoomDto> findAll(RoomSearchDto dto, Pageable pageable) {

        Page<Room> rooms = roomRepository.findAll(getSpecification(dto), pageable);


        return new PageImpl<>(rooms.map(room -> {


            RoomDto roomDto = roomMapper.convertToDto(room);
            roomDto.setBooking(room.getBooking().stream().map(bookingMapper::convertToDto).collect(Collectors.toList()));
            return roomDto;
        }).toList(), pageable, rooms.getTotalElements());
    }

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


    public Specification<Room> getSpecification(RoomSearchDto searchDto) {
        return BaseSpecification.getBaseSpecification(searchDto)
                .and(equal(Room_.name, searchDto.getName()))
                .and(like(Room_.description, searchDto.getDescription()))
                .and(equal(Room_.roomCapacity, searchDto.getRoomCapacity()))
                .and(between(Room_.price, searchDto.getMinPrice(), searchDto.getMaxPrice()))
                .and(joinHotel(searchDto.getHotelId()))
                .and(checkBooking(searchDto.getArrivalDate(), searchDto.getDepartureDate()))
                ;
    }


    private Specification<Room> joinHotel(Long hotelId) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }
            Join<Room, Hotel> hotelJoin = root.join(Room_.hotel);
            return criteriaBuilder.equal(hotelJoin.get(Hotel_.id), hotelId);
        });
    }

    private Specification<Room> checkBooking(ZonedDateTime timeFrom, ZonedDateTime timeTo) {
        return ((root, query, criteriaBuilder) -> {
            if (timeFrom == null || timeTo == null) {
                return null;
            }
            Join<Room, Booking> bookingJoin = root.join(Room_.booking);

            Predicate predicate = criteriaBuilder.or(criteriaBuilder.between(bookingJoin.get(Booking_.arrivalDate), timeFrom, timeTo),
                    criteriaBuilder.between(bookingJoin.get(Booking_.departureDate), timeFrom, timeTo)
                    ,
                    criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(bookingJoin.get(Booking_.arrivalDate), timeFrom),
                            criteriaBuilder.greaterThanOrEqualTo(bookingJoin.get(Booking_.departureDate), timeTo)
                    )
            );
// TODO проверить логику предикатов

//            Predicate predicate1 = criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(bookingJoin.get(Booking_.arrivalDate), timeTo),
//                    criteriaBuilder.greaterThanOrEqualTo(bookingJoin.get(Booking_.departureDate), timeFrom));
//

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Booking> bookingRoot = subquery.from(Booking.class);
            subquery.select(bookingRoot.get(Booking_.room).get(Room_.id));
            subquery.where(predicate);

            return root.get(Room_.id).in(subquery).not();

        });
    }

}
