package com.example.bookingapp.service;

import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.RatingDto;
import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.exception.ContentNotFoundException;
import com.example.bookingapp.mapper.HotelMapper;
import com.example.bookingapp.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelDto setRating(Long hotelId, RatingDto ratingDto) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ContentNotFoundException("Hotel not found!"));

        if (hotel.getNumberRatings() > 0) {

            Double rating = hotel.getRating();
            Integer newMark = ratingDto.getRating();
            Integer numberOfRating = hotel.getNumberRatings();
            Double totalRating = (rating * numberOfRating) - rating + newMark;
            hotel.setRating(Math.ceil((totalRating / numberOfRating) * Math.pow(10, 1)) / Math.pow(10, 1));
            hotel.setNumberRatings(numberOfRating + 1);
        } else {
            hotel.setRating(Double.valueOf(ratingDto.getRating()));
            hotel.setNumberRatings(1);
        }

        return hotelMapper.convertToDto(hotelRepository.save(hotel));

    }
}
