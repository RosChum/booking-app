package com.example.bookingapp.service;

import com.example.bookingapp.configuration.cache.CacheNames;
import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.HotelSearchDto;
import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.entity.Hotel_;
import com.example.bookingapp.exception.ContentNotFoundException;
import com.example.bookingapp.mapper.HotelMapper;
import com.example.bookingapp.mapper.RoomMapper;
import com.example.bookingapp.repository.HotelRepository;
import com.example.bookingapp.specification.BaseSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static com.example.bookingapp.specification.BaseSpecification.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService implements BaseService<HotelDto, HotelSearchDto> {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final RoomService roomService;
    private final RoomMapper roomMapper;


    @Override
    public Page<HotelDto> findAll(HotelSearchDto dto, Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(getSpecification(dto), pageable);
        log.info( "HotelService " + hotels);
        return new PageImpl<>(hotels.map(hotel -> {
            HotelDto hotelDto = hotelMapper.convertToDto(hotel);
            return hotelDto;
        }).toList(), pageable, hotels.getTotalElements());

    }
    @Cacheable(cacheNames = CacheNames.HOTEL_CACHE, key = "#id")
    @Override
    public HotelDto findById(Long id) {
        HotelDto hotelDto = hotelMapper.convertToDto(hotelRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("Hotel not found!")));
        if (hotelDto.getIsDeleted()) {
            throw new ContentNotFoundException("Hotel not found!");
        }
        return hotelDto;
    }


    @Transactional
    @Override
    public HotelDto create(HotelDto dto) {
        Hotel hotel = hotelRepository.save(hotelMapper.createEntityByDto(dto));
        if (dto.getRoom() != null) {
            dto.getRoom().forEach(roomDto -> roomDto.setHotel(hotelMapper.convertToShortDto(hotel)));
            hotel.setRoom(roomMapper.convertListDtoToListEntity(roomService.createAll(dto.getRoom())));
        }
        return hotelMapper.convertToDto(hotel);
    }

    @CacheEvict(cacheNames = CacheNames.HOTEL_CACHE, key = "#id")
    @Override
    public HotelDto update(Long id, HotelDto dto) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ContentNotFoundException("Hotel not found!"));
        hotel.setName(dto.getName());
        hotel.setAddress(hotel.getAddress());
        hotel.setCity(dto.getCity());
        hotel.setDistanceFromCenter(dto.getDistanceFromCenter());
        hotel.setHeadline(dto.getHeadline());
        hotel.setTimeChanged(ZonedDateTime.now());
        return hotelMapper.convertToDto(hotelRepository.save(hotel));
    }

    @CacheEvict(cacheNames = CacheNames.HOTEL_CACHE, key = "#id")
    @Override
    public void deleteById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ContentNotFoundException("Hotel not found!"));
        hotel.getRoom().forEach(room -> roomService.deleteById(room.getId()));
        hotelRepository.delete(hotel);
    }


    private Specification<Hotel> getSpecification(HotelSearchDto searchDto) {
        return BaseSpecification.getBaseSpecification(searchDto)
                .and(equal(Hotel_.name, searchDto.getName()))
                .and(equal(Hotel_.city, searchDto.getCity()))
                .and(equal(Hotel_.address, searchDto.getAddress()))
                .and(equal(Hotel_.isDeleted, false))
                .and(like(Hotel_.headline, searchDto.getHeadline()))
                .and(greaterThanOrEqualTo(Hotel_.numberRatings, searchDto.getNumberRatings()))
                .and(greaterThanOrEqualTo(Hotel_.rating, searchDto.getRating()));
    }


}
